# 1. Analyzing Insurance Severity Claims

Chapter 1, Analyzing Insurance Severity Claims, shows how to develop a predictive model
for analyzing insurance severity claims using some widely used regression techniques. We
will demonstrate how to deploy this model in a production-ready environment.

# Structure of the code

To develope a pipeline with Preprocessing and Machine Learning Algorithm we have two options:

- 1: Put all in one file.scala inside of a _def main_ and run it

- 2: Split the actions by diffrent file.scala: Preprocessing.scala, MLAlg.scala

It is a very personal choice. Here I chosse the second option. For that was created an object with the _def main_ wich have the load sequence of the scala files. This main file has the name _Central.scala_ which call the other scala files in an appropriate seqeunce. For this reason the other scala file dont have the _def main_ function. So, for execute the code you only need to run the Central.scala.  

# Exploratory analysis of the dataset: EDA.scala file

The dataset can be downloaded in: https://www.kaggle.com/c/allstate-claims-severity/data

In this file we can see:

- how can structure a Spark.scala code

- Read data

- Analyse data with SQL queries and Spark.


# Preprocessing : Preprocessing.scala file

In this file we can see how

- clean rows with null values in columns

- split data into training and validation

- construct data set to model 

  - select only features columns
  
  - use StringIndexer to encode a given string column of labels to a column of label indices in categorical feature.
  
    - OneHotEncoder maps a column of label indices to a column of binary
      vectors, with a single one-value at most. This encoding permits algorithms
      that expect continuous features, such as logistic regression, to utilize
      categorical features.

- transform a list of columns in a vector

  - VectorAssembler is a transformer. It combines a given list of columns
    into a single vector column. It is useful for combining the raw features and
    features generated by different feature transformers into one feature
    vector, in order to train ML models such as logistic regression and
    decision trees.
    
 **ML: Linear Regression: for predicting insurance severity claims**
 
The goal of regression is to find relationships and dependencies between variables. It models the
relationship between a continuous scalar dependent variable _y_ (that is, label or target) and
one or more (a D-dimensional vector) explanatory variable (also independent variables,
input variables, features, observed data, observations, attributes, dimensions, and data
points) denoted as _x_ using a linear function. 

Note that there are no single **metrics** in terms of regression errors; there are several as follows:

- Mean Squared Error (MSE): It is a measure of how close a fitted line is to data
points. The smaller the MSE, the closer the fit is to the data.
Root Mean Squared Error (RMSE): It is the square root of the MSE but probably
the most easily interpreted statistic, since it has the same units as the quantity
plotted on the vertical axis.

- R-squared: R-squared is a statistical measure of how close the data is to the fitted
regression line. R-squared is always between 0 and 100%. The higher the Rsquared,
the better the model fits your data.

- Mean Absolute Error (MAE): MAE measures the average magnitude of the
errors in a set of predictions without considering their direction. It's the average
over the test sample of the absolute differences between prediction and actual
observation where all individual differences have equal weight.

- Explained variance: In statistics, explained variation measures the proportion to
which a mathematical model accounts for the variation of a given dataset.


**Spark ML pipelines** have the following components:

- DataFrame: Used as the central data store where all the original
data and intermediate results are stored.

- Transformer: A transformer transforms one DataFrame into
another by adding additional feature columns. Transformers are
stateless, meaning that they don't have any internal memory
and behave exactly the same each time they are used.

- Estimator: An estimator is some sort of ML model. In contrast to
a transformer, an estimator contains an internal state
representation and is highly dependent on the history of the
data that it has already seen.

- Pipeline: Chains the preceding components, DataFrame,
Transformer, and Estimator together.

- Parameter: ML algorithms have many knobs to tweak. These
are called hyperparameters, and the values learned by a ML
algorithm to fit data are called parameters.


**Note**
The preceding code should generate a CSV file named result_LR.csv. If we open the file,
we should observe the loss against each ID, that is, claim. We will see the contents for both
LR, RF, and GBT at the end of this chapter.

# GBT regressor for predicting insurance severity claims

In order to minimize a loss function, Gradient Boosting Trees (GBTs) iteratively train
many decision trees. On each iteration, the algorithm uses the current ensemble to predict
the label of each training instance.

Then the raw predictions are compared with the true labels. Thus, in the next iteration, the
decision tree will help correct previous mistakes if the dataset is re-labeled to put more
emphasis on training instances with poor predictions.

Now, similar to decision trees, GBTs also:

- Handle categorical features (and of course numerical features too)

- Extend to the multiclass classification setting

- Perform both the binary classification and regression (multiclass classification is
not yet supported)

- Do not require feature scaling

- Capture non-linearity and feature interactions, which are greatly missing in LR,
such as linear models

**Validation while training:** Gradient boosting can overfit, especially when
you have trained your model with more trees. In order to prevent this
issue, it is useful to validate while carrying out the training.

# Random Forest for classification and regression

Random Forest is an ensemble learning technique used for solving supervised learning
tasks, such as classification and regression. An advantageous feature of Random Forest is
that it can overcome the overfitting problem across its training dataset. A forest in Random
Forest usually consists of hundreds of thousands of trees. These trees are actually trained on
different parts of the same training set.

