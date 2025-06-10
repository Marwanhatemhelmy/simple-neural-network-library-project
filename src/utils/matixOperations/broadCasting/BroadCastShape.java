package utils.matixOperations.broadCasting;

public class BroadCastShape {
    public static int[] broadcastShape(int[] shapeA, int[] shapeB) {
        int len = Math.max(shapeA.length, shapeB.length);
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            int a = i >= len - shapeA.length ? shapeA[i - (len - shapeA.length)] : 1;
            int b = i >= len - shapeB.length ? shapeB[i - (len - shapeB.length)] : 1;
            if (a != b && a != 1 && b != 1) throw new IllegalArgumentException("broadcasting can't be performed on shapes A & B");
            result[i] = Math.max(a, b);
        }
        return result;
    }
}