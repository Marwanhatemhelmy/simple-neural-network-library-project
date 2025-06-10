package utils.dataTypeConvertage;

public class DoubleToIntArray {
    public static int[] convertDoubleToIntArray(double[] arr){
        int[] resultConverted = new int[arr.length];
        
        for (int i=0;i<arr.length;i++){
            resultConverted[i] = (int) arr[i];
        }

        return resultConverted;
    }
}