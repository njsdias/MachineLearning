package com.ctw.spark

// Libraries
import org.apache.spark._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.Dataset

object EDA {
  
  // Scala case class with all the fields specified
  case class CustomerAccount(state_code: String,
      account_length: Integer,
      area_code: String,
      international_plan: String,
      voice_mail_plan: String,
      num_voice_mail: Double,
      total_day_mins: Double,
      total_day_calls: Double,
      total_day_charge: Double,
      total_evening_mins: Double,
      total_evening_calls: Double,
      total_evening_charge: Double,
      total_night_mins: Double,
      total_night_calls: Double,
      total_night_charge: Double,
      total_international_mins: Double,
      total_international_calls: Double,
      total_international_charge: Double,
      total_international_num_calls: Double,
      churn: String)
  
  /* Create a custom schema having a structure similar 
   * to our already created data source */ 
  val schema = StructType(Array(
      StructField("state_code", StringType, true),
      StructField("account_length", IntegerType, true),
      StructField("area_code", StringType, true),
      StructField("international_plan", StringType, true),
      StructField("voice_mail_plan", StringType, true),
      StructField("num_voice_mail", DoubleType, true),
      StructField("total_day_mins", DoubleType, true),
      StructField("total_day_calls", DoubleType, true),
      StructField("total_day_charge", DoubleType, true),
      StructField("total_evening_mins", DoubleType, true),
      StructField("total_evening_calls", DoubleType, true),
      StructField("total_evening_charge", DoubleType, true),
      StructField("total_night_mins", DoubleType, true),
      StructField("total_night_calls", DoubleType, true),
      StructField("total_night_charge", DoubleType, true),
      StructField("total_international_mins", DoubleType, true),
      StructField("total_international_calls", DoubleType, true),
      StructField("total_international_charge", DoubleType, true),
      StructField("total_international_num_calls", DoubleType, true),
      StructField("churn", StringType, true)
  ))
  
  
  /* Spark session and import the implicit._ 
   * that enables us to specify a DataFrame operation */
  val spark: SparkSession = SparkSessionCreate.createSession()
  import spark.implicits._
  
  // >>>>> Training Dataset <<<<<
  /* Read the CSV file with Spark's recommended format, com.databricks.spark.csv 
   * We don't need any explicit schema inference, making the infer Schema false, 
   * but instead, we need our own schema we just created previously.*/
  val trainSet: Dataset[CustomerAccount] = spark.read.
        option("inferSchema", "false")
        .format("com.databricks.spark.csv")
        .schema(schema)
        .load("data/churn-bigml-80.csv")
        .as[CustomerAccount]
  
  /* If this dataset can be fit into RAM, we can cache it for
   * quick and repeated access using the cache() method from Spark: */
  trainSet.cache()
  
  // >>>>> Test Dataset <<<<<
  println()
  println(">>>>> Test Dataset <<<<<")
  val testSet: Dataset[CustomerAccount] =
      spark.read
      .option("inferSchema", "false")
      .format("com.databricks.spark.csv")
      .schema(schema)
      .load("data/churn-bigml-20.csv")
      .as[CustomerAccount]
  testSet.cache()
  
  println()
  println("Print out the schema")
  trainSet.printSchema()
  
  println()
  println("Print out the first 20 lines")
  trainSet.show()
  
  println("Print out the basic statistics")
  val statsDF = trainSet.describe()
  statsDF.show()
  
  //how the churn is related to the total number of international calls
  println()
  println("Churn vs international calls")
  trainSet.groupBy("churn").sum("total_international_num_calls").show()
  
  // how the churn is related to the total international call charges
  println()
  println("Churn vs international call charges")
  trainSet.groupBy("churn").sum("total_international_charge").show()  
    
  // create a temp view for persistence for this session.
  /* We can create a catalog as an interface that can be used to
   * create, drop, alter, or query underlying databases, tables, functions, and many more: */
  trainSet.createOrReplaceTempView("UserAccount")
  spark.catalog.cacheTable("UserAccount")
  
  // Grouping the data by the churn label and calculating the number of instances in each group
  println()
  println("How unbalance is the dataset?")
  trainSet.groupBy("churn").count().show()
  
  /* Here, we're keeping all instances of the True churn class, 
   * but downsampling the False churn class to a fraction of 388/2278, which is about 0.1675 */
  val fractions = Map("False" -> 0.1675, "True" -> 1.0)
  
  /*This way, we are also mapping only True churn samples. Now, let's create a new
   * DataFrame for the training set containing only downsampled ones: */
  val churnDF = trainSet.stat.sampleBy("churn", fractions, 12345L)
  println()
  println("Check the balanced dataset")
  churnDF.groupBy("churn").count.show()
  
 /*Let's see how the day, night, evening, and international voice calls contribute to the churn class*/ 
  println("Relation between variables and churn class")
  spark.sqlContext.sql("SELECT churn, SUM(total_day_mins) + SUM(total_evening_mins) + SUM(total_night_mins) + SUM(total_international_mins) as Total_minutes FROM UserAccount GROUP BY churn").show()
  
  println("how many minutes of day, night, evening, and international voice calls have contributed to the preceding total charge to the churn class")
  spark.sqlContext.sql("SELECT churn, SUM(total_day_charge) as TDC, SUM(total_evening_charge) as TEC, SUM(total_night_charge) as TNC, SUM(total_international_charge) as TIC, SUM(total_day_charge) + SUM(total_evening_charge) + SUM(total_night_charge) + SUM(total_international_charge) as Total_charge FROM UserAccount GROUP BY churn ORDER BY Total_charge DESC").show()
 
  trainSet.groupBy("churn").count.show()
  spark.sqlContext.sql("SELECT churn,SUM(total_international_num_calls) FROM UserAccount GROUP BY churn")
  
  //End of EDA- Go to Preprocessing
  
  spark.stop()
}