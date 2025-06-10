package utils.multiDimensionalArrays;

public class FindIndecesOfElements {
    public static int[] findIndecesOfElement(int[] data, int val){

        int valCount = 0;

        for (int i : data){
            if (i == val){
                valCount++;
            }
        }

        if (valCount == 0){
            throw new IllegalArgumentException("value : "+val+" not found in data array");
        }

        int[] result = new int[valCount];

        int resultIndex = 0;

        for (int i=0;i<data.length;i++){
            if (data[i] == val){
                result[resultIndex] = i;
                resultIndex++;
            }
        }

        return result;
    
    }
}