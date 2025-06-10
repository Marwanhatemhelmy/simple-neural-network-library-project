package utils.matixOperations.broadCasting;

public class BroadCastIndices {
    public static int[] broadcastIndices(int[] indices, int[] originalShape, int[] newShape) {
        int[] broadcasted = new int[originalShape.length];
        int dimOffset = newShape.length - originalShape.length;
    
        for (int i = 0; i < originalShape.length; i++) {
            if (originalShape[i] == 1) {
                broadcasted[i] = 0;
            } else {
                broadcasted[i] = indices[i + dimOffset];
            }
        }
    
        return broadcasted;
    }
}