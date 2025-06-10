package utils.multiDimensionalArrays;

public class GenerateSequentialArray {
    public static int[] sequentialArray(int from, int to){
        int range = to-from;

        if (range<=0){
            throw new IllegalArgumentException("to must be bigger than from");
        }

        int[] sequentialArray = new int[range];

        int current = from;

        for (int i=0;i<range;i++){
            sequentialArray[i] = current;
            current++;
        }

        return sequentialArray;
    }
}