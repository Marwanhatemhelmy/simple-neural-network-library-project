package utils.multiDimensionalArrays;

public class ShiftingArray {
    public static int[] shift(int[] array){

        if (array.length == 1){
            throw new IllegalArgumentException("array.length needs to be greater than 1");
        }

        int[] newArray = new int[array.length-1];

        for (int i=1;i<array.length;i++){
            newArray[i-1] = array[i];
        }

        return newArray;
    }
}