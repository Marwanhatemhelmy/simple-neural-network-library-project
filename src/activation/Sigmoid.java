package activation;

import tensor.Tensor;

public class Sigmoid extends Activation{

    public Sigmoid(Tensor tensor){
        super(tensor);
    }

    @Override
    public double activation(double z){
        return 1.0/(1.0+Math.exp(-z));
    }

    @Override
    public double derivative(double a){
        return a*(1.0-a);
    }
    
}