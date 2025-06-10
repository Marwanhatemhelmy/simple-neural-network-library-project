package utils.multiDimensionalArrays;

public class AllocateSubArrayInFlattenArray {
    public static int[] getIndices(int[] shape, int[] indices) {
        int[] strides = new int[shape.length];
        int stride = 1;
        for (int i = shape.length - 1; i >= 0; i--) {
            strides[i] = stride;
            stride *= shape[i];
        }

        int offset = 0;
        for (int i = 0; i < indices.length; i++) {
            offset += indices[i] * strides[i];
        }

        int subSize = 1;
        for (int i = indices.length; i < shape.length; i++) {
            subSize *= shape[i];
        }

        int[] flatIndices = new int[subSize];
        for (int i = 0; i < subSize; i++) {
            flatIndices[i] = offset + i;
        }

        return flatIndices;
    }
    
    public static int[] getShape(int indecesLength, int[] arrayShape){
        if (indecesLength > arrayShape.length){
            throw new IllegalArgumentException("indecesLength must be less than or equal to arrayShape.length");
        }

        if (indecesLength == arrayShape.length){
            return new int[]{1};
        }
        
        int[] shape = new int[arrayShape.length-indecesLength];
        int index = 0;

        for (int i=indecesLength;i<arrayShape.length;i++){
            shape[index] = arrayShape[i];
            index++;
        }

        return shape;
    }
}