package com.ctw.spark

import org.apache.spark.sql.SparkSession

object SparkSessionCreate {
    def createSession(): SparkSession = {
    val spark = SparkSession
      .builder
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "C:/temp/")
      .appName(s"ChurnPredictionLogisticRegression")
      .getOrCreate()

    return spark
  }
}