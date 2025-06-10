package utils.multiDimensionalArrays;

import java.util.ArrayList;
import java.util.List;

public class RegeneratingNdArray {
    public static List<Object> convertToArrayList(double[] array, int[] shape) {
        return convert(array, shape, 0);
    }

    private static List<Object> convert(double[] array, int[] shape, int dim) {
        int length = shape[dim];
        List<Object> result = new ArrayList<>(length);

        if (dim == shape.length - 1) {
            for (int i = 0; i < length; i++) {
                result.add(array[i]);
            }
        } else {
            int sliceLength = 1;
            for (int i = dim + 1; i < shape.length; i++) {
                sliceLength *= shape[i];
            }
            for (int i = 0; i < length; i++) {
                int startIdx = i * sliceLength;
                double[] subArray = new double[sliceLength];
                System.arraycopy(array, startIdx, subArray, 0, sliceLength);
                result.add(convert(subArray, shape, dim + 1));
            }
        }

        return result;
    }
}