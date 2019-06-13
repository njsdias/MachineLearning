### 1. Topic Modeling - A Better Insight into Large-Scale Texts

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

For example: Potato have more probability occurence in texts related with cooking than the word motor.

Different topics may share some words, and a document can have more than one topic associated with it.

For example: potato and apple can appear in cooking topic and the vegan-food.

So in short, we have a collection of text datasets—that is, a set of text files. Now the challenging part is
finding useful patterns about the data using LDA.
