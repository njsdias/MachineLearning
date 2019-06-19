# 1. Recommender Systems

Recommender systems can be used for multiple purposes in the sense of
recommending various things to users.

1. Retail Products

2. Jobs

3. Connections/Friends

4. Movies/Music/Videos/Books/Articles

5. Ads

The role of Recommender Systems (RS) becomes critical for recommending relevant items and
driving customer conversion in situations where there are zilions of different products available since the user don’t like to invest a lot of time going through the entire catalogue of items.
     
Recommender systems are mainly used for auto-suggesting the right
content or product to the right users in a personalized manner to enhance
the overall experience. Each of the decisions of users (Positive or Negative) helps to retrain the
RS on the latest data to be able to give even better recommendations.   

RS take care of the critical
aspect that the product or content that is being recommended should either
be something which users might like but would not have discovered on their
own. A few examples of heavy usage of RS by
businesses today such as Amazon products, Facebook’s friend suggestions,
LinkedIn’s “People you may know,” Netflix’s movie, YouTube’s videos,
Spotify’s music, and Coursera’s courses. Some of the immediate benefits that RS offer in retail settings are:

1. Increased Revenue

2. Positive Reviews and Ratings by Users

3. Increased Engagement

There are mainly five types of RS that can be built:

1. Popularity Based RS

2. Content Based RS

3. Collaborative Filtering based RS

4. Hybrid RS

5. Association Rule Mining based RS

## Popularity Based RS

It recommends items/content based on bought/viewed/liked/downloaded by most of the users. While it is easy and simple to implement, it doesn’t produce relevant results as the recommendations stay the same for every user, but it sometimes outperforms some of the more sophisticated RS. The way this RS is implemented is by simply ranking the items on various parameters and recommending the top-ranked items in the list:

1. No. of times downloaded

2. No. of times bought

3. No. of times viewed

4. Highest rated

5. No. of times shared

6. No. of times liked

This kind of RS directly recommends the best-selling or most watched/
bought items to the customers and hence increases the chances of
customer conversion. The limitation of this RS is that it is not hyper-personalized.

## Content Based RS

It recommends similar items to the users that the user has liked in the past.  So, the whole idea is to **calculate a similarity score**
between any two items and recommended to the user based upon the profile of the user’s interests. We start with **creating item profiles** for each of the items. Now these item profiles can be created in multiple ways, but the most common approach is to include information regarding the details or attributes of the item. For an example, the item profile of a Movie can have values on various attributes such as Horror, Art, Comedy, Action, Drama, and Commercial.

The other component in content based RC is the **User Profile** that is created using item profiles that the user has liked or rated. The user profile might look like a single vector, which is simply the mean of item vectors but there are other sophisticated ways to create more enriched user profiles such as normalized values, weighted values, etc. 

The next step is to recommend the items (movies) that this user might like based on the earlier preferences. So, **the similarity score** between the user profile and item profile is calculated and ranked accordingly. The more the similarity score, the higher the chances of liking the movie by the user.

The **Eucledian Distance** is one option to calculate the similarity. It calculates the distance between two vectores since the user profile and item profile both are high-dimensional vectors. The higher the distance value, the less similar are the two vectors. Therefore, the distance between the user profile and all other items
are calculated and ranked in decreasing order.

The **Cosine Similarity** is another possibility to calculat the similarity. Instead of distance, it measures the angle
between two vectors (user profile vector and item profile vector). The smaller the angle between both vectors, the more similar they are to each other.

## Collaborative Filtering Based RS

There are two kinds of CF:

1. Nearest Neighbors based CF

2. Latent Factor based CF

CF based RS doesn’t require the item attributes or description for
recommendations; instead **it works on user item interactions**. These
interactions can be measured in various ways such as ratings, item bought, time spent, shared on another platform, etc.

In real life, we suppose ask to our friends (colleagues, relatives, or community members), who are similar to us, for recommendations about restaurants, movies, book, place to travel. Our interests match in some areas and so we trust their recommendations. In real life, it’s easy to know who are the people falling in this circle, but when it comes to online recommendations, **the key task** in collaborative filtering is **to find the users who are most similar to you**. Each user can be represented by a vector that contains the feedback value of a user item interaction.

The **user item matrix** is exactly what the name suggests. In the rows, we
have all the unique users; and along the columns, we have all the unique
items. The values are filled with feedback or interaction scores to highlight
the liking or disliking of the user for that product. The user item matrix is generally **very sparse** as
there are millions of items, and each user doesn’t interact with every
item; so the matrix contains a lot of null values. There are two types of feedback that can be
considered in the UI matrix:

- Explicity Feedback: When the user gives ratings to the item
after the interaction and has been experiencing the item features. The Explicit feedback data **contains very limited amounts of data
points** as a very small percentage of users take out the time to give ratings even after buying or using the item. 

- Implicit Feedback: It is mostly inferred from the activities of the user on the online platform and is based on interactions with items. The challenges with implicit feedback are that it contains a lot of noisy data and
therefore doesn’t add too much value in the recommendations.

### Nearest Neighbors based CF

This CF works by **finding out** the k-nearest neighbors of users by finding
**the most similar users who also like or dislike the same items as the
active user** (for user we are trying to recommend). There are two steps
involved in the nearest neighbor’s collaborative filtering:

- The first step is to find k-nearest neighbors

- The second step is to predict the rating or likelihood of the active user liking a particular item.

The k-nearest neighbors can be found out using:

- Euclidean distance

- cosine similarity 

- Jaccard similarity: There is a major issue with this approach, though,
because the Jaccard similarity doesn’t consider the feedback value while
calculating the similarity score and only considers the common items rated. For users that rated the intem  high and the other might have
rated the intem low, the Jaccard considers they are simillary. Because of that we are considere only Euclidian and cosine methods.

But we need to handel with a problem. The **sparsity of the user/item matrix**. The user item matrix would contain lot of missing values for the simple reason that there are lot of items and not every user interacts with each item. There are a couple of ways to deal with missing values in the UI matrix:

1. Replace the missing value with 0s.

2. Replace the missing values with average ratings of
the user.

There are, again, **two categories of Nearest Neighbors** based CF:

1. User based CF: we find k-nearest users or  the
most similar user to the active user and recommend the items that the
similar user has bought/rated highly to the active user, which he hasn’t
seen/bought/tried yet. The assumption that this kind of RS makes is that
if two or more users have the same opinion about a bunch of items, then
they are likely to have the same opinion about other items as well.

2. Item based CF: we find k-nearest items to be recommended to users

### Latent Factor based CF
