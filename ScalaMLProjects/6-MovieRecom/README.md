# 1. Developing Model-based Movie Recommendation Engines (RE)

In this chapter, we will see two end-to-end projects and develop a model for **item-based
collaborative filtering** for movie similarity measurement and a **model-based movie
recommendation engine** with Spark that recommends movies for new users. We will see
how to interoperate between ALS and matrix factorization (MF) for these two scalable
movie recommendation engines. We will use the movie lens dataset for the project. Finally,
we will see how to deploy the best model in production.

In a nutshell, we will learn the following topics through two end-to-end projects:

- Recommendation system: how and why?

- Item-based collaborative filtering for movie similarity

- Model-based movie recommendation with Spark

- Model deployment

In short, a recommender system tries to predict potential items a user
might be interested in based on history for other users. There are a couple of ways to
develop recommendation engines that typically produce a list of recommendations, for
example, collaborative and content-based filtering or the personality-based approach.

A RE that use **collaborative filtering approaches** can be built based on a user's past behavior
where numerical ratings are given on purchased items. Sometimes, it can be developed on
similar decisions made by other users who also have purchased the same items. 
In the next figure we can see a comparative view of different recommendation systems.

![rec_syst](https://user-images.githubusercontent.com/37953610/59454372-cb68be80-8e09-11e9-91a5-fa282fc4449e.JPG)

Collaborative filtering-based approaches have three problems that can be overcome using Matrix Factorization (MF), a
low-rank matrix approximation technique:

- Cold Start: needs more users to accurate the recommendation systems

- Scalability: needs a large amount of computation power for dealing with datasets which have milions of users and products

- Sparsity: due to the milions of products many of them are rated by the users. As the result we obtain a large-scale sparse matrix. 

The **content-based filtering approaches** use a series of discrete characteristics of an item to recommend additional items with similar properties Sometimes it is based on a description of the item and a profile of the user's preferences. These approaches try to
recommend items that are similar to those that a user liked in the past or is using currently. When this type of RE is deployed, it can be used to predict items or ratings for items that the user is interested in.

The **hybrid recommender systems** are used to overcome the issues of the two last approaches.
Hybrid approaches can be implemented in several ways:

- Separately, computes the preditions using the Content-based approach and collaborative-based. After that combine thw two predictions using MF and Singular Value Decomposition (SVD)

- Adding content-based capabilities to a collaborative-based approach or vice
versa. Again, FM and SVD are used for better prediction.

**Netflix** is a good example that uses this hybrid approach to make a recommendation to its
subscribers. This site makes recommendations in two ways:

- Collaborative filtering: By comparing the watching and searching habits of
similar users.

- Content-based filtering: By offering movies that share characteristics with films
that a user has rated highly.

Here we are implement a Collaborative filtering based methods. They are classified as:

- Memory-based, that is, a user-based algorithm

- Model-based collaborative filtering, that is, kernel-mapping.

In this method, users and products are described by a small set of factors, also called **latent factors (LFs)**. The LFs are then used to predict the missing entries. The **Alternating Least Squares (ALS)** algorithm is used to learn these LFs.

In a **hybrid recommendation system**, there are two classes of entities: users and items.  
Now, as a user, you might have preferences for certain items. Therefore, these preferences must be extracted from data about items, users, or ratings. Often this data is represented as a **utility matrix**, such as a user-item pair. The entry in the matrix, that is, a table, can come from an ordered set. For example, integers 1-5 can be used to represent the number of stars that the user gave as a
rating for items. This matrix can be sparce as much user don't rated the products. The goal is to
predict the blanks in the utility matrix. 

For this project, we can use the **MovieLens 100k**
rating dataset from http://www.grouplens.org/node/73 . The training set ratings are in a
file called _ua.base_, while the movie item data is in _u.item_ . On the other hand, _ua.test_
contains the test set to evaluate our model.
