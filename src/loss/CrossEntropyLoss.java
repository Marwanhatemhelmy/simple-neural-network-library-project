package loss;

import activation.Activation;
import activation.SoftMax;
import tensor.Tensor;
import utils.dataTypeConvertage.DoubleToIntArray;
import utils.multiDimensionalArrays.ConvertToOneHotEncoded;

public class CrossEntropyLoss implements Loss {

    @Override
    public Tensor errorTensor(Tensor logits, Tensor y_true) {

        if (y_true.dimensions != 1){
            throw new IllegalArgumentException("expected true values tensor to be 1d but got dim = "+y_true.dimensions);
        }

        if (y_true.data.length != logits.shape[0]){
            throw new IllegalArgumentException("expected true values data length to equal logits length (logits.shape[0])");
        }

        double[] trueVals = ConvertToOneHotEncoded.oneHotEncodedFlattened(DoubleToIntArray.convertDoubleToIntArray(y_true.data), logits.shape[1]);

        logits.gradTensor().fill_(1.0);
        
        // setting new tensor logits , to not affect the actual logits tensor outputs
        logits = logits.copy();

        Activation softMax = new SoftMax(logits,1);
        if (logits.dimensions == 0) softMax = new SoftMax(logits, 0);

        softMax.forward_();

        Tensor result = logits.copy();
        
        for (int i=0;i<logits.data.length;i++){
            result.data[i] = (logits.data[i] - trueVals[i]);
        }

        return result;
    }
    
}