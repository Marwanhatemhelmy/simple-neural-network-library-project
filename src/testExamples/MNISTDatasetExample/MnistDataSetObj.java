package testExamples.MNISTDatasetExample;

import data.DataSet;
import tensor.Tensor;

public class MnistDataSetObj implements DataSet {

    public Tensor data;
    public Tensor targets;

    public double[] y;

    public MnistDataSetObj(double[][] data, int[] labels){
        
        this.y = new double[labels.length];
        
        for (int j=0;j<labels.length;j++){
            this.y[j] = labels[j];
        }

        this.data = new Tensor(data);
        this.targets = new Tensor(this.y);
        
    }

    @Override
    public Tensor[] atIndex(int index){
        return new Tensor[]{this.data.get(new int[]{index}), this.targets.get(new int[]{index})};
    }

    @Override
    public int len(){
        return this.targets.shape[0];
    }
    
}