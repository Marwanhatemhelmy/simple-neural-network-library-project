package activation;

import tensor.Tensor;
import utils.multiDimensionalArrays.IndecesAtDimension;
import utils.multiDimensionalArrays.VectorActualValuesAtIndeces;

public class SoftMax extends Activation{

    private Tensor mainTensor;
    private int[][] groupedIndeces;
    private double[] vectorMaxLogits;
    private double[] vectorSumExponentiatedLogits;

    public SoftMax(Tensor tensor, int dim) {
        super(tensor);
        this.mainTensor = tensor;
        this.groupedIndeces = IndecesAtDimension.getIndecesGroups(this.mainTensor.shape, dim);
        // IMPORTANT -> the maximum values of each dimension array need to be declared before the sum of exponents
        // because sum of exponents is depending on the maximum values
        this.vectorMaxLogits = this.getVectorMaxLogits();
        this.vectorSumExponentiatedLogits = this.getVectorSumExponentiatedLogits(this.vectorMaxLogits);
    }

    @Override
    public void forward_(){

        int[][] groupedIndeces = this.groupedIndeces;
        double[] vectorMaxLogits = this.vectorMaxLogits;
        double[] vectorSumExponentiatedLogits = this.vectorSumExponentiatedLogits;
        double[] mainTensorData = this.mainTensor.data;

        for (int i=0;i<groupedIndeces.length;i++){

            double max = vectorMaxLogits[i];
            double sumExponentiatedLogits = vectorSumExponentiatedLogits[i];
            double[] values = VectorActualValuesAtIndeces.vectorActualValuesAtIndeces(groupedIndeces[i], mainTensorData);
            
            for (int j=0;j<values.length;j++){

                this.mainTensor.data[groupedIndeces[i][j]] = this.activation(mainTensorData[groupedIndeces[i][j]], max, sumExponentiatedLogits);

            }
            
            this.mainTensor.refreshRootTensor();
            
        }

    }

    @Override
    public void applyGrad_(){
        throw new UnsupportedOperationException("this method isn't completed yet!");
    }

    @Override
    public double activation(double logit) {
        throw new UnsupportedOperationException("not supported activation method signature, need to specify max -> maximum logit & sumExponentiatedLogits -> sum of logits at dimension");
    }

    public double activation(double logit, double max, double sumExponentiatedLogits){
        return Math.exp(logit-max)/sumExponentiatedLogits;
    }

    @Override
    public double derivative(double activation) {
        return activation*(1-activation);
    }

    private double[] getVectorSumExponentiatedLogits(double[] maxLogitsVector){

        int[][] groupedIndeces = this.groupedIndeces;
        double[] mainTensorData = this.mainTensor.data;
        double[] result = new double[this.groupedIndeces.length];

        for (int i=0;i<result.length;i++){
            double[] values = VectorActualValuesAtIndeces.vectorActualValuesAtIndeces(groupedIndeces[i], mainTensorData);
            result[i] = getSumExponentiatedLogits(values, maxLogitsVector[i]);
        }

        return result;
    }

    private double[] getVectorMaxLogits(){

        int[][] groupedIndeces = this.groupedIndeces;
        double[] mainTensorData = this.mainTensor.data;
        double[] result = new double[this.groupedIndeces.length];

        for (int i=0;i<groupedIndeces.length;i++){
            double[] data = VectorActualValuesAtIndeces.vectorActualValuesAtIndeces(groupedIndeces[i], mainTensorData);
            result[i] = getMaxLogit(data);
        }

        return result;
    }

    private static double getSumExponentiatedLogits(double[] logits, double max){

        double sumExponentiatedLogits = 0;

        for (int i=0;i<logits.length;i++){
            sumExponentiatedLogits += (Math.exp(logits[i]-max));
        }

        return sumExponentiatedLogits;
    }

    private static double getMaxLogit(double[] logits){

        double max = Double.NEGATIVE_INFINITY;
    
        for (double value : logits) {
            if (value > max) max = value;
        }

        return max;
    }
    
}