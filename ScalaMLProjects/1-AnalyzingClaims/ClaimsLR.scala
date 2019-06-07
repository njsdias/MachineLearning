package com.ctw.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.log4j._

import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}
import org.apache.spark.ml.{ Pipeline, PipelineModel}
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.tuning.ParamGridBuilder
import org.apache.spark.ml.tuning.{CrossValidator, CrossValidatorModel}
import org.apache.spark.mllib.evaluation.RegressionMetrics

import org.apache.spark.ml.feature.{StringIndexer, StringIndexerModel}
import org.apache.spark.ml.feature.VectorAssembler


object ClaimsLR {
  
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.OFF)
    
    /* Then we create an active Spark session as the entry point to the application.
     * In addition, importing implicits__ required for implicit conversions like 
     * converting RDDs to DataFrames. */
    val spark = SparkSessionCreate.createSession()
    import spark.implicits._
    
    // Hyperparameters definition
    val numFolds = 10                                // the number of folds for cross-validation
    val MaxIter: Seq[Int] = Seq(1000)                // number of maximum iterations
    val RegParam: Seq[Double] = Seq(0.001)           // value of the regression parameter
    val Tol: Seq[Double] = Seq(1e-6)                 // the value of tolerance
    val ElasticNetParam: Seq[Double] = Seq(0.001)    // elastic network parameters
    
    // create an LR estimator
    val model = new LinearRegression()
        .setFeaturesCol("features")
        .setLabelCol("label")
    
    // build a pipeline estimator by chaining the transformer and the LR estimator
    println("Building ML pipeline")
    val pipeline = new Pipeline().setStages((Preprocessing.stringIndexerStages :+ Preprocessing.assembler) :+ model)
 
    /* The paramgrid allow us specify the number of maximum iterations, 
     * the value of the regression parameter, the value of tolerance, 
     * and Elastic network parameters */
    println("Preparing K-fold Cross Validation and Grid Search: Model tuning")
    // ***********************************************************
    val paramGrid = new ParamGridBuilder()
      .addGrid(model.maxIter, MaxIter)
      .addGrid(model.regParam, RegParam)
      .addGrid(model.tol, Tol)
      .addGrid(model.elasticNetParam, ElasticNetParam)
      .build()

   /* for a better and stable performance, let's prepare the K-fold cross-validation 
    * and grid search as a part of model tuning  */
    val cv = new CrossValidator()
      .setEstimator(pipeline)
      .setEvaluator(new RegressionEvaluator)
      .setEstimatorParamMaps(paramGrid)
      .setNumFolds(numFolds)

    // train the LR model:
    println("Training model with Linear Regression algorithm")
    val cvModel = cv.fit(Preprocessing.trainingData)
  
   /* evaluating the model on the train and validation set 
    * and calculating RMSE, MSE, MAE, R-squared */
    println("Evaluating model on train and validation set and calculating RMSE")
    val trainPredictionsAndLabels = cvModel.transform(Preprocessing.trainingData)
                                    .select("label", "prediction")
                                    .map { case Row(label: Double, prediction: Double)
                                    => (label, prediction) }.rdd
    val validPredictionsAndLabels = cvModel.transform(Preprocessing.validationData)
                                    .select("label", "prediction")
                                    .map { case Row(label: Double, prediction: Double)
                                    => (label, prediction) }.rdd

    val trainRegressionMetrics = new RegressionMetrics(trainPredictionsAndLabels)
    val validRegressionMetrics = new RegressionMetrics(validPredictionsAndLabels)
                                    
    val bestModel = cvModel.bestModel.asInstanceOf[PipelineModel]
                                    
    val results = "\n=====================================================================\n" +
      s"Param trainSample: ${Preprocessing.trainSample}\n" +
      s"Param testSample: ${Preprocessing.testSample}\n" +
      s"TrainingData count: ${Preprocessing.trainingData.count}\n" +
      s"ValidationData count: ${Preprocessing.validationData.count}\n" +
      s"TestData count: ${Preprocessing.testData.count}\n" +
      "=====================================================================\n" +
      s"Param maxIter = ${MaxIter.mkString(",")}\n" +
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
      s"CV params explained: ${cvModel.explainParams}\n" +
      s"GBT params explained: ${bestModel.stages.last.asInstanceOf[LinearRegressionModel].explainParams}\n" +
      "=====================================================================\n"
    println(results)
    
    // *****************************************
    println("Run prediction on the test set")
    cvModel.transform(Preprocessing.testData)
            .select("id", "prediction")
             .withColumnRenamed("prediction", "loss")
             .coalesce(1)
             .write.format("com.databricks.spark.csv")
             .option("header", "true")
             .save("output/result_LR.csv")
      
    spark.stop()  
    
  
}