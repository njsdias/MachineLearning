package com.ctw.spark

import org.apache.log4j._

object Central {
    def main(args: Array[String]) {
      
      // Set the log level to only print errors and warn
      Logger.getLogger("org").setLevel(Level.ERROR)
      Logger.getLogger("org").setLevel(Level.WARN)
  
      // calling objects
      MovieSimilarities                          //uncomment for EDA 

      
      } 
}