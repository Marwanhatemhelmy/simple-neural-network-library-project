package dense;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import randomization.BasicInitialization;
import randomization.Randomizer;
import tensor.Tensor;

public class Linear extends Layer {

    @JsonIgnore
    public Tensor input;
    @JsonIgnore
    public Tensor output;

    public Linear(int in_features, int out_features, Randomizer randomizer, boolean normal){
        super(null, null);
        
        Tensor weights = new Tensor(new int[]{out_features, in_features});
        Tensor biases = new Tensor(new int[]{out_features});

        if (normal){
            randomizer.normal_(weights);
            randomizer.normal_(biases);
        }else{
            randomizer.uniform_(weights);
            randomizer.uniform_(biases);
        }

        super.weights = weights;
        super.biases = biases;
    }

    public Linear(int in_features, int out_features){
        super(null, null);
        
        Tensor weights = new Tensor(new int[]{out_features, in_features});
        Tensor biases = new Tensor(new int[]{out_features});
        
        new BasicInitialization().uniform_(biases);;
        new BasicInitialization().uniform_(weights);

        super.weights = weights;
        super.biases = biases;
    }

    @JsonCreator
    public Linear(@JsonProperty("weights") Tensor weights, @JsonProperty("biases") Tensor biases){
        super(weights, biases);
        if (weights.rootTensor != weights || biases.rootTensor != biases){
            throw new IllegalArgumentException("weights & biases tensors must have not root tensor, they both must be their own root tensor");
        }
    }

    @Override
    public Tensor forward(Tensor input){
        if (input.shape.length<1 || input.shape.length>2){
            throw new IllegalArgumentException("input tensor need to be 2d if it's a batch tensor, or 1d if it's one sample tensor");
        }

        // previous output
        this.input = input;
        
        if (input.shape.length == 1){
            this.input.unsqueez_(0);
        }

        Tensor transposedWeightsMatrix = this.weights.transpose();
        Tensor transposedWeightsMatrixTimesInput = this.input.matMul(transposedWeightsMatrix);

        Tensor output = this.biases.broadCastAdd(transposedWeightsMatrixTimesInput);
        this.output = output;

        return this.output;
    }
    
    @Override
    public Tensor backward(Tensor error){
        if (!Arrays.equals(error.shape, this.output.shape)){
            throw new IllegalArgumentException("error tensor shape must equal output tensor shape");
        }

        // derivative loss w.r.t weighted input values
        Tensor dldz = this.output.gradTensor().mul(error);
        
        // transpose derivative loss w.r.t weighted input values
        Tensor transposedDlDz = dldz.transpose();

        // update biases gradients
        this.biases.gradTensor().add_(dldz.sumOverDimension(0));

        // derivative loss w.r.t weights
        Tensor dldw = transposedDlDz.matMul(this.input);

        // updating weights gradients
        this.weights.gradTensor().add_(dldw);

        // setting error tensor
        error = dldz.matMul(this.weights);

        // passing error tensor to previous layer
        return error;
    }

}