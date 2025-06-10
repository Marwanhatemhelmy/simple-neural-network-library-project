package randomization;

import java.util.Random;

import tensor.Tensor;

public class BasicInitialization implements Randomizer {

    Random random;

    public BasicInitialization(){
        this.random = new Random();
    }

    @Override
    public void uniform_(Tensor tensor){
        for (int i=0;i<tensor.data.length;i++){
            tensor.data[i] = this.uniform();
        }
        tensor.refreshRootTensor();
    }

    @Override
    public void normal_(Tensor tensor){
        throw new UnsupportedOperationException("function not supported via this class");
    }

    @Override
    public Tensor uniform(Tensor tensor){
        tensor = tensor.copy();
        for (int i=0;i<tensor.data.length;i++){
            tensor.data[i] = this.uniform();
        }
        return tensor;
    }

    @Override
    public Tensor normal(Tensor tensor){
        throw new UnsupportedOperationException("function not supported via this class");
    }

    public double uniform(){
        return this.random.nextDouble(-0.5, 0.5);
    }

    @Override
    public double uniform(int in, int out){
        throw new UnsupportedOperationException("function not supported via this class");
    }

    @Override
    public double normal(int in, int out){
        throw new UnsupportedOperationException("function not supported via this class");
    }
    
}