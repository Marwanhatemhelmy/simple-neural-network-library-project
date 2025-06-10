package model;

import dense.Layer;
import tensor.Tensor;

// only supports two trainable parameters (weights & biases)
public class Model {

    public Layer[] layers;
    Tensor[] parametersList;
    
    int numberOfTrainableParameters = 2;

    public void saveLayers(Layer[] layers){
        this.layers = layers;
        this.parametersList = new Tensor[layers.length*numberOfTrainableParameters];
        int index = 0;
        for (int i=0;i<layers.length;i++){
            for (int j=0;j<numberOfTrainableParameters;j++){
                if (j==0){
                    parametersList[index] = layers[i].weights;
                }else{
                    parametersList[index] = layers[i].biases;
                }
                index++;
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