package loss;

import java.util.Arrays;

import tensor.Tensor;
import utils.multiDimensionalArrays.ArrayProduct;

public class MseLoss implements Loss {

    // uses mean reduction
    @Override
    public Tensor errorTensor(Tensor y_hat, Tensor y_true){
        
        if (!Arrays.equals(y_hat.shape, y_true.shape)){
            throw new IllegalArgumentException("expected y_hat tensor to have the same shape as y_true tensor");
        }

        Tensor errorTensor = y_hat.copy();

        for (int i=0;i<y_hat.data.length;i++){
            // the number of elements in the tensor
            int n = ArrayProduct.getProduct(y_true.shape);
            errorTensor.data[i] = (2.0/(double) n)*(y_hat.data[i] - y_true.data[i]);
        }
        
        return errorTensor;
    }

}