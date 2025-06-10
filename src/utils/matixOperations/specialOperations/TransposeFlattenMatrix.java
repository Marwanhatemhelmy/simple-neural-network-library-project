package utils.matixOperations.specialOperations;

public class TransposeFlattenMatrix {
    public static double[] transposeFlattenedMatrix(double[] flatMatrix, int[] shape) {
        if (shape.length!=2){
            throw new UnsupportedOperationException("transposing function only supports 2d shapes");
        }
        if (flatMatrix.length != shape[0] * shape[1]) {
            throw new IllegalArgumentException("invalid dimensions for the given flat matrix");
        }
        
        double[] transposed = new double[flatMatrix.length];
        
        for (int i = 0; i < shape[0]; i++) {
            for (int j = 0; j < shape[1]; j++) {
                transposed[j * shape[0] + i] = flatMatrix[i * shape[1] + j];
            }
        }
        
        return transposed;
    }
}