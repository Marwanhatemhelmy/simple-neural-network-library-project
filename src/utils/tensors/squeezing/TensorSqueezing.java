package utils.tensors.squeezing;

public class TensorSqueezing {

    public static int[] squeezeOnDimension(int[] shape, int dim) {
        if (dim < 0 || dim >= shape.length) {
            throw new IllegalArgumentException("Invalid dimension index.");
        }

        if (shape[dim] != 1) {
            return shape.clone();
        }

        int[] result = new int[shape.length - 1];
        int index = 0;

        for (int i = 0; i < shape.length; i++) {
            if (i != dim) {
                result[index++] = shape[i];
            }
        }

        return result;
    }


    public static int[] squeeze(int[] shape) {
        int nonOneCount = 0;
        for (int dim : shape) {
            if (dim != 1) {
                nonOneCount++;
            }
        }
        
        int[] result = new int[nonOneCount];
        
        int index = 0;
        for (int dim : shape) {
            if (dim != 1) {
                result[index++] = dim;
            }
        }
        return result;
    }

}