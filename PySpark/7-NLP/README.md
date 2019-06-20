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


# 2. Bag of Words

All machine Learning algorithm only understand numbers. So, the Bag of Words (BOW) is the methodology through which we can represent the text data into numerical form for it to be used by Machine Learning or any other analysis.

BOW (Bag of Words) allows us to convert the text form into a numerical vector
form by considering the occurrence of the words in text documents. The documents are converted in **vocabulary** which is list of unique words appearing in all the documents. Each document can be represented by a vector of ones and zero. The 1 means that word is in that text the zero meand the word is not in the text. 

However, **the BOW does not consider the order of words in the document and the semantic meaning of the word** and hence is the most baseline method to represent the text data into numerical form. words. The **count vectorizer** instead takes count of the word appearing in the particular document. As we can observe, each sentence is represented as a dense vector. It
shows that the vector length is 11 and the first sentence contains 3 values
at the 0th, 2nd, and 8th indexes.

![count_vect](https://user-images.githubusercontent.com/37953610/59883323-8d8d0c80-93ac-11e9-9cb7-e932bf7b5c10.JPG)






