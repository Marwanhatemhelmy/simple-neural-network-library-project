package utils.multiDimensionalArrays;

public class RavelIndex {
    public static int getIndex(int[] shape, int[] indices){

        if (shape.length != indices.length){
            throw new IllegalArgumentException("expected shape.length to equal indices.length but got shape.length : "+shape.length+" & indices.length : "+indices.length);
        }

        int index = 0;
        int stride = 1;
        int dimensions = shape.length;

        for (int i = dimensions - 1; i >= 0; i--) {

            if (indices[i] >= shape[i] || indices[i] < 0) {
                throw new IllegalArgumentException("index "+ indices[i] +" out of bounds for dimension " + i);
            }

            index += indices[i] * stride;
            stride *= shape[i];
        }

        return index;
    }
}