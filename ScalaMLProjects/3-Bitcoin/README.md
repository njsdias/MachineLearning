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

## 2.Download Dataset

The bitstampUSD_1-min_data_2012-01-01_to_2017-10-20.csv. can be downloaded from: 

- https:// www. kaggle. com/ mczielinski/ bitcoin- historical- data/

The dataset has eight columns:

- Timestamp: The time elapsed in seconds since January 1, 1970. It is 1,325,317,920
for the first row and 1,325,317,920 for the second 1. (Sanity check! The difference
is 60 seconds).

- Open: The price at the opening of the time interval. It is 4.39 dollars. Therefore it
is the price of the first trade that happened after Timestamp (1,325,317,920 in the
first row's case).

- Close: The price at the closing of the time interval.

- High: The highest price from all orders executed during the interval.

- Low: The same as High but it is the lowest price.

- Volume_(BTC): The sum of all Bitcoins that were transferred during the time
interval. So, take all transactions that happened during the selected interval and
sum up the BTC values of each of them.

- Volume_(Currency): The sum of all dollars transferred.

- Weighted_Price: This is derived from the volumes of BTC and USD. By dividing
all dollars traded by all bitcoins, we can get the weighted average price of BTC
during this minute. So Weighted_Price = Volume_(Currency)/Volume_(BTC).

## 3-Assumptions

- 1: We can ignore the actual price and rather look at its change. As a measure of this, we can take the delta
between opening and closing prices. If it is positive, it means the price grew during that minute; the price went down if it is negative and stayed the same if delta = 0.

- 2: Price Prediction: We predict the price at T+60 seconds, for instance, based on the
price at T, T-60s, T-120s and so on.

- 3: The first 600,000 of rows are eliminated from the dataset because they are not informative since the price changes are rare and trading volumes are small.

- 4: We need to Label our data so that we can use a supervised ML algorithm. This is the easiest measure, without concerns about transaction fees.
