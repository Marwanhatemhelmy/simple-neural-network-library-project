package utils.multiDimensionalArrays;

public class ApplyDataToRootArray {
    public static void applyDataToRootArray(int[] indeces, double[] subData, double[] rootData){

        if (indeces.length != subData.length){
            throw new IllegalArgumentException("subData array must have same length as indeces array");
        }

        if (indeces.length > rootData.length){
            throw new IllegalArgumentException("indeces.length and subData.length must be both smaller than or equal rootData.length");
        }

        for (int i=0;i<indeces.length;i++){
            rootData[indeces[i]] = subData[i];
        }

    }
}