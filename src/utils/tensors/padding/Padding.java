package utils.tensors.padding;

public class Padding {

    public static double[] getPadded2DArray(double[] data, int[] shape, int pad, int val){

        if (shape.length!=2){
            throw new IllegalArgumentException("data array must be 2d");
        }

        int rows = shape[0];
        int cols = shape[1];

        int newRows = rows + 2 * pad;
        int newCols = cols + 2 * pad;

        double[] padded = new double[newRows * newCols];

        for (int i = 0; i < padded.length; i++) {
            padded[i] = val;
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int originalIndex = r * cols + c;
                int paddedRow = r + pad;
                int paddedCol = c + pad;
                int paddedIndex = paddedRow * newCols + paddedCol;
                padded[paddedIndex] = data[originalIndex];
            }
        }

        return padded;

    }

    public static int[] shape2DPadded(int[] shape, int pad, int val){

        int rows = shape[0];
        int cols = shape[1];

        int newRows = rows + 2 * pad;
        int newCols = cols + 2 * pad;

        return new int[]{newRows, newCols};
        
    }

}