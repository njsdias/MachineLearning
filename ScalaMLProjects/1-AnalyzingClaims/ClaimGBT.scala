package com.ctw.spark

import org.apache.spark.ml.regression.{GBTRegressor, GBTRegressionModel}
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.tuning.ParamGridBuilder
import org.apache.spark.ml.tuning.CrossValidator
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.mllib.evaluation.RegressionMetrics

object ClaimGBT {
  
  /* Define and initialize the hyperparameters needed to train the GBTs, such as the
   * number of trees, number of max bins, number of folds to be used during cross-validation,
   * number of maximum iterations to iterate the training, and finally max tree depth:*/
  
  val NumTrees = Seq(5, 10, 15)
  val MaxBins = Seq(5, 7, 9)
  val numFolds = 10
  val MaxIter: Seq[Int] = Seq(10)
  val MaxDepth: Seq[Int] = Seq(10)
  
  // Instantiate a Spark session and implicits
  val spark = SparkSessionCreate.createSession()
  import spark.implicits._
  
  // Create the GBT regressor
  val model = new GBTRegressor()
                  .setFeaturesCol("features")
                  .setLabelCol("label")
                  
  // Build the pipeline by chaining the transformations and predictor together
  val pipeline = new Pipeline().setStages((Preprocessing.stringIndexerStages :+ Preprocessing.assembler) :+ model)
  
  // define paramgrid for cross validation
  val paramgrid = new ParamGridBuilder()
                      .addGrid(model.maxIter, MaxIter)
                      .addGrid(model.maxDepth, MaxDepth)
                      .addGrid(model.maxBins, MaxBins)
                      .build()
                      
  /* For a better and stable performance, let's prepare the K-fold cross-validation
   * and grid search as a part of model tuning.*/
   println("Preparing K-fold Cross Validation and Grid Search")
   val cv = new CrossValidator()
                .setEstimator(pipeline)
                .setEstimatorParamMaps(paramgrid)
                .setEvaluator(new RegressionEvaluator)
                .setNumFolds(numFolds)
   
   // Training model
   println("Training model with Gradient Boost Trees algorithm")
   val cvModel = cv.fit(Preprocessing.trainingData)
   
   //Evaluating the model
   //RMSE
   println("Evaluating model on train and test data and calculating RMSE")
   //training Data
   val trainPredictionsAndLabels = cvModel.transform(Preprocessing.trainingData)
                                   .select("label","predition")
                                   .map { case Row(label: Double, prediction: Double) => (label,prediction) }.rdd
   //Validation Data 
   val validPredictionsAndLabels = cvModel.transform(Preprocessing.validationData)
                                   .select("label","prediction")
                                   .map { case Row(label: Double, prediction: Double) => (label,prediction) }.rdd 
                                   
   val trainRegressionMetrics = new RegressionMetrics(trainPredictionsAndLabels)
   val validRegressionMetrics = new RegressionMetrics(validPredictionsAndLabels)
                                   
   // Best Model
   val bestModel = cvModel.bestModel.asInstanceOf[PipelineModel]   
   // Find the feature importance of the best model
   val featureImportances = bestModel.stages.last
                                            .asInstanceOf[GBTRegressionModel]
                                            .featureImportances.toArray
    val FI_to_List_sorted = featureImportances.toList.sorted.toArray
    
    //Output Results
    val output = "\n=====================================================================\n" +
      s"Param trainSample: ${Preprocessing.trainSample}\n" +
      s"Param testSample: ${Preprocessing.testSample}\n" +
      s"TrainingData count: ${Preprocessing.trainingData.count}\n" +
      s"ValidationData count: ${Preprocessing.validationData.count}\n" +
      s"TestData count: ${Preprocessing.testData.count}\n" +
      "=====================================================================\n" +
      s"Param maxIter = ${MaxIter.mkString(",")}\n" +
      s"Param maxDepth = ${MaxDepth.mkString(",")}\n" +
      s"Param numFolds = ${numFolds}\n" +
      "=====================================================================\n" +
      s"Training data MSE = ${trainRegressionMetrics.meanSquaredError}\n" +
      s"Training data RMSE = ${trainRegressionMetrics.rootMeanSquaredError}\n" +
      s"Training data R-squared = ${trainRegressionMetrics.r2}\n" +
      s"Training data MAE = ${trainRegressionMetrics.meanAbsoluteError}\n" +
      s"Training data Explained variance = ${trainRegressionMetrics.explainedVariance}\n" +
      "=====================================================================\n" +
      s"Validation data MSE = ${validRegressionMetrics.meanSquaredError}\n" +
      s"Validation data RMSE = ${validRegressionMetrics.rootMeanSquaredError}\n" +
      s"Validation data R-squared = ${validRegressionMetrics.r2}\n" +
      s"Validation data MAE = ${validRegressionMetrics.meanAbsoluteError}\n" +
      s"Validation data Explained variance = ${validRegressionMetrics.explainedVariance}\n" +
      "=====================================================================\n" +
      s"CV params explained: ${cvModel.explainParams}\n" +
      s"GBT params explained: ${bestModel.stages.last.asInstanceOf[GBTRegressionModel].explainParams}\n" +
      s"GBT features importances:\n ${Preprocessing.featureCols.zip(FI_to_List_sorted).map(t => s"\t${t._1} = ${t._2}").mkString("\n")}\n" +
      "=====================================================================\n"

    println(output)
    // *****************************************
    println("Run prediction over test dataset")
    // *****************************************

    // Predicts and saves file ready for Kaggle!
    //if(!params.outputFile.isEmpty){
    cvModel.transform(Preprocessing.testData)
      .select("id", "prediction")
      .withColumnRenamed("prediction", "loss")
      .coalesce(1)
      .write.format("com.databricks.spark.csv")
      .option("header", "true")
      .save("output/result_GBT.csv")
    //}
  
   spark.stop() 
      
}