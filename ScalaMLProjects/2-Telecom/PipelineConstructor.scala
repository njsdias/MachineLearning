package com.ctw.spark

import org.apache.spark.ml.feature.{ StringIndexer, StringIndexerModel}
import org.apache.spark.ml.feature.VectorAssembler


object PipelineConstructor {
  
  
  
  /*Now, we need to create a pipeline to pass the data through and chain several transformers
   * and estimators. The pipeline then works as a feature extractor. More specifically, we have
   * prepared two StringIndexer transformers and a VectorAssembler.*/
  
 /* The first StringIndexer converts the String categorical feature "international_plan"
  * and "labels" into number indices. The second StringIndexer converts the categorical label
  * (that is, churn) to numeric.*/
  val ipindexer = new StringIndexer()
    .setInputCol("international_plan")   //International_Plan have values YES and NO
    .setOutputCol("iplanIndex")          // Transform to Zeros and Ones
    
  val labelindexer = new StringIndexer()
    .setInputCol("churn")                // Churn have values TRUE and FALSE
    .setOutputCol("label")               //  Transform to Zeros and Ones
    
 /*To extract the most important features that contribute to the classification.*/
  val featureCols = Array(
      "account_length", 
      "iplanIndex", 
      "num_voice_mail",
      "total_day_mins",
      "total_day_calls",
      "total_evening_mins",
      "total_evening_calls",
      "total_night_mins",
      "total_night_calls",
      "total_international_mins",
      "total_international_calls",
      "total_international_num_calls")

  /* Now, let's transform the features into feature vectors, which are vectors of numbers
   * representing the value for each feature.*/
 val assembler = new VectorAssembler()
      .setInputCols(featureCols)
      .setOutputCol("features") 
    
  
}