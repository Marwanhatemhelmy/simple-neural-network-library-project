package utils.multiDimensionalArrays;

public class VectorActualValuesAtIndeces {
    
    public static double[] vectorActualValuesAtIndeces(int[] indeces, double[] data){
        double[] result = new double[indeces.length];
        for (int i=0;i<indeces.length;i++){
            result[i] = data[indeces[i]];
        }
        return result;
    }

    public static int[] vectorActualValuesAtIndeces(int[] indeces, int[] data){
        int[] result = new int[indeces.length];
        for (int i=0;i<indeces.length;i++){
            result[i] = data[indeces[i]];
        }
        return result;
    }

}