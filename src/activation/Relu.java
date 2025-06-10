package activation;

import tensor.Tensor;

public class Relu extends Activation {

    public Relu(Tensor tensor){
        super(tensor);
    }

    @Override
    public double activation(double weightedInputValue){
        return Math.max(0, weightedInputValue);
    }

    @Override
    public double derivative(double activation){
        return (activation>0) ? 1:0;
    }
    
}