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
