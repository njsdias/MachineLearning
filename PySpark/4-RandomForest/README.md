# 1. Random Forest

This chapter focuses on building Random Forests (RF) with PySpark for
classification.

A RFt is comprised of a set of Decision Trees (DT), each of which is trained on a random subset of the training data. These trees predictions can then be aggregated to provide a single prediction from a series of predictions.

A DT is also used for Classification/Regression. But in terms of accuracy, RF beat DT classifiers.

A decision tree falls under the supervised category of machine learning
and uses frequency tables for making the predictions. One advantage of a
decision tree is that it can handle both categorical and numerical variables.
As the name suggests, it operates in sort of a tree structure and forms these
rules based on various splits to finally make predictions.

The topmost split node from where the tree branches out is known
as the root node; in the above example Age is the root node. The values
represented in circles are known as leaf nodes or predictions.

![DT-structure](https://user-images.githubusercontent.com/37953610/59772979-b6c27580-92a4-11e9-8c53-e18b79f4485d.JPG)

The decision tree makes subsets of this data in such a way that each
of those subsets contains the same class values (homogenous); and
to calculate homogeneity, we use something known as **Entropy**. This
can also be calculated using couple of other metrics like the **Gini Index**
and **Classification error**. 

The **Information Gain (IG)** is used to make the splits in decision trees.
The attribute that offers the maximum information gain is used for splitting the
subset. Information gain tells which is the most important feature out of
all in terms of making predictions. Tis process continues recursively and further splits are made in the
decision tree.

There are sets of Hyperparameters associated with decision trees. One of those is Max Depth, which allows
us to decide the depth of a decision tree; the deeper the tree, the more slits
the tree has and there are chances of **overfitting**.

**Random Forest** combine votes from a lot of
individual Decision Trees and then predict the class with majority votes
or take the average in case of regression. This works really well because
**the weak learners eventually group together to make strong predictions**.
The name "Random" is there for a reason in RF because the trees are formed with a
random set of features and a random set of training examples. 
Each of these decision trees has used a subset of data to get
trained as well as a subset of features. This is also known as the “Bagging”
technique – Bootstrap aggregating. Each tree sort of votes regarding
the prediction, and the class with the maximum votes is the ultimate
prediction by a random forest classifier.

Some of the advantages that random forests offers are:

- Feature Importance:

- Increased Accuracy

- Less Overfitting
