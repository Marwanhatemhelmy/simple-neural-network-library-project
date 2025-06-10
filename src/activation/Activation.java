package activation;

import tensor.Tensor;

public abstract class Activation {

    protected Tensor mainTensor;

    public Activation(Tensor tensor){
        this.mainTensor = tensor;
    }

    public Tensor forward(){
        Tensor tensor = this.mainTensor.copy();
        for (int i=0;i<tensor.data.length;i++){
            tensor.data[i] = this.activation(tensor.data[i]);
        }
        return tensor;
    }

    public Tensor calculateGrad(){
        Tensor tensor = this.mainTensor.copy();
        for (int i=0;i<tensor.data.length;i++){
            tensor.grad[i] += this.derivative(tensor.data[i]);
        }
        return tensor;
    }

    public void forward_(){
        Tensor tensor = this.mainTensor;
        for (int i=0;i<tensor.data.length;i++){
            tensor.data[i] = this.activation(tensor.data[i]);
        }
        tensor.refreshRootTensor();
    }

    public void applyGrad_(){
        Tensor tensor = this.mainTensor;
        for (int i=0;i<tensor.data.length;i++){
            tensor.grad[i] += this.derivative(tensor.data[i]);
        }
        tensor.refreshRootTensor();
    }

    public abstract double activation(double weightedInputValue);
    public abstract double derivative(double activation);
    
}