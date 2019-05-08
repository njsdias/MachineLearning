### 1. Purpose
This repository covers the essential of Tensorflow for Machine Learning with the objective to increase the knowledge of the best approaches in Machine Learning. For that the structure of repository follows the subjects of the book _Building Machine Learning Projects with Tensor Flow_ .

The explanations and the comments as well the code are transcripted from the book. 
So, all rights are attributed to the author of the book.

The main objective is learning with examples well explained. 

Note: Some code can be different from the original only to stay in accordance with the new features released by the new version of Tensor Flow.

This repo uses TensorFlow 1.13.1 version and the book uses the TensorFlow 0.10 version.

### 1. Building Machine Learning Projects with Tensor Flow

This book is for **data analysts, data scientists, and researchers** who want to make the results of
their machine learning activities faster and more efficient. Those who want a crisp guide to
complex numerical computations with TensorFlow will find the book extremely helpful. This
book is also for **developers who want to implement TensorFlow in production** in various
scenarios. Some experience with C++ and Python is expected.

![book](https://user-images.githubusercontent.com/37953610/57300495-d6c02000-70ce-11e9-9d23-8f5d57468c5b.JPG)



### 2. TensorFlow

TensorFlow is an **open source software library for numerical computation** using data flow
graphs. Nodes in the graph represent mathematical operations, while the graph edges
represent the multidimensional data arrays (tensors) passed between them.

The library includes various functions that enable you to implement and explore the cutting
edge **Convolutional Neural Network (CNN) and Recurrent Neural Network (RNN)**
architectures for image and text processing. As the complex computations are arranged in the
form of graphs, TensorFlow **can be used as a framework** that enables you to develop your
own models with ease and use them in the field of machine learning.

It is also **capable of running in** the most heterogeneous environments, from **CPUs to mobile
processors, including highly-parallel GPU computing**, and with the new serving architecture
being able to run on very complex mixes of all the named options.

The name Tensor is from **linear algebra** which is a generalization of of vectors and
matrices. So, in this sense, a vector is a 1D tensor, a matrix is a 2D tensor.
So, a tensor is just a typed, multidimensional array, with additional operations, modeled in the tensor object. 

![tensor_rank](https://user-images.githubusercontent.com/37953610/57301754-a2019800-70d1-11e9-83ae-113b0829c421.JPG)

The TensorFlow documentation uses three notational conventions to describe tensor
dimensionality: **rank, shape, and dimension number**. The following table shows how these
relate to one another:

![relations_rank](https://user-images.githubusercontent.com/37953610/57301808-bba2df80-70d1-11e9-8ba8-beca232f2333.JPG)

In addition to dimensionality, tensors have a fixed **data type**. The data type can be float, integer, string and boolean.

### 3. TensorFlow Board
TensorFlow's data flow graph is a symbolic representation of how the computation of the models will work that allows the graphical representation of the data flow graph and a dashboard used for the interpretation of results, normally coming from the
logging utilities.

![tensorboard2](https://user-images.githubusercontent.com/37953610/57317126-552cba00-70ef-11e9-9da4-b6894fe641d3.JPG)

A data flow graph is, succinctly, a complete TensorFlow computation, represented as a graph
where nodes are operations and edges are data flowing between operations.

Normally, nodes implement mathematical operations, but also represent a connection to feed
in data or a variable, or push out results.

Edges describe the input/output relationships between nodes. These data edges exclusively
transport tensors. Nodes are assigned to computational devices and execute asynchronously
and in parallel once all the tensors on their incoming edges become available.

All operations have a name and represent an abstract computation (for example, matrix
inverse or product).

![tensorboard](https://user-images.githubusercontent.com/37953610/57302963-3a991780-70d4-11e9-832d-b47066be54cd.JPG)

Every computation graph we build, TensorFlow has a real-time logging mechanism for, in
order to save almost all the information that a model possesses.
However, the model builder has to take into account which of the possible hundred
information dimensions it should save, to later serve as an analysis tool.
To save all the required information, TensorFlow API uses data output objects, called
Summaries.
These Summaries write results into TensorFlow event files, which gather all the required data
generated during a Session's run.
First it is necessary create a folder (i.e. logs) inside of the main folder of the project.
After that run in the terminal of the python environment the command
  
    tensorboard --logdir=logs/

and copy the http address and past it in new tab of your web browser.
Inside of your code write the next code lines:

    NAME = "nameoflogs{}".format(int(time.time()))
    tensorboard = TensorBoard(log_dir='logs/{}'.format(NAME))

where nameoflogs is a folder that tensorflow will be created to save the logs of your model and plot them using the TensorBoard.

### 3.1- Adding Summary nodes
All Summaries in a TensorFlow Session are written by a SummaryWriter object. The main
method to call is:
          
    tf.train.SummaryWriter.__init__(logdir, graph_def=None)
    
This command will create a SummaryWriter and an event file, in the path of the parameter.
The constructor of the the SummaryWriter will create a new event file in logdir. This event
file will contain Event type protocol buffers constructed when you call one of the following
functions: 

- add_summary(), 
- add_session_log(), 
- add_event()
- add_graph().

If you pass a graph_def protocol buffer to the constructor, it is added to the event file. (This is
equivalent to calling add_graph() later). When you run TensorBoard, it will read the graph definition from the file and display it
graphically so you can interact with it.

First, create the TensorFlow graph that you'd like to collect summary data from and decide
which nodes you would like to annotate with summary operations.

Operations in TensorFlow don't do anything until you run them, or an operation that depends
on their output. So, to generate summaries, we
need to run all of these summary nodes. Managing them manually would be tedious, so use
          
    tf.merge_all_summaries 
to combine them into a single op that generates all the summary data.

Then, you can just run the merged summary op, which will generate a serialized Summary
protobuf object with all of your summary data at a given step. Finally, to write this summary
data to disk, pass the Summary protobuf to a
      
    tf.train.SummaryWriter
    
The SummaryWriter takes a logdir in its constructor, this logdir is quite important, it's the
directory where all of the events will be written out. Also, the SummaryWriter can optionally
take a GraphDef in its constructor. If it receives one, then TensorBoard will visualize your
graph as well.

Now that you've modified your graph and have a SummaryWriter, you're ready to start
running your network! If you want, you could run the merged summary op every single step,
and record a ton of training data. That's likely to be more data than you need, though. Instead,
consider running the merged summary op every _n_ steps.

This is a list of the different Summary types, and the parameters employed on its construction:

- tf.scalar_summary (tag, values, collections=None, name=None)
- tf.image_summary (tag, tensor, max_images=3, collections=None, name=None)
- tf.histogram_summary (tag, values, collections=None, name=None)

These are special functions, that are used to merge the values of different operations, be it a
collection of summaries, or all summaries in a graph:

- tf.merge_summary (inputs, collections=None, name=None)
- tf.merge_all_summaries (key='summaries')
