package utils.multiDimensionalArrays;


public class ArrayProduct {
    public static int getProduct(int[] data){
        int multiplier = data[0];
        for (int i=1;i<data.length;i++){
            multiplier *= data[i];
        }
        return multiplier;
    }
}