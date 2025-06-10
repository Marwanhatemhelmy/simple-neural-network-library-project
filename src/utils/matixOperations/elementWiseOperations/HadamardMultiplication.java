package utils.matixOperations.elementWiseOperations;

import java.util.Arrays;

public class HadamardMultiplication {
    public static double[] multiply(double[] matA, int[] shapeA, double[] matB, int[] shapeB){
        if (!Arrays.equals(shapeA, shapeB)){
            throw new IllegalArgumentException("expected shapeA to equal shapeB");
        }
        
        if (matA.length != matB.length){
            throw new IllegalArgumentException("expected matA length not equal matB length");
        }

        double[] result = new double[matA.length];

        for (int i=0;i<matA.length;i++){
            result[i] = (matA[i]*matB[i]);
        }
        
        return result;
    }
}