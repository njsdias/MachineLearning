package com.ctw.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._

import org.apache.spark.ml.feature.{ StringIndexer, StringIndexerModel}
import org.apache.spark.ml.feature.VectorAssembler

object Preprocessing {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
  
    //Read training and test file
    var trainSample = 1.0
    var testSample = 1.0
    val train = "data/insurance_train.csv"
    val test = "data/insurance_test.csv"
  
    val spark = SparkSessionCreate.createSession()
    import spark.implicits._

    /* If you don't specify the inferSchema configuration explicitly,
  	* the float values will be treated as strings.*/
    val trainInput = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .format("com.databricks.spark.csv")
      .load(train)
      .cache
    
    val testInput = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .format("com.databricks.spark.csv")
      .load(test)
      .cache
  
     /* Then the content of train.csv was split into training and (cross) 
   		* validation data, 75% and 25%, respectively. The content of test.csv is 
   		* used for evaluating the ML model. Both original DataFrames are also sampled, 
   		* which is particularly useful for running fast executions on your local machine:*/  
    
    // renamed the column loss to label  
    println("Preparing data for training model")
    var data = trainInput.withColumnRenamed("loss", "label").sample(false,trainSample)
  
    // null checking . Dropping row if some null value is in any column
    var DF = data.na.drop()
    if (data == DF)
      println("No null values in the DataFrame")
    else{
      println("Null values exist in the DataFrame")
      data = DF
    }
  
    // Simple data in training and validation 
    val seed = 12345L
    val splits = data.randomSplit(Array(0.75, 0.25), seed)
    val (trainingData, validationData) = (splits(0), splits(1))
    
    //cache both the sets for faster in-memory access
    trainingData.cache
    validationData.cache
    
    /* Perform the sampling of the test set that will be required in the
     *	evaluation step */
    val testData = testInput.sample(false, testSample).cache
    
    // Identification of categorical and numerical variables 
    def isCateg(c: String): Boolean = c.startsWith("cat")
    def categNewCol(c: String): String = if (isCateg(c)) s"idx_${c}" else c
    
    /* Remove categorical columns with too many categories,
     *  to avoid the skewness of the data */
    def removeTooManyCategs(c: String): Boolean = !(c matches "cat(109$|110$|112$|113$|116$)")

    // Function to select only feature columns (omit id and label)
    def onlyFeatureCols(c: String): Boolean = !(c matches "id|label")
    
    // Construct the Definitive set of feature columns
    val featureCols = trainingData.columns
      .filter(removeTooManyCategs)
      .filter(onlyFeatureCols)
      .map(categNewCol)
      
    // StringIndexer for categorical columns (OneHotEncoder should be evaluated as well)
    val stringIndexerStages = trainingData.columns.filter(isCateg)
      .map(c => new StringIndexer()
      .setInputCol(c)
      .setOutputCol(categNewCol(c))
      .fit(trainInput.select(c).union(testInput.select(c))))
    
   // Use the VectorAssembler() a given list of columns into a single vector column: 
   // for training features
    val assembler = new VectorAssembler()
      .setInputCols(featureCols)
      .setOutputCol("features")
    
  
}