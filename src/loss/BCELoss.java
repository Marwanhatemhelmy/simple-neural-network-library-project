package loss;

import java.util.Arrays;

import tensor.Tensor;

public class BCELoss implements Loss {

    @Override
    public Tensor errorTensor(Tensor y_hat, Tensor y_true) {

        if (!Arrays.equals(y_hat.shape, y_true.shape)){
            throw new IllegalArgumentException("expected y_hat tensor to have the same shape as y_true tensor");
        }
        
        Tensor result = y_hat.copy();
        double eps = 1e-8;

        for (int i=0;i<y_true.data.length;i++){
            double a = y_hat.data[i];
            double y = y_true.data[i];

            a = Math.min(Math.max(a, eps), 1 - eps);

            result.data[i] = - (y / a - (1 - y) / (1 - a));
        }

        return result;
    }
    
}