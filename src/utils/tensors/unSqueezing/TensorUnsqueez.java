package utils.tensors.unSqueezing;

public class TensorUnsqueez {
    public static int[] unsqueeze(int[] shape, int dim) {
        if (dim < 0 || dim > shape.length) {
            throw new IllegalArgumentException("dimension out of bounds");
        }

        int[] newShape = new int[shape.length + 1];

        for (int i = 0; i < dim; i++) {
            newShape[i] = shape[i];
        }

        newShape[dim] = 1;

        for (int i = dim; i < shape.length; i++) {
            newShape[i + 1] = shape[i];
        }

        return newShape;
    }
}