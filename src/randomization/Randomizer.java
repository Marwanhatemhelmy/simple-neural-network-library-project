package randomization;

import tensor.Tensor;

public interface Randomizer {

    public void normal_(Tensor tensor);
    public void uniform_(Tensor tensor);
    public Tensor normal(Tensor tensor);
    public Tensor uniform(Tensor tensor);
    public double normal(int in, int out);
    public double uniform(int in, int out);
    
}