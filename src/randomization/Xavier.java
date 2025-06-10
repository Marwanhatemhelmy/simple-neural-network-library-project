package randomization;

import java.util.Random;

import tensor.Tensor;

public class Xavier implements Randomizer {
    Random random;

    public Xavier(){
        this.random = new Random();
    }

    @Override
    public void uniform_(Tensor tensor){
        int fan_in = tensor.shape[tensor.shape.length-1];
        int fan_out = tensor.shape[0];
        for (int i=0;i<tensor.data.length;i++){
            tensor.data[i] = this.uniform(fan_in, fan_out);
        }
        tensor.refreshRootTensor();
    }

    @Override
    public void normal_(Tensor tensor){
        int fan_in = tensor.shape[tensor.shape.length-1];
        int fan_out = tensor.shape[0];
        for (int i=0;i<tensor.data.length;i++){
            tensor.data[i] = this.normal(fan_in, fan_out);
        }
        tensor.refreshRootTensor();
    }

    @Override
    public Tensor uniform(Tensor tensor){
        tensor = tensor.copy();
        int fan_in = tensor.shape[tensor.shape.length-1];
        int fan_out = tensor.shape[0];
        for (int i=0;i<tensor.data.length;i++){
            tensor.data[i] = this.uniform(fan_in, fan_out);
        }
        return tensor;
    }

    @Override
    public Tensor normal(Tensor tensor){
        tensor = tensor.copy();
        int fan_in = tensor.shape[tensor.shape.length-1];
        int fan_out = tensor.shape[0];
        for (int i=0;i<tensor.data.length;i++){
            tensor.data[i] = this.normal(fan_in, fan_out);
        }
        return tensor;
    }

    @Override
    public double uniform(int fan_in, int fan_out) {
        double limit = Math.sqrt(6.0 / (fan_in + fan_out));
        return this.random.nextDouble() * (2 * limit) - limit;
    }

    @Override
    public double normal(int fan_in, int fan_out) {
        double stdDev = Math.sqrt(2.0 / (fan_in + fan_out));
        return random.nextGaussian() * stdDev;
    }
    
}