### 1. High Frequency Bitcoin Price Prediction from Historical and Live Data
     
Here we will see how to develop a real-life project that collects historical and
live Bitcoin data and predicts the price for an upcoming week, month, and so on hat help us taking automated decision for online
cryptocurrency. In addition to this, we will see how to generate a simple signal for online cryptocurrency trading.

Briefly, we will learn the following topics throughout this end-to-end project:

- Bitcoin, cryptocurrency, and online trading

- Historical and live-price data collection

- High-level pipeline of the prototype

- Gradient-boosted trees regression for Bitcoin price prediction

- Demo prediction and signal generation using the Scala play framework

- Future outlook—using the same technique for other datasets

It's hard predict the value of Bitcoin in the long term, as the value behind Bitcoin
is less tangible. The price mostly reflects market perception and is highly dependent on
news, regulations, collaboration of governments and banks, technical issues of the platform
(such as transactions fee and block size), interest of institutional investors in including
Bitcoin into their portfolio, and more. 

Nonetheless, from a short-term perspective, Bitcoin price is a by-product of market activity
usually happening on a platform, called exchange (Bitstamp, Coinbase, Kraken, and
Bitfinex among the most well-known exchanges). Users, after registration and after going
through KYC (Know Your Customer) procedures, can trade Bitcoin in it for fiat currencies
such as dollars and euros, as well as for other cryptocurrencies, called alt-coins or
alternative coins (Ethereum, Litecoin, and Dash are well known).

The goal of this chapter is to develop a prototype of a system that will predict the shortterm
change of Bitcoin price, using historical data to train the algorithm, and real-time data
to predict and select algorithms that perform better.

![bitcoin-TS](https://user-images.githubusercontent.com/37953610/59272447-f4dceb00-8c4d-11e9-9621-a8df77c6a4f3.JPG)

So far, the best possible approach that was identified from research papers is as follows.

- Training:
Use order book data, instead of derived OHLC + volume data. Therefore, for training and
prediction, use data that looks like this:
   - Split the data into a time series of a certain size (size is a parameter to tune).

   - Cluster the time series data into K clusters (K is a parameter to tune). It's assumed
that clusters with some natural trends would appear (sharp drop/rise in price and
so on).

   - For each cluster, train the regression and classifier to predict the price and price
change, respectively.

-Prediction: This approach considers the most recent time series with the size of a specific window and
trains the model. Then it classifies the data as follows:

   - Takes the most recent time series with window size used for training

   - Classifies it: which of the clusters does it belong to?
   
   - Uses the ML model for that cluster to predict the price or price change.
   
By having many parameters to identify, and not having the order-book historical data available easily,
in this project, we use a simpler approach and dataset.

The second goal is to build a tool for experiments that allows us to try different approaches
to predicting prices and evaluate it on real-life data easily. The code has to be flexible,
robust, and easily extensible.

Therefore, in summary, there are three main components of the system:

- Scala script for preprocessing of historical data into the required format

- Scala app to train the ML model

- Scala web service to predict future prices

![flowchart-prototype](https://user-images.githubusercontent.com/37953610/59273322-dd066680-8c4f-11e9-93c3-a6c8bff2dd39.JPG)
