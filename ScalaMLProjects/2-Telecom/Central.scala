package com.ctw.spark

import org.apache.log4j._

object Central {
      def main(args: Array[String]) {
      
      // Set the log level to only print errors and warn
      Logger.getLogger("org").setLevel(Level.ERROR)
      Logger.getLogger("org").setLevel(Level.WARN)
  
      // calling objects
 //     EDA                          //uncomment for EDA 
 //     Preprocessing               //uncomment for Preprocessing 
 //     PipelineConstructor         //uncomment for PipelineConstructor
      
      /* You don't need uncomment Preprocessing and Pipeline because
       * they are used directly by the Prediction objects */
      
      ChurnPredictionLR
      ChurnPredictionSVM
      ChurnPredictionDT
      ChurnPredictionRF
      RFModelReuse               // Random Forest model was selected as the best model and now here we are using it for the test data
      
      
      } 
   
}