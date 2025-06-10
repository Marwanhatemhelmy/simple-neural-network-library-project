package dense;

import java.util.Arrays;

import randomization.BasicInitialization;
import randomization.Randomizer;
import tensor.Tensor;

public class Linear extends Layer {

    public Tensor input;
    public Tensor weights;
    public Tensor biases;
    public Tensor output;

    public Linear(int in_features, int out_features, Randomizer randomizer, boolean normal){
        super(null, null);
        this.weights = new Tensor(new int[]{out_features, in_features});
        this.biases = new Tensor(new int[]{out_features});
        if (normal){
            randomizer.normal_(this.weights);
            randomizer.normal_(this.biases);
        }else{
            randomizer.uniform_(this.weights);
            randomizer.uniform_(this.biases);
        }
        super.weights = this.weights;
        super.biases = this.biases;
    }

    public Linear(int in_features, int out_features){
        super(null, null);
        this.weights = new Tensor(new int[]{out_features, in_features});
        this.biases = new Tensor(new int[]{out_features});
        
        new BasicInitialization().uniform_(this.biases);;
        new BasicInitialization().uniform_(this.weights);
        super.weights = this.weights;
        super.biases = this.biases;
    }

    public Linear(Tensor weights, Tensor biases){
        super(weights, biases);
        if (weights.rootTensor != weights || biases.rootTensor != biases){
            throw new IllegalArgumentException("weights & biases tensors must have not root tensor, they both must be their own root tensor");
        }
        this.weights = weights;
        this.biases = biases;
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