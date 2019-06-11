package com.ctw.spark



import java.io.{ BufferedWriter, File, FileWriter }
import org.apache.spark.sql.types.{ DoubleType, IntegerType, StructField, StructType }
import org.apache.spark.sql.{ DataFrame, Row, SparkSession }
import scala.collection.mutable.ListBuffer


object Preprocess {
  
 //how many of first rows are omitted
    val dropFirstCount: Int = 612000

    def rollingWindow(data: DataFrame, window: Int, xFilename: String, yFilename: String): Unit = {
      
    /*This code transforms the original dataset into time series data. It takes the Delta values of
     * WINDOW_SIZE rows (22 in this experiment) and makes a new row out of them. In this way,
     * the first row has Delta values from t0 to t21, and the second one has values from t1 to t22. 
     * Then we create the corresponding array with labels (1 or 0).*/
      
      var i = 0
      val xWriter = new BufferedWriter(new FileWriter(new File(xFilename)))
      val yWriter = new BufferedWriter(new FileWriter(new File(yFilename)))

      val zippedData = data.rdd.zipWithIndex().collect()
      System.gc()
      val dataStratified = zippedData.drop(dropFirstCount) //todo slice fisrt 614K
      while (i < (dataStratified.length - window)) {
        val x = dataStratified
          .slice(i, i + window)
          .map(r => r._1.getAs[Double]("Delta")).toList
        val y = dataStratified.apply(i + window)._1.getAs[Integer]("label")
        val stringToWrite = x.mkString(",")
        xWriter.write(stringToWrite + "\n")
        yWriter.write(y + "\n")

        i += 1
        if (i % 10 == 0) {
          xWriter.flush()
          yWriter.flush()
        }
      }

      xWriter.close()
      yWriter.close()
    }
    
  def main(args: Array[String]): Unit = {
    //todo modify these variables to match desirable files
    val priceDataFileName: String = "data/bitstampUSD_1-min_data_2012-01-01_to_2017-10-20.csv"
    val outputDataFilePath: String = "data/output/scala_test_x.csv"
    val outputLabelFilePath: String = "data/output/scala_test_y.csv"

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "C:/Temp/")
      .appName("Bitcoin Preprocessing")
      .getOrCreate()

    val data = spark.read.format("com.databricks.spark.csv").option("header", "true").load(priceDataFileName)
    data.show(10)
    println((data.count(), data.columns.size))

    /* create the Delta column, containing the difference between closing and opening
    prices (that is, to consider only that data where meaningful trading has started to occur):*/
    val dataWithDelta = data.withColumn("Delta", data("Close") - data("Open"))

    import org.apache.spark.sql.functions._
    import spark.sqlContext.implicits._

    /*labels our data by assigning 1 to the rows the Delta value of which was
     * positive; it assigns 0 otherwise*/
    val dataWithLabels = dataWithDelta.withColumn("label", when($"Close" - $"Open" > 0, 1).otherwise(0))
    

    
    /*we save X and Y into files where 612000 rows were cut off from the original
     * dataset; 22 means rolling window size and 2 classes represents that labels are binary 0 and
     * 1:*/
    rollingWindow(dataWithLabels, 22, outputDataFilePath, outputLabelFilePath)    
    
    spark.stop()
  }
}