package utils.dataTypeConvertage;

public class IntToDoubleArrray {
    public static double[] convertIntToDoubleArray(int[] arr){
        double[] resultConverted = new double[arr.length];
        
        for (int i=0;i<arr.length;i++){
            resultConverted[i] = (double) arr[i];
        }

        return resultConverted;
    }
}