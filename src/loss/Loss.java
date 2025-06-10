package loss;

import tensor.Tensor;

public interface Loss {
    
    public Tensor errorTensor(Tensor y_hat, Tensor y_true);

}