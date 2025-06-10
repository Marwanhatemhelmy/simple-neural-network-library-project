package optimizer;

import tensor.Tensor;

public abstract class Optimizer{
    public double learningRate;
    public Optimizer(double learningRate){
        this.learningRate = learningRate;
    }
    public abstract void updateParameters(Tensor[] parameters);
    public abstract Tensor updateParameter(Tensor param, Tensor grad);
}