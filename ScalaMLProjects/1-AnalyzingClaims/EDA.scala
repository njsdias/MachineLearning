package com.ctw.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._

object EDA { 
  
  def createSession(): SparkSession = { 
    val spark = SparkSession 
      .builder 
      .master("local[*]")                            // adjust accordingly 
      .config("spark.sql.warehouse.dir", "C:/temp/") //change accordingly 
      .appName("EDA")                                //change accordingly 
      .getOrCreate() 
    return spark 
    }
 
  def main(args: Array[String]) {

  // Set the log level to only print errors
  Logger.getLogger("org").setLevel(Level.ERROR)
    
  val spark = createSession()
  import spark.implicits._
  val train = "data/insurance_train.csv"
  
  val trainInput = spark.read
    .option("header", "true")
    .option("inferSchema", "true")
    .format("com.databricks.spark.csv")
    .load(train)
    .cache
   
   // You can see that there are 116 categorical columns for categorical features. 
   // Also, there are 14 numerical feature columns.
   println(trainInput.printSchema())
   
   val df = trainInput 
   val rows = df.count()
   val cols = df.columns.size
   
   println(s"Columns: $cols")      // how many columns?
   println(s"Rows: $rows")         // how many rows?
   
   //Show a snapshot of dataset with a selected columns
   println()
   println("Show a snapshot of dataset with a selected columns")
   df.select("id", 
       "cat1", "cat2", "cat3", 
       "cont1", "cont2", "cont3",
       "loss").show()
       
   //Rename the last column from "loss" to "label" because de ML model  
    val newDF = df.withColumnRenamed("loss","label")
    
   /* Create a temporary view to execute SQL query so that the operation
    * can be performed in memory */
    newDF.createOrReplaceTempView("insurance")
    
   // Average the damage claimed by the clients
    println()
    println(" Average the damage claimed by the clients")
    spark.sql("SELECT avg(insurance.label) as AVG_LOSS FROM insurance").show()

   // the lowest claim 
    println()
    println("the lowest claim")
    spark.sql("SELECT min(insurance.label) as MIN_LOSS FROM insurance").show()
    
    // the highest claim 
    println()
    println("the highest claim")
    spark.sql("SELECT max(insurance.label) as MAX_LOSS FROM insurance").show()
    
    
    
    
    
    
    
    
    
    
       
       
       
  }
 
} 
