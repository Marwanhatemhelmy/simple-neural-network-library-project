package utils.matixOperations.matrixMultiplication;

public class FlattenMatrixMultiplication {
    public static double[] matrixMultiply(double[] A, int[] shapeA, double[] B, int[] shapeB) {
        int rowsA = shapeA[0], colsA = shapeA[1];
        int rowsB = shapeB[0], colsB = shapeB[1];

        if (shapeA.length!=2 || shapeB.length!=2){
            throw new UnsupportedOperationException("matrix multiplication function only supports 2d shapes");
        }

        if (colsA != rowsB) {
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
        }

        double[] outFlatMat = new double[rowsA * colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                double sum = 0;
                for (int k = 0; k < colsA; k++) {
                    int indexA = i * colsA + k;
                    int indexB = k * colsB + j;
                    sum += A[indexA] * B[indexB];
                }
                outFlatMat[i * colsB + j] = sum; 
            }
        }
        return outFlatMat;
    }

    public static int[] shapeAfterMatMultiplication(int[] shapeA, int[] shapeB){
        if (shapeA.length!=2 || shapeB.length!=2){
            throw new UnsupportedOperationException("matrix multiplication function only supports 2d shapes");
        }
        
        return new int[]{shapeA[0],shapeB[1]};
    }
}