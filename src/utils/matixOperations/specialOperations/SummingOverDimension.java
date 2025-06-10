package utils.matixOperations.specialOperations;

import utils.multiDimensionalArrays.IndecesAtDimension;

public class SummingOverDimension {
    public static double[] sumOverDimension(int dim, int[] shape, double[] data){
        if (dim < 0 || dim >= shape.length){
            throw new IndexOutOfBoundsException("index dim "+dim+" out of bounds for shape length "+shape.length);
        }

        int[][] indecesGroups = IndecesAtDimension.getIndecesGroups(shape, dim);
        double[] result = new double[indecesGroups.length];

        for (int i=0;i<indecesGroups.length;i++){
            for (int j=0;j<indecesGroups[i].length;j++){
                result[i] += data[indecesGroups[i][j]];
            }
        }

        return result;
    }
    public static int[] sumOverDimensionShape(int dim, int[] shape){
        if (dim < 0 || dim >= shape.length){
            throw new IndexOutOfBoundsException("index dim "+dim+" out of bounds for shape length "+shape.length);
        }

        int[] newShape = new int[shape.length-1];

        int shapeIndex = 0;
        for (int i=0;i<shape.length;i++){
            if (i!=dim){
                newShape[shapeIndex] = shape[i];
                shapeIndex++;
            }
        }

        return newShape;
    }
}