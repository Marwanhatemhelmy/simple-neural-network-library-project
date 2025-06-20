package testExamples.MNISTDatasetExample;

import activation.Activation;
import activation.Relu;
import data.DataLoader;
import data.DataSet;
import dense.Layer;
import dense.Linear;
import loss.CrossEntropyLoss;
import model.Model;
import optimizer.Adam;
import randomization.Xavier;
import tensor.Tensor;

public class MNISTNeuralNetworkExample {

    public static class SimpleModel extends Model {

        Linear l1;
        Linear l2;

        public SimpleModel(){

            this.l1 = new Linear(784,200, new Xavier(), false);
            this.l2 = new Linear(200,10);

            registerLayers(new Layer[]{this.l1, this.l2});

        }
        
        public Tensor forward(Tensor x){

            x = this.l1.forward(x);

            Activation relu = new Relu(x);
            relu.forward_();
            relu.applyGrad_();

            x = this.l2.forward(x);

            return x;
        }

        public Tensor eval(Tensor x){

            x = this.l1.forward(x);

            Activation relu = new Relu(x);
            x = relu.forward();

            x = this.l2.forward(x);

            return x;
        }

        public void backward(Tensor e, Tensor trueValues){

            e = new CrossEntropyLoss().errorTensor(e, trueValues);
            e = this.l2.backward(e);
            e = this.l1.backward(e);

        }
    }

    public static boolean checkIfMax(int index, double[] array){
        for (int i=0;i<array.length;i++){
            if (array[i]>array[index]){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("start");
        long startTime = System.currentTimeMillis();

        SimpleModel model = new SimpleModel();

        MNISTReader mnistReader = new MNISTReader(0,10000);
        DataSet dataSet = new MnistDataSetObj(mnistReader.trainingImages,mnistReader.trainingLabels);
        System.out.println("finished dataset");
        DataLoader dataLoader = new DataLoader(dataSet, 64);

        long loadEndTime = System.currentTimeMillis();
        long loadDuration = loadEndTime-startTime;

        System.out.println("loading duration : "+loadDuration);
        System.out.println("loaded images");

        Adam optimizer = new Adam(0.001, 0.9, 0.999, 1e-8);

        for (int i=0;i<13;i++){
            for (int j=0;j<dataLoader.totalBatches;j++){

                // squeezing at dimension 1 , to make it 1d instead of 2d tensor
                if (dataLoader.batchAt(j)[1].dimensions != 1){
                    dataLoader.batchAt(j)[1].squeez_(1);
                }

                model.backward(model.forward(dataLoader.batchAt(j)[0]), dataLoader.batchAt(j)[1]);                
                optimizer.updateParameters(model.parameters());
                model.zeroGrad();
            }
            System.out.println("batches completed: "+(i+1));
        }

        // evaluating
        int correct = 0;
        int wrong = 0;
        for (int eval = 0;eval<mnistReader.validationLabels.length;eval++){
            if (checkIfMax(mnistReader.validationLabels[eval], model.eval(new Tensor(mnistReader.validationImages[eval])).data)){
                correct++;
            }else{
                wrong ++;
            }
        }
        
        System.out.println("correct : "+correct);
        System.out.println("wrong : "+wrong);
        System.out.println("accuracy : "+((((double)correct)/((double)correct + (double)wrong))*100.0)+"%");
        
        long endTime = System.currentTimeMillis();
        long duration = endTime-startTime;
        
        System.out.println("duration : "+duration);
        System.out.println("end");
    }

}