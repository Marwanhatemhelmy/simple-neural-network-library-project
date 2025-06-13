package utils.tensors.stacking;

import java.util.Arrays;

import tensor.Tensor;

public class TensorStacking {

    public static Tensor stack(Tensor[] tensors){
        if (tensors.length == 0){
            throw new IllegalArgumentException("expected tesnors array to not be empty");
        }

        int[] shape = getStackedShape(tensors[0].shape, tensors.length);
        
        Tensor stackedTensor = new Tensor(getStackedDataFlattened(tensors));
        stackedTensor.shape = shape;
        stackedTensor.dimensions = stackedTensor.shape.length;
        
        return stackedTensor;
    }

    public static int[] getStackedShape(int[] tensorShape, int numberOfTensors){

        int[] stackedShape = new int[tensorShape.length+1];

        stackedShape[0] = numberOfTensors;

        for (int s=1;s<stackedShape.length;s++){
            stackedShape[s] = tensorShape[s-1];
        }

        return stackedShape;
    }

    public static double[] getStackedDataFlattened(Tensor[] tensors){

        double[] data = new double[tensors.length * tensors[0].data.length];
        int dataIndex = 0;

        for (int i=0;i<tensors.length;i++){
            if (i!=0){
                if (!Arrays.equals(tensors[i].shape, tensors[i-1].shape)){
                    throw new IllegalArgumentException("expected the tensors to have the same shape");
                }
            }
            for (int j=0;j<tensors[i].data.length;j++){
                data[dataIndex] = tensors[i].data[j];
                dataIndex++;
            }
        }

        return data;
    }
}