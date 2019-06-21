# 1. Natural language processing (NLP)

The area that focuses on making machines learn and understand the textual data
in order to perform some useful tasks is known as Natural Language
Processing (NLP). 

The text data could be structured or unstructured, and
we have to apply multiple steps in order to make it analysis ready. 

There are many applications of NLP that are heavily used by businesses these days such as
chatbot, speech recognition, language translation, recommender systems,
spam detection, and sentiment analysis.

There are five major steps to make the text data ready for analysis:

1. Reading the corpus

2. Tokenization

3. Cleaning /Stopword removal

4. Stemming

5. Converting into Numerical Form

**Corpus**

A corpus is known as the entire collection of text documents. For example,
suppose we have thousands of emails in a collection that we need to
process and analyze for our use. This group of emails is known as a corpus
as it contains all the text documents. The next step in text processing is
tokenization.

**Tokenize**

The method of dividing the given sentence or collection of words of a text
document into separate /individual words is known as tokenization. It
removes the unnecessary characters such as punctuation.

For example, we can build a dataframe with four sentences (corpus) to tokenize.

![tokenize_1](https://user-images.githubusercontent.com/37953610/59874993-8e677380-9397-11e9-8014-984836516ab0.JPG)


The next
step is to import Tokenizer from the Spark library. We have to then pass the
input column and name the output column after tokenization. We use the
transform function in order to apply tokenization to the review column

![tokenize_2](https://user-images.githubusercontent.com/37953610/59875049-afc85f80-9397-11e9-9cb0-d136cc9812da.JPG)


**Stop Words**

As you can observe, the tokens column contains very common words such as
‘this’, ‘the’, ‘to’ , ‘was’, ‘that’, etc. These words are known as stopwords and they
seem to add very little value to the analysis. Hence, it's always considered a good idea to drop these stopwords from the
tokens. In PySpark, we use StopWordsRemover to remove the stopwords.

![stopwords_1](https://user-images.githubusercontent.com/37953610/59875142-e2725800-9397-11e9-81d9-6757bef4e711.JPG)



All machine Learning algorithm only understand numbers. So, the Bag of Words (**BOW**) is the methodology through which we can represent the text data into numerical form for it to be used by Machine Learning or any other analysis.

BOW (Bag of Words), allows us to convert the text form into a numerical vector form by considering the occurrence of the words in text documents. The documents are converted in **vocabulary** which is list of unique words appearing in all the documents. Each document can be represented by a vector of ones and zero. The 1 means that word is in that text the zero meand the word is not in the text. 

However, **the BOW does not consider the order of words in the document and the semantic meaning of the word** and hence is the most baseline method to represent the text data into numerical form. words. The **count vectorizer** instead takes count of the word appearing in the particular document. As we can observe, each sentence is represented as a dense vector. It shows that the vector length is 11 and the first sentence contains 3 values at the 0th, 2nd, and 8th indexes.

![count_vect](https://user-images.githubusercontent.com/37953610/59883323-8d8d0c80-93ac-11e9-9cb7-e932bf7b5c10.JPG)

The Term Frequency – Inverse Document Frequency (**TF-IDF**) overcome the issue of Count Vectorizer in sense that it 
consider the co-occurrences of words in other documents - the words that appearing more often would have a larger impact on the feature vector. The whole idea is to give more weight to the word if appearing a high number of times in the same document but penalize if it is appearing a higher number of times in other documents as well. **This indicates that a word is common across the corpus and is not as important as its frequency in the current document indicates.**

![tf-idf](https://user-images.githubusercontent.com/37953610/59883941-98e13780-93ae-11e9-81cc-8b782a7388d2.JPG)


# 2. Sequence Embeddings


How can we know more about the journeys of several website's visitors and compare these visitors to each other?
Sequence Embedding is a powerful way that offers us the flexibility to not only **compare any two
distinct viewers' entire journeys in terms of similarity but also to predict
the probability of their conversion.** 

Sequence embeddings essentially help us to move away from using traditional features to make predictions and
considers not only the order of the activities of a user **but also the average time spent on each of the unique pages to translate into more robust features**; and it also used in Supervised Machine Learning across multiple use cases (next possible action prediction, converted vs. non-converted, product classification).

Using traditional machine learning models on the advanced features like sequence embeddings, we can achieve tremendous
results in terms of prediction accuracy, but the real benefit lies in visualizing all these user journeys and observing how distinct these paths are from the ideal ones.

Embeddings are unique in terms of **capturing the context of the words** and representing them in such a way that words with similar meanings are represented with similar sort of embeddings. There are two ways to calculate the embeddings.

1. Skip Gram

2. Continuous Bag of Words (CBOW)

Both methods give the embedding values that are nothing but **weights of the hidden layer in a neural network**. These embedding vectors can be of size 100 or more depending on the requirement. The **word2vec** gives the embedding values for each word where as **doc2vec** gives the embeddings for the entire sentence. Sequence Embeddings are similar to doc2vec and are the result of weighted means of the individual embedding of the word appearing in the sentence.




























