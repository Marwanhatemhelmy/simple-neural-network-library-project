package utils.matixOperations.elementWiseOperations;

import java.util.Arrays;

public class FlattenMatrixAddition {
    public static double[] matrixAdd(double[] matA, int[] shapeA, double[] matB, int[] shapeB) {
        if (shapeA == shapeB){
            throw new IllegalArgumentException("shapeA and shapeB should not have same address in memory");
        }

        if (!Arrays.equals(shapeA, shapeB)){
            throw new IllegalArgumentException("expected shapeA to equal shapeB");
        }

        if (matA.length != matB.length) {
            throw new IllegalArgumentException("Matrix sizes do not match for addition.");
        }

        double[] result = new double[matA.length];

        for (int i = 0; i < matA.length; i++) {
            result[i] = (matA[i] + matB[i]);
        }

        return result;
    }
}