More technically, an individual tree that grows very deep tends to learn from highly
unpredictable patterns. This creates overfitting problems on the training sets. Moreover,
low biases make the classifier a low performer even if your dataset quality is good in terms
of the features presented. On the other hand, an Random Forest helps to average multiple
decision trees together with the goal of reducing the variance to ensure consistency by
computing proximities between pairs of cases.

**GBT or RF?**

- GBTs train one tree at a time, but Random Forest can train
multiple trees in parallel. So the training time is lower for RF.
However, in some special cases, training and using a smaller
number of trees with GBTs is easier and quicker.

- RFs are less prone to overfitting in most cases, so it reduces the
likelihood of overfitting. In other words, Random Forest
reduces variance with more trees, but GBTs reduce bias with
more trees.

- Finally, Random Forest can be easier to tune since performance
improves monotonically with the number of trees, but GBT
performs badly with an increased number of trees.

**RF parameteres to setting**

Here is the list and it is highly recommended that you search for more detailed information:

- number of trees

- impurity criterion

- the maximum depth of the tree

- the maximum number of bins used for splitting the features

- the random seed is used for bootstrapping and choosing feature subsets to avoid the random nature of the results.

# Analyse of results

Use python we can extract some insigths from plots related to the Importance of Categorcal features and the Correlation among Continuos features. By graphs we can observe that

- categorical variavels: 20,64,47,70,69 are less important for the model

- all continuos variables have positive correlation with the loss column witch indicates that are not that
important compared to the categorical ones we have seen in the preceding figure.

So after naively drop some unimportant columns we need train the Random Forest model to observe if there is any reduction in the MAE
value for both the training and validation set.

    println("Run prediction on the test set")
    cvModel.transform(Preproessing.testData)
      .select("id", "prediction")
      .withColumnRenamed("prediction", "loss")
      .coalesce(1) // to get all the predictions in a single csv file
      .write.format("com.databricks.spark.csv")
      .option("header", "true")
      .save("output/result_RF.csv")
      
The file result_RF.csv contains the loss against each ID, that is, claim.

![cat_import](https://user-images.githubusercontent.com/37953610/59121994-72a0ae00-8951-11e9-83fa-a3439d4ee403.JPG)

![corr_cont](https://user-images.githubusercontent.com/37953610/59121998-759b9e80-8951-11e9-94f7-0f8db7c623bb.JPG)

## Model Deployment
The idea is, as a data scientist, you may have produced an ML model and handed it over to an engineering team in your company for deployment in a productionready environment.

This scenario can easily become a reality by using model persistence—the ability to save and
load models that come with Spark. Using Spark, you can either:

- Save and load a single model

- Save and load a full pipeline

A single model is pretty simple, but less effective and mainly works on Spark MLlib-based
model persistence. Since we are more interested in saving the best model, that is, the
Random Forest regressor model, at first we will fit an Random Forest regressor using Scala,
save it, and then load the same model back using Scala:

    // Estimator algorithm
    val model = new RandomForestRegressor()
      .setFeaturesCol("features")
      .setLabelCol("label")
      .setImpurity("gini")
      .setMaxBins(20)
      .setMaxDepth(20)
      .setNumTrees(50)

    fittedModel = rf.fit(trainingData)
    
We can now simply call the _write.overwrite().save()_ method to save this model to
local storage, HDFS, or S3, and the load method to load it right back for future use:

    fittedModel.write.overwrite().save("model/RF_model")
    val sameModel = CrossValidatorModel.load("model/RF_model")
    
Now the thing that we need to know is how to use the restored model for making
predictions. Here's the answer:

    sameModel.transform(Preproessing.testData)
      .select("id", "prediction")
      .withColumnRenamed("prediction", "loss")
      .coalesce(1)
      .write.format("com.databricks.spark.csv")
      .option("header", "true")
      .save("output/result_RF_reuse.csv")
      
The reality is that, in practice, ML workflows consist of many stages, from feature extraction
and transformation to model fitting and tuning. Spark ML provides pipelines to help users
construct these workflows. Similarly, a pipeline with the cross-validated model can be
saved and restored back the same way as we did in the first approach.

We fit the cross-validated model with the training set:

    val cvModel = cv.fit(Preproessing.trainingData)

Then we save the workflow/pipeline:

    cvModel.write.overwrite().save("model/RF_model") 
   
Then we restore the same model back:

    val sameCV = CrossValidatorModel.load("model/RF_model")
Now when you try to restore the same model, Spark will automatically pick
the best one. Finally, we reuse this model for making a prediction as
follows:

    sameCV.transform(Preproessing.testData)
      .select("id", "prediction")
      .withColumnRenamed("prediction", "loss")
      .coalesce(1)
      .write.format("com.databricks.spark.csv")
      .option("header", "true")
      .save("output/result_RF_reuse.csv")
      
 For info about _Spark-based model deployment for large-scale
dataset_ see Spark website at _https:/ / spark. apache. org/ docs/ latest/ submittingapplications_ .
html.
