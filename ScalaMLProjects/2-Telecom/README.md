### 1. Analyzing and Predicting Telecommunication Churn

Churn prediction is fundamental to businesses, as it allows them to detect customers who
are likely to cancel a subscription, product, or service. It can also minimize customer
defection. It does so by predicting which customers are likely to cancel a subscription to a
service. Then, the respective business can have a special offer or plan for those customers
(who might cancel the subscription). This way, a business can reduce the churn ratio. This
should be a key business goal of every online business.

When it comes to employee churn prediction, the typical task is to determine what factors
predict an employee leaving his/her job. For this, a
number of factors should be analyzed in order to understand the customer's behavior,
including but not limited to:

- Customer's demographic data, such as age, marital status, and so on

- Customer's sentiment analysis of social media

- Browsing behavior from clickstream logs

- Historical data that shows patterns of behavior that suggest churn

- Customer's usage patterns and geographical usage trends

- Calling-circle data and support call center statistics

The churn-80 and churn-20 datasets can be downloaded from the following links, respectively:

- https:/ / bml- data. s3. amazonaws. com/ churn- bigml- 80. csv
- https:/ / bml- data. s3. amazonaws. com/ churn- bigml- 20. csv

The dataset has the following schema:

- State: String
- Account length: Integer
- Area code: Integer
- International plan: String
- Voicemail plan: String
- Number email messages: Integer
- Total day minutes: Double
- Total day calls: Integer
- Total day charge: Double
- Total eve minutes: Double
- Total eve calls: Integer
- Total eve charge: Double
- Total night minutes: Double
- Total night calls: Integer
- Total night charge: Double
- Total intl minutes: Double
- Total intl calls: Integer
- Total intl charge: Double
- Customer service calls: Integer

By inspection of the features we can see which are the correlations. This help us to dismiss the one of the pair variables that are directly correlated or don't have a smooth distribution.

![correl_features](https://user-images.githubusercontent.com/37953610/59216000-96166380-8bb2-11e9-9050-81b76c391b78.JPG)

By the above graph we considere only the next features for our model:

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
      
The first ML algortihm apply to the dataset is the **Logistic Regression** that is widely used classifiers to predict a binary response. The model responses are measures of probability. After see the accuracy of the model was 77% and the precision was 58% we need to proceed with testing other models. 

The next model tested was **Support Vector Machine (SVM)** which is also used widely for large-scale classification (that is, binary as well as multinomial) tasks. The linear SVMs in Spark are trained with an L2 regularization, by default. However, it also
supports L1 regularization, by which the problem itself becomes a linear program. The results were worst than LR: 75% and 56%. 

The next ML algorithm is **Decision Trees** that are commonly considered a supervised learning technique used for solving
classification and regression tasks. One of several main observation is that: 

- the deeper the tree, the more complex the decision rules and the more fitted the model is.

![dt_pros_cons](https://user-images.githubusercontent.com/37953610/59222160-c6fd9500-8bc0-11e9-9a50-84adf33cd8d4.JPG)

We identify the most important features of the best model which are used for decision making. So, the two most important reasons why a customer is likely to churn are: 

- total_international_num_calls

- total_day_mins

The evaluation returns 87% accuracy but only 73% precision. We can try use RandomForest, XgBoost ML Algortihms and see we can to obtain a better models.

So, the next model that we can to try is **Random Forest**. Random Forest is an
ensemble technique that takes a subset of observations and a subset of variables to build
decision trees—that is, an ensemble of Decision Trees. We obtained the same result as Decision Trees: Accuracy: 87% and Precision: 73%. 

The next figure we compare the four models by a pie-charts, amalysing the maount of: True Positive, True NEgative, False Positive and False Negative. We can see the best model is the Random Forest Model. To deploy the model

![model_comparasion](https://user-images.githubusercontent.com/37953610/59225600-11830f80-8bc9-11e9-99cb-49519344c751.JPG)


For **deploying the model** see the RFModelReuse.scala .


