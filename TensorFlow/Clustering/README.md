### 1. Purpose
This folder is related only for clustering subject following the contents of Data Science and Machine Learning books.
In each chater will be cited the book that the info was collected to guied the user of this repository.


### 2. Clustering by "Building Machine Learning Project with TensorFlow"

In Machine Learning we have

- supervised: the data are labled and the new data is classified by the machine learning algorithm evaluating the probability of a new data to belong to a specific cluster  
- unsupervised: the data is unlabled and the new data is classified using the characteristics of the current data.


One of the simplest operations that can be initially to **unlabeled** dataset is to try to understand
the possible groups of the dataset members' common features.

To do so, the dataset can be split into an **arbitrary number of segments**, where each can be
represented as a **central mass (centroid) point** that represents the points belonging to a
determined group or cluster.

In order to **define the criteria** that assigns the same group to different group members, we
need to define a concept that represents the **distance between data elements**, so we can simply
say that all class members are closer to their own centroids than to any other centroid.

![exampclust](https://user-images.githubusercontent.com/37953610/57388249-b74cf400-71af-11e9-9e2c-73a63d5c8e0b.JPG)

### 2.1. Mechanism of k-means
**k-means** is a very popular clustering algorithm. It is very
straight forward, and applying it as a first procedure to datasets with good class separation
can provide a good a priori understanding of the data.

k-means tries to **divide** a set of samples **in k disjoint groups or clusters** using **the mean value
of the members as the main indicator**. This point is normally called a **Centroid**, referring to
the arithmetical entity with the same name, and is represented as a vector in a space of
arbitrary dimensions.

k-means is a **naive method** because it works by looking for the appropriate centroids but
**doesn't know a priori** what **the number of clusters is.**
In order to get an evaluation of **how many clusters give a good representation** of the provided
data, one of the more popular methods is the **Elbow method**.

**Algorithm iteration criterion**
The criterion and goal of this method is **to minimize the sum of squared distances** from the
cluster's member to the actual centroid of all cluster-contained samples. This is also known as
**Minimization of Inertia.**

![minsqr](https://user-images.githubusercontent.com/37953610/57387938-29710900-71af-11e9-9b53-7ee5241f83c8.JPG)


The **k-means algorithm** can be simplified as follows:

1. We **start with the unclassified samples** and **take k elements** as the starting centroids. There
are also possible simplifications of this algorithm that take the first elements in the
element list for the sake of brevity.
2. We then **calculate the distances** between the samples and the first chosen samples and get
the first, calculated centroids (or other representative values). You can see the centroids
in the illustration move toward a more common-sense centroid.
3. **After the centroids change**, their displacement will provoke the individual distances to
change, and so **the cluster membership can change**.
4. This is the time when **we recalculate the centroids** and **repeat the first steps** in case the
**stop condition isn't met**.

The **stopping conditions** could be of various types:

- After N iterations it could be that either we chose a very large number and we'll have
unnecessary rounds of computing, or it could converge slowly and we will have very
unconvincing results if the centroid doesn't have a very stable means. This stop condition
could also be used as a last resort, in case we have a very long iterative process.

- Referring to the previous mean result, a possible better criterion for the convergence of
the iterations is **to take a look at the changes of the centroids**, be it in total displacement
or total cluster element switches. The last one is employed normally, so **we will stop the
process** once there are no more elements changing from its current cluster to another
one.

The mechanism of the k-means algorithm can be summarized by the following flowchart:

![meckmeans](https://user-images.githubusercontent.com/37953610/57388112-78b73980-71af-11e9-898f-728464e63b2a.JPG)

### Pros and cons of k-means
The advantages of this method are:

- It scales very well (most of the calculations can be run in parallel)
- It has been used in a very large range of applications

But simplicity also comes with a cost (no silver bullet rule applies):
- It requires a-priori knowledge (the number of possible clusters should be known
beforehand)
- The outlier values can Push the values of the centroids, as they have the same value as
any other sample
- As we assume that the figure is convex and isotropic, it doesn't work very well with noncircle-
like delimited clusters

### 2.2. k-nn Algorithm
k-nearest neighbors (k-nn) is a supervised Machine Learning algorithm. k-nn can be implemented in more than one of configurations. One of them is using a Semi Supervised approach where we start with a certain number of already assigned
samples (labled), and we will later guess the cluster membership based on the characteristics of the
train set. 

The **k-nn algorithm** can be summarized by the following steps:

1. We place the **previously known samples** on the data structures.
2. We then **read the next sample to be classified** and **calculate the Euclidean distance** from
the new sample to every sample of the training set.
3. We decide the class of the new element by **selecting the class of the nearest sample** by
Euclidean distance. The k-nn method **requires the vote of the k closest samples.**
4. We **repeat the procedure** until there are no more remaining samples.

**Pros and cons of k-nn:**
The advantages of this method are:

- Simplicity; no need for tuning parameters
- No formal training; we just need more training examples to improve the model

**The disadvantages:**
- Computationally expensive (All distances between points and every new sample have to
be calculated)

In **k-means**, the initial centers drifted toward the areas that had the most concentrated
sample numbers, and so divided the data linearly. This is **one of the limitations of the simple
models.** To cope with nonlinear separability samples, we can
try other statistical approaches, such as density-based spatial
clustering of applications with noise (**DBSCAN**).
