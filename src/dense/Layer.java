package dense;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import tensor.Tensor;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Linear.class, name = "linear")
    // need to specify a type for convolutional after it's fully created
})
public abstract class Layer {

    public Tensor weights;
    public Tensor biases;

    public Layer(Tensor weights, Tensor biases){
        this.weights = weights;
        this.biases = biases;
    }

    public abstract Tensor forward(Tensor input);
    public abstract Tensor backward(Tensor error);
    
}