# simple-neural-network-library
**A simple AI library that helps you in creating AI models &amp; simple neural networks.**

## this library was tested on both piema indians diabetes dataset and MNIST digits dataset, and the results were as follows :

### for piema indians diabetes dataset :

**layers structure** = `[8 -> 120 -> 1]`

**training settings** = `[100 epochs, relu for hidden layers, sigmoid for output layer, BCE loss function, xavier initializer, adam optimizer]`

**avarage accuracy on training dataset** = `95%`

**duration on avarage** = `3.7 seconds`


### for MNIST dataset :

**layers structure** = `[784 -> 120 -> 10]`

**training settings** = `[5 epochs, relu for hidden layers, cross-entropy loss function, xavier initializer, adam optimizer]`

**avarage accuracy on training dataset** `(only 1000 samples)` = `94%`

**duration on avarage** = `17.5 seconds`

### constriants :

**_only supports double datatypes `(float64)` for tensors_**
