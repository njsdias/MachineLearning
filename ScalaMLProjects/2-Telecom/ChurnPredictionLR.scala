package com.ctw.spark

import org.apache.spark._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.ml.classification.{BinaryLogisticRegressionSummary, LogisticRegression, LogisticRegressionModel}
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.tuning.{ParamGridBuilder, CrossValidator}
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator


object ChurnPredictionLR {

    val spark: SparkSession = SparkSessionCreate.createSession()
    import spark.implicits._

    // to define some hyperparameters to train an LR-based pipeline:  
    val numFolds = 10
    val MaxIter: Seq[Int] = Seq(100)
    val RegParam: Seq[Double] = Seq(1.0)        // L2 regularization param, set 0.10 with L1 reguarization
    val Tol: Seq[Double] = Seq(1e-8)
    val ElasticNetParam: Seq[Double] = Seq(1.0) // Combination of L1 and L2
    
   //instantiate an LR estimator
    val lr = new LogisticRegression()
                    .setLabelCol("label")
                    .setFeaturesCol("features")

    // chain in a single pipeline the three transformers and an estimator
    val pipeline = new Pipeline()
      .setStages(Array(PipelineConstructor.ipindexer,
        PipelineConstructor.labelindexer,
        PipelineConstructor.assembler,
        lr))

    /* The paramgrid allow us specify the number of maximum iterations, 
     * the value of the regression parameter, the value of tolerance, 
     * and Elastic network parameters */                       
    val paramGrid = new ParamGridBuilder()
      .addGrid(lr.maxIter, MaxIter)
      .addGrid(lr.regParam, RegParam)
      .addGrid(lr.tol, Tol)
      .addGrid(lr.elasticNetParam, ElasticNetParam)
      .build()
  /* Using the "BinaryClassificationEvaluator" evaluator, the model will be evaluated according
   * to a precision metric by comparing the test label column with the test prediction column.
   * The default metrics are an area under the precision-recall curve and an area under the
   * receiver operating characteristic (ROC) curve:*/
    val evaluator = new BinaryClassificationEvaluator()
                  .setLabelCol("label")
                  .setRawPredictionCol("prediction")

    // Set up 10-fold cross validation
    val crossval = new CrossValidator()
      .setEstimator(pipeline)
      .setEvaluator(evaluator)
      .setEstimatorParamMaps(paramGrid)
      .setNumFolds(numFolds)

    val cvModel = crossval.fit(Preprocessing.trainDF)   

    //Evaluate the model against the test dataset
    val predictions = cvModel.transform(Preprocessing.testSet)
    val result = predictions.select("label", "prediction", "probability")
    val resutDF = result.withColumnRenamed("prediction", "Predicted_label")
    resutDF.show(10)
    
    /* However, seeing the previous prediction DataFrame, it is really difficult to guess the
     * classification accuracy. In the second step, the evaluator evaluates itself using
     * BinaryClassificationEvaluator, as follows:*/
    val accuracy = evaluator.evaluate(predictions)
    println("Classification accuracy: " + accuracy)  
    
    /*For Area Under the precision-recall curve and Area Under the ROC curve
     *  we need to construct an RDD containing the raw scores on the test set:*/

    // Compute other performance metrics
    val predictionAndLabels = predictions
      .select("prediction", "label")
      .rdd.map(x => ( x(0).asInstanceOf[Double], 
                      x(1).asInstanceOf[Double]))

    val metrics = new BinaryClassificationMetrics(predictionAndLabels)
    val areaUnderPR = metrics.areaUnderPR
    println("Area under the precision-recall curve: " + areaUnderPR)
    
    val areaUnderROC = metrics.areaUnderROC
    println("Area under the receiver operating characteristic (ROC) curve: " + areaUnderROC)
    
   //In this case, the evaluation returns 77% accuracy, but only 58% precision

    /*
    val precesion = metrics.precisionByThreshold()
    println("Precision: "+ precesion.foreach(print))
    
    val recall = metrics.recallByThreshold()
    println("Recall: "+ recall.foreach(print))
    
    val f1Measure = metrics.fMeasureByThreshold()
    println("F1 measure: "+ f1Measure.foreach(print))
    * 
    */

    val lp = predictions.select("label", "prediction")
    val counttotal = predictions.count()
    val correct = lp.filter($"label" === $"prediction").count()
    val wrong = lp.filter(not($"label" === $"prediction")).count()
    val ratioWrong = wrong.toDouble / counttotal.toDouble
    val ratioCorrect = correct.toDouble / counttotal.toDouble
    val truep = lp.filter($"prediction" === 0.0).filter($"label" === $"prediction").count() / counttotal.toDouble
    val truen = lp.filter($"prediction" === 1.0).filter($"label" === $"prediction").count() / counttotal.toDouble
    val falsep = lp.filter($"prediction" === 1.0).filter(not($"label" === $"prediction")).count() / counttotal.toDouble
    val falsen = lp.filter($"prediction" === 0.0).filter(not($"label" === $"prediction")).count() / counttotal.toDouble

    println("Total Count: " + counttotal)
    println("Correct: " + correct)
    println("Wrong: " + wrong)
    println("Ratio wrong: " + ratioWrong)
    println("Ratio correct: " + ratioCorrect)
    println("Ratio true positive: " + truep)
    println("Ratio false positive: " + falsep)
    println("Ratio true negative: " + truen)
    println("Ratio false negative: " + falsen)
    
    // we have not received good accuracy, so let's continue trying other classifiers. 
    // SVM is the next one.

}