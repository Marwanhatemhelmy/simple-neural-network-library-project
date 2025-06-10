package utils.matixOperations.broadCasting;

import utils.multiDimensionalArrays.ArrayProduct;
import utils.multiDimensionalArrays.RavelIndex;
import utils.multiDimensionalArrays.UnravelIndex;

public class FlattenMatrixBroadCastAddition {
    public static double[] broadcastAdd(double[] matA, int[] shapeA, double[] matB, int[] shapeB) {
        int[] resultShape = BroadCastShape.broadcastShape(shapeA, shapeB);
        int len = ArrayProduct.getProduct(resultShape);
        double[] result = new double[len];

        for (int i = 0; i < len; i++) {
            int[] idx = UnravelIndex.unravelIndex(i, resultShape);
            
            int[] idxA = BroadCastIndices.broadcastIndices(idx, shapeA, resultShape);
            int[] idxB = BroadCastIndices.broadcastIndices(idx, shapeB, resultShape);

            int aIndex = RavelIndex.getIndex(shapeA, idxA);
            int bIndex = RavelIndex.getIndex(shapeB, idxB);

            result[i] = matA[aIndex] + matB[bIndex];
        }

        return result;
    }
}