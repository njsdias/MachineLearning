# 1. Topic Modeling - A Better Insight into Large-Scale Texts

Topic modeling (TM) is a technique widely used in mining text from a large collection of
documents. These topics can then be used to summarize and organize documents that
include the topic terms and their relative weights. The dataset that will be used for this
project is just in plain unstructured text format.

We will see how effectively we can use the Latent Dirichlet Allocation (LDA) algorithm for
finding useful patterns in the data. We will compare other TM algorithms and the
scalability power of LDA. In addition, we will utilize Natural Language Processing (NLP)
libraries, such as Stanford NLP.

In a nutshell, we will learn the following topics throughout this end-to-end project:

- Topic modelling and text clustering

- How does LDA algorithm work?

- Topic modeling with LDA, Spark MLlib, and Standard NLP

- Other topic models and the scalability testing of LDA

- Model deployment

In TM, a topic is defined by a cluster of words, with each word in the cluster having a
probability of occurrence for the given topic, and different topics having their respective
clusters of words along with corresponding probabilities.
For example: The word "potato" have more probability occurence in texts related with cooking topic than the word "motor".

Different topics may share some words, and a document can have more than one topic associated with it.
For example: The words "potato" and "apple" can appear in cooking topic and the vegan-food topic.

So in short, we have a collection of text datasets — that is, a set of text files. Now the challenging part is
finding useful patterns about the data using LDA.

The topics are considered hidden and must be uncovered via analyzing
joint distributions to compute the conditional distribution of hidden variables (topics),
given the observed variables and words in documents. These topics can then be used
to summarize and organize documents that include the topic terms and their relative
weights

In contrast to TM, in **document clustering**, the basic idea is to group documents into
different groups based on a well-known similarity measure. To perform grouping, each
document is represented by a vector representing the weights assigned to words in the
document. It is common to perform weighting using the term frequency-inverse document frequency
(also known also the **TF-IDF** scheme).

LDA can be thought of as a clustering algorithm where topics correspond to cluster centers, and
documents correspond to examples (rows) in a dataset. Topics and documents both exist in
a feature space, where feature vectors are vectors of word counts (bags of words). Instead of
estimating a clustering using a traditional distance, LDA uses a function based on a
statistical model of how text documents are generated.

Let's see an example. Assume there are _b_ balls in a basket having _c_ different colors. Now
also assume each term in a vocabulary has one of _c_ colors. Now also assume that the
vocabulary terms are distributed in _t_ topics. Now the frequency of occurrence of each color
in the basket is proportional to the corresponding term's weight in topic, _w_. 
Then the LDA algorithm incorporates a term weighting scheme by making the _size_ of each ball proportional to the weight of its corresponding term. 

So, the balls with the same color belongs to the same topic. And each ball have own size that is proportional to the weight.
Then, the LDA do a weighted sum of the balls that belongs to the same topic and and that are in the basket. At the end LDA see which topic have the bigger sum value and classify the document as belongs to that topic. 

The RDD-based LDA algorithm developed in Spark is a topic model designed for text
documents. It is based on the original LDA paper (journal version): Blei, Ng, and Jordan,
Latent Dirichlet Allocation, JMLR, 2003.

Since the release of Spark 1.3, MLlib supports the LDA, which is one of the
most successfully used TM techniques in the area of text mining and NLP.
Moreover, LDA is also the first MLlib algorithm to adopt Spark GraphX. The following
terminologies are worth knowing before we formally start our TM application:

- "word" = "term": an element of the vocabulary
- "token": instance of a term appearing in a document
- "topic": multinomial distribution over words representing some concept

The parameteres of LDA algorithm are explained here:

 - https://spark.apache.org/docs/2.3.0/api/java/org/apache/spark/mllib/clustering/LDA.html
 
 
The dataset can be downloaded from: 

- https://github.com/minghui/Twitter-LDA/tree/master/data/Data4Model/test


You need create a stopWord.txt file. It contains the english stop words. For example you can use the containt of these two files:

- https://github.com/stanfordnlp/CoreNLP/blob/master/data/edu/stanford/nlp/patterns/surface/stopwords.txt

- https://gist.github.com/larsyencken/1440509

The model of the project was saved in _textmining.txt_ file.  

After run the _topicModellingwithLDA.scala_ file **the structure of the project** can be seen in the figure. At folder _data/docs_ we stored the text files and at folder _data/stopwords_ we stored the _stopWords.txt_ file. 

![srt_proj](https://user-images.githubusercontent.com/37953610/59445053-d82fe700-8df6-11e9-9a52-01a14bea7437.JPG)

# 2. Deploying the trained LDA model

For this mini deployment, let's use a real-life dataset: PubMed. The dataset contains some abstracts of some biological articles, their
publication year, and the serial number(ID,Year,Short abstract). A sample dataset containing PubMed terms can be downloaded from:

- https://nlp.stanford.edu/software/tmt/tmt-0.4/ searching by _pubmed-oa-subset.csv_

In the previous code we saved the trained LDA: 

    params.ldaModel.save(spark.sparkContext, "model/LDATrainedModel")
    
And we have the follow folder structure:

![model_str](https://user-images.githubusercontent.com/37953610/59449303-b5093580-8dfe-11e9-8e59-b3656301f6d9.JPG)

To run the LDAModelReuse.scala we need to modify the folder where the new model will be saved, otherwise we will obtain an error exception due to the exting folder that was created by yhe previous code. The model results are in _TextMiningReused.txt_.


