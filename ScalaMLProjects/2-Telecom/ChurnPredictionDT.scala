package com.ctw.spark

import org.apache.spark._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{DecisionTreeClassifier,
DecisionTreeClassificationModel}
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.tuning.{ParamGridBuilder, CrossValidator}

object ChurnPredictionDT {
  
  val spark: SparkSession =
  SparkSessionCreate.createSession()
  import spark.implicits._
  
  // instantiate a DecisionTreeClassifier estimator
  val dTree = new DecisionTreeClassifier()
      .setLabelCol("label")
      .setFeaturesCol("features")
      .setSeed(1234567L)
     
 // chain in a single pipeline the three transformers and an estimator     
 val pipeline = new Pipeline()
      .setStages(Array(PipelineConstructor.ipindexer,
          PipelineConstructor.labelindexer,
          PipelineConstructor.assembler,
          dTree))     
          
 // >>> Define Paramgrid <<<<<
     
 /* The paramgrid to perform such a grid search over the hyperparameter space.
  * This search is through DT's impurity, max bins, and max depth for the best model.
  * Maximum depth of the tree: depth 0 means 1 leaf node; depth 1 means 1 internal node + 2 leaf nodes.
  * On the other hand, the maximum number of bins is used for separate continuous features
  * and for choosing how to split on features at each node.*/
  
  var paramGrid = new ParamGridBuilder()
         .addGrid(dTree.impurity, "gini" :: "entropy" :: Nil)
         .addGrid(dTree.maxBins, 2 :: 5 :: 10 :: 15 :: 20 :: 25 :: 30 :: Nil)
         .addGrid(dTree.maxDepth, 5 :: 10 :: 15 :: 20 :: 25 :: 30 :: Nil)
         .build()
  
  // Defining an evaluator to evaluate the model
  val evaluator = new BinaryClassificationEvaluator()
         .setLabelCol("label")
         .setRawPredictionCol("prediction")

  // Set up 10-fold cross validation
  val numFolds = 10
  val crossval = new CrossValidator()
         .setEstimator(pipeline)
         .setEvaluator(evaluator)
         .setEstimatorParamMaps(paramGrid)
         .setNumFolds(numFolds)

  // Training the model using cross validation       
  val cvModel = crossval.fit(Preprocessing.trainDF)   
  
  // What is the best model?
  val bestModel = cvModel.bestModel
  println("The Best Model and Parameters:\n--------------------")
  println(bestModel.asInstanceOf[org.apache.spark.ml.PipelineModel].stages(3))

  bestModel.asInstanceOf[org.apache.spark.ml.PipelineModel]
            .stages(3)
            .extractParamMap

  val treeModel = bestModel.asInstanceOf[org.apache.spark.ml.PipelineModel]
                .stages(3)
                .asInstanceOf[DecisionTreeClassificationModel]

  println("Learned classification tree model:\n" + treeModel.toDebugString)      
  println("Feature 11:" + Preprocessing.trainDF.select(PipelineConstructor.featureCols(11)))
  println("Feature 3:" + Preprocessing.trainDF.select(PipelineConstructor.featureCols(3)))
  
  // Evaluate the DT model
  val predictions = cvModel.transform(Preprocessing.testSet)
  predictions.show(10)

  val result = predictions.select("label", "prediction", "probability")
  val resutDF = result.withColumnRenamed("prediction", "Predicted_label")
  resutDF.show(10)

  val accuracy = evaluator.evaluate(predictions)
  println("Accuracy: " + accuracy)
  evaluator.explainParams()
  
  /* To observe the area under the precision-recall curve and the
   * area under the ROC curve based on the following RDD 
   * containing the raw scores on the test set:*/
  val predictionAndLabels = predictions
          .select("prediction", "label")
          .rdd.map(x => (x(0).asInstanceOf[Double], x(1)
          .asInstanceOf[Double]))
  
  val metrics = new BinaryClassificationMetrics(predictionAndLabels)
  val areaUnderPR = metrics.areaUnderPR
  println("Area under the precision-recall curve: " + areaUnderPR)

  val areaUnderROC = metrics.areaUnderROC
  println("Area under the receiver operating characteristic (ROC) curve: " + areaUnderROC)

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
  
  // The evaluation returns 87% accuracy but only 73% precision.
  
  // Now we can investigate id with an ensemble model, like Random Forest we can improve our results.
  // The next model that will test is the Random Forest Model. 
  
  
}