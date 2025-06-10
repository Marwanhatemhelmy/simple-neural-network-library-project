package testExamples.PimaIndiansDiabetesDatasetExample;

import data.DataSet;
import normalization.ZScoreNormalization;
import tensor.Tensor;

public class DiabetesDataSetObj implements DataSet {

    public Tensor data;
    public Tensor targets;
    
    public DiabetesDataSetObj(double[][] samples, double[] targets){
        this.data = new Tensor(samples);
        this.targets = new Tensor(targets);
        new ZScoreNormalization().normalize(this.data);
    }

    @Override
    public Tensor[] atIndex(int index){
        return new Tensor[]{this.data.get(new int[]{index}), this.targets.get(new int[]{index})};
    }

    @Override
    public int len(){
        return this.data.shape[0];
    }
    
}