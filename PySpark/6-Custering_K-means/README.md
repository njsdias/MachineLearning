# 1. Clustering

Until now we have seen supervised Machine Learning where the target variable or label is known to us, and we try to predict the output based on the input features. 

Unsupervised Learning is different in a sense that there is no labeled data, and we don’t try to predict any output
as such; instead we try to find interesting patterns and come up with groups within the data. The similar values are grouped together.

We can apply clustering on any sort of data where we want to form groups
of similar observations and use it for better decision making. When we start clustering, each observation is different and doesn’t
belong to any group but is based on how similar are the attributes of each observation. We group them in such a way that each group contains the most similar records, and there is as much difference as possible between any two groups. The similarity between observation can be obtained by:

- Euclidean distance

- Manhattan Distance

- Mahalanobis Distance

- Minkowski Distances

- Chebyshev Distance

- Cosine Distance

# 2. K-means

‘K’ stands for a number of clusters or groups that we want to form in the
given dataset. This type of clustering involves deciding the number of
clusters in advance.

Before looking in detailed the above steps we need define _centroid_ and _variance_:

1- **Centroid**: Refers to the center data point at the center of a cluster or a group. Each cluster or group contains different number of data points that are nearest to the centroid of the cluster. Once the individual data points change clusters, the centroid value of the cluster also changes. The center position within a group is altered, resulting in a new centroid. The whole idea of clustering is to minimize intracluster distance, that is, the internal distance of data points from the centroid of cluster and
maximize the intercluster distance, that is, between the centroid of two different clusters.

2- **Variance**: Variance is the total sum of intracluster distances between centroid
and data points within that cluster. The variance keeps on decreasing with an increase in the number of clusters. The more
clusters, the less the number of data points within each cluster and hence less variability.

The algortihm of K-means is compounded by:

- 1. To decide the number of clusters (K): Most of the time, we are
not sure of the right number of groups at the start, but we can find the best
number of clusters using a method called the **Elbow method** based on variability

- 2. Initiate the centroids localization by random: We need to specify the initial centroid points. We are free to choose any localization because the algorithm is capable to find the right localization of clustroids at the end. 

- 3. Assign the cluster number of each value: we calculate the distance of each point from the centroid. Based on the distance value, we go ahead and decide which particular cluster the point belongs to. Whichever centroid the point is near to (less distance) would become part of that cluster.

- 4. Update the centroids and reassign the clusters: to calculate the new centroids of clusters and reassign the clusters to each value based on the distance from new centroids. With centroids of each cluster, we repeat step 3 of calculating the distance of each user from new centroids and find out the nearest centroid. We keep repeating the above steps until there is no more change in
cluster allocations.

The elbow method helps us to measure the total variance in the data
with a number of clusters and so select the number of clusters. The higher the number of clusters, the less the
variance would become. If we have an equal number of clusters to the
number of records in a dataset, then the variability would be zero because
the distance of each point from itself is zero. The variability or SSE (Sum of
Squared Errors) along with ‘K’ values.

Next grahp shows there is sort of elbow formation between K values
of 3 and 4. There is a sudden reduction in total variance (intra-cluster
difference), and the variance sort of declines very slowly after that. In fact,
it flattens after the K=9 value. So, the value of K =3 makes the most sense
if we go with the elbow method as it captures the most variability with a
lesser number of clusters.

![elbow](https://user-images.githubusercontent.com/37953610/59855209-7c241000-936c-11e9-8c6f-d8d94c997aba.JPG)

# 3. Hierarchical Clustering

By this method we don’t have to know the number of clusters in advance. There are two types of Hierarchical
clustering:

• Agglomerative Clustering (Bottom-Up Approach)

• Divisive Clustering (Top-Down Approach)





































