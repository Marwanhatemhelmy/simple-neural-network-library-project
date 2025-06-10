package utils.multiDimensionalArrays;

public class UnravelIndex {
    public static int[] unravelIndex(int flatIndex, int[] shape) {

        int len = ArrayProduct.getProduct(shape);

        if (flatIndex >= len){
            throw new IndexOutOfBoundsException("index "+flatIndex+" out of bounds for length "+len);
        }

        int[] indices = new int[shape.length];

        for (int i = shape.length - 1; i >= 0; i--) {
            indices[i] = flatIndex % shape[i];
            flatIndex /= shape[i];
        }

        return indices;
    }
}