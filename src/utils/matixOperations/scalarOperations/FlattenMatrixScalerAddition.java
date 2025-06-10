package utils.matixOperations.scalarOperations;

public class FlattenMatrixScalerAddition {
    public static double[] add(double[] nums, double scaler){
        double[] result = new double[nums.length];
        for (int i=0;i<nums.length;i++){
            result[i] = (nums[i]+scaler);
        }
        return result;
    }
}