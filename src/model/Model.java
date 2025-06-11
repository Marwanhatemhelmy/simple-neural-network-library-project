package model;

import dense.Layer;
import tensor.Tensor;

// only supports two trainable parameters (weights & biases)
public class Model {

    public Layer[] layers;
    Tensor[] parametersList;
    int numberOfTrainableParameters = 2;

    public void registerLayers(Layer[] layers){
        this.layers = layers;
        this.setParameters(this.numberOfTrainableParameters);
    }

    private void setParameters(int numberOfTrainableParameters){
        this.parametersList = new Tensor[layers.length*numberOfTrainableParameters];
        for (int i=0;i<layers.length;i++){
            for (int j=0;j<numberOfTrainableParameters;j++){
                if (j==0){
                    parametersList[i * numberOfTrainableParameters + j] = layers[i].weights;
                }else{
                    parametersList[i * numberOfTrainableParameters + j] = layers[i].biases;
                }
            }
        }
    }

    public Tensor[] parameters(){
        return this.parametersList;
    }

    public void zeroGrad(){
        for (int i=0;i<this.parametersList.length;i++){
            parametersList[i].zeroGrad_();
        }
    }
    
}