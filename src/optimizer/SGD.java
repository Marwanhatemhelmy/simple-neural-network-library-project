package optimizer;

import tensor.Tensor;

public class SGD extends Optimizer{
    
    public double lr;

    public SGD(double lr){
        super(lr);
        this.lr = lr;
    }

    @Override
    public void updateParameters(Tensor[] parameters){
        for (int i=0;i<parameters.length;i++){
            parameters[i].setData(this.updateParameter(parameters[i], parameters[i].gradTensor()).data);
        }
    }

    @Override
    public Tensor updateParameter(Tensor param, Tensor grad){
        return param.add(grad.mul(-this.lr));
    }

}