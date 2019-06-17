### 1. Some Considerations


To understand logistic regression, we will have to go over the concept of
Probability first. It is defined as the chances of occurrence of a desired
event or interested outcomes upon all possible outcomes.

Logistic regression is used to predict the probability of each target class (**categorical variables**).
In case of binary classification (only two classes), it returns the probability
associated with each class for every record. It uses linear
regression behind the scenes in order to capture the relationship between
input and output variables, yet we additionally use one more element
(nonlinear function) to convert the output from continuous form into
probability.
     
To accomplish the objective of converting the output value in probability,
we use something called Logit. Logit is a nonlinear function and does
a nonlinear transformation of a linear equation to convert the output
between 0 and 1. In logistic regression, that nonlinear function is the
**Sigmoid function**:

$\frac{1}{1+e^x}$,

and it always produces values between 0 and 1 independent of the
values of x. Here, x is our linear regression equation, that gives us the output with TimeSpent input data. 

y=B0 + B1 * TimeSpent .

The coefficients of the input variables are found using a technique known
as **gradient descent**, which looks for optimizing the loss function in such a
way that the total error is minimized.

To convert the ctaegorical data (string) in the numerical data (that allow be used by Machine Learning algortihms) there are
some techniques to do this convertion. One of them is the dummification or **One Hot Encoding**. The steps below explain the entire
process of converting a categorical feature into a numerical one.

- 1. Find out the distinct number of categories in the
categorical column. We have only three distinct
categories as of now (Google, Bing, Yahoo).

- 2. Create new columns for each of the distinct
categories and add value 1 in the category
column for which it is present or else 0 as shown
in Table.

![one-hot](https://user-images.githubusercontent.com/37953610/59624014-96bc7600-912d-11e9-9383-23acacea54c3.JPG)

- 3. Remove the original categories column. So,
the dataset now contains five columns in total
(excluding index) because we have three additional
dummy variables as shown in Table.

![dummification](https://user-images.githubusercontent.com/37953610/59624033-a6d45580-912d-11e9-86b9-02ceb3166a00.JPG)

The model is evaluated by the **F1-Score metric** which use Precision and Recall calculations. The evaluation results can be analyzed by the **ROC Curve**. 

![con_matrix](https://user-images.githubusercontent.com/37953610/59624068-bce21600-912d-11e9-98b1-c29075665d7a.JPG)


![ROC_Curve](https://user-images.githubusercontent.com/37953610/59624100-ce2b2280-912d-11e9-8953-f4df25085a3c.JPG)
