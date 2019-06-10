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

![correl_features](https://user-images.githubusercontent.com/37953610/59215323-e1c80d80-8bb0-11e9-9adf-943c517e44bf.JPG)

