package optimizer;

import java.util.HashMap;
import java.util.Map;

import tensor.Tensor;

public class Adam extends Optimizer {

    public double learningRate;
    public double beta1;
    public double beta2;
    public double epsilon;

    public Map<Tensor, HashMap<String, Tensor>> state;

    public Adam(double learningRate, double beta1, double beta2, double epsilon) {
        super(learningRate);
        this.learningRate = learningRate;
        this.beta1 = beta1;
        this.beta2 = beta2;
        this.epsilon = epsilon;
        this.state = new HashMap<>();
    }

    @Override
    public void updateParameters(Tensor[] parameters){
        for (int i=0;i<parameters.length;i++){
            parameters[i].setData(this.updateParameter(parameters[i], parameters[i].gradTensor()).data);
        }
    }
    
    @Override
    public Tensor updateParameter(Tensor param, Tensor grad) {

        state.putIfAbsent(param, new HashMap<>());
        HashMap<String, Tensor> paramState = state.get(param);

        Tensor mts = grad.copy();
        mts.fill_(0.0);
        Tensor vts = grad.copy();
        vts.fill_(0.0);

        paramState.putIfAbsent("m", mts);
        paramState.putIfAbsent("v", vts);
        paramState.putIfAbsent("step", new Tensor(new double[]{0}));

        Tensor m = paramState.get("m");
        Tensor v = paramState.get("v");
        Tensor step = paramState.get("step");

        step.data[0] += 1;

        Tensor mt = m.mul(beta1).add(grad.mul(1-beta1));
        Tensor vt = v.mul(beta2).add(grad.mul(grad).mul(1 - beta2));

        double beta1_t = 1 - Math.pow(beta1, step.data[0]);
        double beta2_t = 1 - Math.pow(beta2, step.data[0]);
        Tensor mt_hat = mt.mul(1.0 / beta1_t);
        Tensor vt_hat = vt.mul(1.0 / beta2_t);

        paramState.put("m", mt);
        paramState.put("v", vt);
        
        return param.add(mt_hat.div(vt_hat.sqrt().add(epsilon)).mul(-learningRate));
    }

}