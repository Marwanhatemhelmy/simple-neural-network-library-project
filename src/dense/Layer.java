package dense;

import tensor.Tensor;

public abstract class Layer {

    public Tensor weights;
    public Tensor biases;

    public Layer(Tensor weights, Tensor biases){
        this.weights = weights;
        this.biases = biases;
    }

    public abstract Tensor forward(Tensor input);
    public abstract Tensor backward(Tensor error);
    
}