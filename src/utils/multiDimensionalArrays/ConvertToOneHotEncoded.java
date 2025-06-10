package utils.multiDimensionalArrays;

public class ConvertToOneHotEncoded {
    public static double[] oneHotEncoded(int classIndex, int limit){
        if (classIndex >= limit || classIndex < 0){
            throw new IndexOutOfBoundsException("class index "+classIndex+" out of bounds for length "+limit);
        }

        double[] one_hot_encoded = new double[limit];
        one_hot_encoded[classIndex] = 1.0;
        return one_hot_encoded;
    }

    public static double[][] oneHotEncoded(int[] classesIndices, int limit){
        double[][] one_hot_encoded = new double[classesIndices.length][limit];

        for (int i=0;i<classesIndices.length;i++){
            one_hot_encoded[i] = oneHotEncoded(classesIndices[i], limit);
        }

        return one_hot_encoded;
    }

    public static double[] oneHotEncodedFlattened(int[] classesIndices, int limit){
        double[][] one_hot_encoded = new double[classesIndices.length][limit];
        double[] one_hot_encoded_flattened = new double[classesIndices.length*limit];

        for (int i=0;i<classesIndices.length;i++){
            one_hot_encoded[i] = oneHotEncoded(classesIndices[i], limit);
        }

        for (int j=0;j<classesIndices.length;j++){
            for (int k=0;k<limit;k++){
                one_hot_encoded_flattened[j * limit + k] = one_hot_encoded[j][k];
            }
        }

        return one_hot_encoded_flattened;
    }
}