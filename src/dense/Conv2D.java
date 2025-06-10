package dense;

import randomization.BasicInitialization;
import tensor.Tensor;
import utils.matixOperations.elementWiseOperations.FlattenMatrixAddition;
import utils.tensors.convolution.Convolve2D;

// this class performs cross-correlation over batches of images containing 1 or more channels
// NOTE this class is still not fully created yet!
public class Conv2D extends Layer{

    public Tensor input;
    public Tensor weights;
    public Tensor biases;
    public Tensor output;

    public int c_out;
    public int c_in;
    public int stride;

    public Conv2D(Tensor kernel, Tensor biases) {
        super(kernel, biases);

        this.weights = kernel;
        this.biases = biases;
    }

    public Conv2D(int in_channels, int out_channels, int kernelSize, int stride) {
        super(null, null);

        this.weights = new Tensor(new int[]{out_channels, in_channels, kernelSize, kernelSize});
        this.biases = new Tensor(new int[]{out_channels});
        this.stride = stride;
        this.c_in = in_channels;
        this.c_out = out_channels;

        BasicInitialization basicInitialization = new BasicInitialization();
        basicInitialization.uniform_(this.weights);
        basicInitialization.uniform_(this.weights);
        
        super.weights = this.weights;
        super.biases = this.biases;
    }

    @Override
    public Tensor forward(Tensor input) {

        if (input.shape.length != 4){
            throw new IllegalArgumentException("expected input tensor to be 4d but got "+input.shape.length);
        }

        this.input = input;

        int[] kernelShape = this.weights.get(new int[]{0,0}).shape;
        int[] inputShape = input.get(new int[]{0,0}).shape;
        int[] convoloution2DShape = Convolve2D.convolve2DShape(kernelShape, inputShape, this.stride);

        int batchSize = input.shape[0];
        int out_H = convoloution2DShape[0];
        int out_W = convoloution2DShape[1];

        this.output = new Tensor(new int[]{batchSize, this.c_out, out_H, out_W});

        for (int n=0;n<input.shape[0];n++){
            for (int i=0;i<this.c_out;i++){

                Tensor out_channel = new Tensor(new int[]{out_H,out_W});

                for (int j=0;j<this.c_in;j++){

                    double[] out_channelData = out_channel.data;
                    int[] out_channelShape = out_channel.shape;
                    double[] convoloution2DData = Convolve2D.convolve2D(
                                input.get(new int[]{n,j}).data
                                , input.get(new int[]{n,j}).shape
                                , this.weights.get(new int[]{i,j}).data
                                , this.weights.get(new int[]{i,j}).shape
                                , this.stride
                            );
                    double[] addedChannelsData = FlattenMatrixAddition.matrixAdd(out_channelData, out_channelShape, convoloution2DData, convoloution2DShape);
                    
                    out_channel.setData(addedChannelsData);

                }

                this.output.get(new int[]{n,i}).setData(out_channel.data);

            }
        }

        return this.output;

    }

    @Override
    // this method is still not created yet!
    public Tensor backward(Tensor error) {
        throw new UnsupportedOperationException("Unimplemented method 'backward'");
    }
    
}