package testExamples.PimaIndiansDiabetesDatasetExample;

import activation.Activation;
import activation.Relu;
import activation.Sigmoid;
import data.DataLoader;
import data.DataSet;
import dense.Layer;
import dense.Linear;
import loss.BCELoss;
import model.Model;
import optimizer.Adam;
import randomization.Xavier;
import tensor.Tensor;

public class DiabetesNeuralNetworkExample {

    public static class SimpleModel extends Model {

        Linear l1;
        Linear l2;

        public SimpleModel(){

            this.l1 = new Linear(8, 120, new Xavier(), true);
            this.l2 = new Linear(120, 1, new Xavier(), true);

            saveLayers(new Layer[]{this.l1, this.l2});
        }
        
        public Tensor forward(Tensor x){

            x = this.l1.forward(x);
            Activation relu = new Relu(x);
            relu.forward_();
            relu.applyGrad_();

            x = this.l2.forward(x);
            Activation sigmoid = new Sigmoid(x);
            sigmoid.forward_();
            sigmoid.applyGrad_();

            return x;
        }

        public void backward(Tensor e, Tensor trueValues){

            e = new BCELoss().errorTensor(e, trueValues);
            e = this.l2.backward(e);
            e = this.l1.backward(e);
            
        }
        
    }
    public static void main(String[] args) {
        System.out.println("start");
        long startTime = System.currentTimeMillis();

        SimpleModel model = new SimpleModel();

        diabetesReader dataMSV = new diabetesReader();
        DataSet dataSet = new DiabetesDataSetObj(dataMSV.data, dataMSV.targets);
        DataLoader dataLoader = new DataLoader(dataSet, 32);

        Adam a = new Adam(0.03, 0.9, 0.999, 1e-8);

        // training
        int epochs = 100;
        for (int epoch=0;epoch<epochs;epoch++){
            for (int j=0;j<dataLoader.batches.length;j++){
                model.backward(model.forward(dataLoader.batches[j][0]),dataLoader.batches[j][1]);
                a.updateParameters(model.parameters());
                model.zeroGrad();
            }
        }

        // evaluating
        int correct = 0;
        int wrong = 0;
        for (int v=0;v<dataSet.len();v++){
            double[] value = dataSet.atIndex(v)[0].data;
            double target = dataSet.atIndex(v)[1].data[0];
            double result = model.forward(new Tensor(value)).data[0];
            if ((result>0.5 && target==1.0) || (result<0.5 && target==0.0)){
                correct++;
            }else{
                wrong++;
            }
        }

        System.out.println();
        System.out.println("correct : "+correct);
        System.out.println();
        System.out.println("wrong : "+wrong);
        System.out.println("accuracy : " + ((double) correct/(double) (correct+wrong))*100.0);

        long endTime = System.currentTimeMillis();
        long duration = endTime-startTime;
        
        System.out.println("duration : "+duration);
        System.out.println("end");
    }
}