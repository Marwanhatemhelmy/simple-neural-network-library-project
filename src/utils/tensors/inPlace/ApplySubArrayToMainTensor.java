package utils.tensors.inPlace;

import tensor.Tensor;
import utils.multiDimensionalArrays.ApplyDataToRootArray;

public class ApplySubArrayToMainTensor {
    public static void applySubArrayToMainTensor(int[] indeces, double[] subArray, Tensor mainTensor, boolean grad){
        if (grad){ApplyDataToRootArray.applyDataToRootArray(indeces, subArray, mainTensor.grad);}
        if (!grad){ApplyDataToRootArray.applyDataToRootArray(indeces, subArray, mainTensor.data);}
    }
}