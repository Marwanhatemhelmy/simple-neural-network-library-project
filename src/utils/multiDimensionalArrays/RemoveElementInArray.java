package utils.multiDimensionalArrays;

import java.util.Arrays;

public class RemoveElementInArray {
    public static int[] removeElements(int[] data, int[] indeces){

        int[] sortedIndeces = indeces.clone();
        Arrays.sort(sortedIndeces);

        if (!Arrays.equals(indeces, sortedIndeces)){
            throw new IllegalArgumentException("indeces must be arranged in ascending order");
        }

        int[] result = new int[data.length-indeces.length];

        int indecesIndex = 0;
        int index = 0;

        for (int i=0;i<data.length;i++){

            if (indeces[indecesIndex] >= data.length){
                throw new IndexOutOfBoundsException("index "+indeces[indecesIndex]+" out of bounds for length "+data.length);
            }

            if (indeces[indecesIndex] != i){
                result[index] = data[i];
                index++;
            }else{
                if (indecesIndex != (indeces.length-1)){
                    indecesIndex++;
                }
            }
        }

        return result;

    }
}