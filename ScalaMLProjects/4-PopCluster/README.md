# 1. Population-Scale Clustering and Ethnicity Prediction

## 1.1 Objective

We will see how to apply the K-means algorithm on large-scale genomic
data from the 1000 Genomes Project aiming at clustering genotypic variants at the
population scale. Then we'll train an H2O-based deep learning model for predicting
geographic ethnicity. Finally, Spark-based Random Forest will be used to enhance the
predictive accuracy.
     
## 1.2 Genome sequences studies

Understanding variations in genome sequences assists us in identifying people who are
predisposed to common diseases, curing rare diseases, and finding the corresponding
population group of individuals from a larger population group.

One of the most important tasks is the analysis of genomic profiles to attribute individuals
to specific ethnic populations, or the analysis of nucleotide haplotypes for disease
susceptibility. The data from the 1000 Genomes project serves as the prime source to
analyze genome-wide single nucleotide polymorphisms (SNPs) at scale for the prediction
of the individual's ancestry with regards to continental and regional origins.

By using the genetic variants dataset from the 1000 Genomes project, we will try to answer the following questions:

- How is human genetic variation distributed geographically among different
population groups?

- Can we use the genomic profile of individuals to attribute them to specific
populations or derive disease susceptibility from their nucleotide haplotype?

- Is the individual's genomic data suitable to predict geographic origin (that is, the
population group for an individual)?

## 1.3 Tecnologies used in this project

Spark forms the most efficient data-processing framework and, in addition, provides
primitives for in-memory cluster computing, for example, for querying the user data
repeatedly. This makes Spark an excellent candidate for machine learning algorithms that
outperform the Hadoop-based MapReduce framework.

In this project, we addressed the preceding questions in a scalable and more efficient way.
Particularly, we examined how we applied Spark and ADAM for large-scale data
processing, H2O for K-means clustering of the whole population to determine inter- and
intra-population groups, and MLP-based supervised learning by tuning more
hyperparameters to more accurately predict the population group for an individual
according to the individual's genomic data.

We will configure H2O so that the same setting can be used in upcoming
chapters too. Concisely, we will learn the following topics throughout this end-to-end
project:

- Population-scale clustering and geographic ethnicity prediction

- The 1000 Genomes project, a deep catalog of human genetic variants

- Algorithms and tools

- Using K-means for population-scale clustering

- Using H2O and Random Forest for ethnicity prediction


## 1.4 Dataset

The data from the 1000 Genomes project is a very large catalog of human genetic variants.
The project aims to determine genetic variants with frequencies higher than 1% in the
populations studied. The data has been made openly available and freely accessible through
public data repositories to scientists worldwide. Also, the data from the 1000 Genomes
project is widely used to screen variants discovered in exome data from individuals with
genetic disorders and in cancer genome projects.

The genotype dataset in Variant Call Format (VCF) provides the data of human individuals
(that is, samples) and their genetic variants, and in addition, the global allele frequencies as
well as the ones for the super populations. The data denotes the population's region for
each sample which is used for the predicted category in our approach. Specific
chromosomal data (in VCF format) may have additional information denoting the superpopulation
of the sample or the sequencing platform used. For multiallelic variants, each
alternative allele frequency (AF) is presented in a comma-separated list.

The 1000 Genomes Project started in 2008 and finished in September 2014 covering 2504 individuals from 26
populations (that is, ethnic backgrounds) in total. In total, over 88 million variants (84.7
million single nucleotide polymorphisms (SNPs), 3.6 million short insertions/deletions
(indels), and 60,000 structural variants) have been identified as high-quality haplotypes. 
As a result, the third phase release leaves 84.4 million variants.

## 1.5 Steps using the technologies
Large-scale data from release 3 of the 1000 Genomes project contributes to 820 GB of data.
Therefore, ADAM and Spark are used to pre-process and prepare the data (that is, training,
testing, and validation sets) for the MLP and K-means models in a scalable way. Sparkling
water transforms the data between H2O and Spark.

Then, K-means clustering, the MLP (using H2O) are trained. For the clustering and
classification analysis, the genotypic information from each sample is required using the
sample ID, variation ID, and the count of the alternate alleles where the majority of variants
that we used were SNPs and indels.

Using H2O, it's possible to develop machine learning and DL applications with
a wide range of languages, such as Java, Scala, Python, and R. It also has the ability to interface with Spark, HDFS, SQL, and NoSQL databases. In short, H2O works with R, Python, and Scala on Hadoop/Yarn, Spark, or laptop. On the other hand, Sparkling water combines the fast, scalable ML algorithms of H2O with the capabilities of Spark. It drives the computation from Scala/R/Python and utilizes the H2O flow UI. In short, Sparkling water = H2O + Spark.

