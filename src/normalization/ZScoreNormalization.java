package normalization;

import tensor.Tensor;
import utils.multiDimensionalArrays.AllocateSubArrayInFlattenArray;
import utils.multiDimensionalArrays.VectorActualValuesAtIndeces;

public class ZScoreNormalization {

    public void normalize(Tensor data){

        if (data.dimensions != 2 || data.shape.length != 2){
            throw new IllegalArgumentException("data tensor dimension must be 2d");
        }

        int rows = data.shape[0];
        int cols = data.shape[1];
        
        double[] means = new double[cols];
        double[] stdDevs = new double[cols];

        applyMeans(rows, cols, data.shape, data.data, means);
        applyStandardDeviation(rows, cols, data.shape, data.data, means, stdDevs);
        applyNormalization(rows, cols, data.shape, data.data, means, stdDevs);

        data.refreshRootTensor();

    }

    private void applyMeans(int rows, int cols, int[] shape, double[] data, double[] means){

        for (int j = 0; j < cols; j++) {
            double sum = 0.0;
            for (int i = 0; i < rows; i++) {
                double currentElement = VectorActualValuesAtIndeces.vectorActualValuesAtIndeces(AllocateSubArrayInFlattenArray.getIndices(shape, new int[]{i,j}), data)[0];
                sum += currentElement;
            }
            means[j] = sum / rows;
        }

    }

    private void applyStandardDeviation(int rows, int cols, int[] shape, double[] data, double[] means, double[] stds){

        for (int j = 0; j < cols; j++) {
            double sum = 0.0;
            for (int i = 0; i < rows; i++) {
                double currentElement = VectorActualValuesAtIndeces.vectorActualValuesAtIndeces(AllocateSubArrayInFlattenArray.getIndices(shape, new int[]{i,j}), data)[0];
                sum += Math.pow(currentElement - means[j], 2);
            }
            stds[j] = Math.sqrt(sum / rows);
        }

    }

    private void applyNormalization(int rows, int cols, int[] shape, double[] data, double[] means, double[] stds){
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double currentElement = VectorActualValuesAtIndeces.vectorActualValuesAtIndeces(AllocateSubArrayInFlattenArray.getIndices(shape, new int[]{i,j}), data)[0];
                if (stds[j] != 0) {
                    data[AllocateSubArrayInFlattenArray.getIndices(shape, new int[]{i,j})[0]] = (currentElement - means[j]) / stds[j];
                } else {
                    data[AllocateSubArrayInFlattenArray.getIndices(shape, new int[]{i,j})[0]] = 0;
                }
            }
        }

    }

}