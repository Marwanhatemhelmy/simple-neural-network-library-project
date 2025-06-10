package utils.tensors.convolution;

public class Convolve2D {

    public static double[] convolve2D(double[] data, int[] dataShape, double[] kernel, int[] kernelShape, int stride) {
        
        int inputHeight = dataShape[0];
        int inputWidth = dataShape[1];
        int kernelHeight = kernelShape[0];
        int kernelWidth = kernelShape[1];

        int outputHeight = (inputHeight - kernelHeight) / stride + 1;
        int outputWidth = (inputWidth - kernelWidth) / stride + 1;
        double[] output = new double[outputHeight * outputWidth];

        for (int i = 0; i < outputHeight; i++) {
            for (int j = 0; j < outputWidth; j++) {
                double sum = 0.0;
                for (int m = 0; m < kernelHeight; m++) {
                    for (int n = 0; n < kernelWidth; n++) {
                        int row = i * stride + m;
                        int col = j * stride + n;
                        double dataVal = data[row * inputWidth + col];
                        double kernelVal = kernel[m * kernelWidth + n];
                        sum += dataVal * kernelVal;
                    }
                }
                output[i * outputWidth + j] = sum;
            }
        }

        return output;

    }

    public static int[] convolve2DShape(int[] kernelShape, int[] dataShape, int stride){

        int inputHeight = dataShape[0];
        int inputWidth = dataShape[1];
        int kernelHeight = kernelShape[0];
        int kernelWidth = kernelShape[1];

        int outputHeight = (inputHeight - kernelHeight) / stride + 1;
        int outputWidth = (inputWidth - kernelWidth) / stride + 1;

        return new int[]{outputHeight, outputWidth};
        
    }

    public static void main(String[] args) {
        
    }
    
}