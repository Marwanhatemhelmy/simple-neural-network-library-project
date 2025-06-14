package data;

import tensor.Tensor;
import utils.tensors.stacking.TensorStacking;

public class DataLoader {

    public DataSet dataset;
    public int batchSize;
    public int[] indeces;
    public int batchSpan;
    public Tensor[][] batches;
    public int totalBatches;

    public DataLoader(DataSet dataset, int batchSize){
        this.dataset = dataset;
        this.batchSize = batchSize;
        this.batchSpan = dataset.atIndex(0).length;
        this.indeces = new int[dataset.len()];
        this.batches = new Tensor[this.dataset.len()/this.batchSize][this.batchSpan];
        this.totalBatches = this.dataset.len()/this.batchSize;
        this.setIndeces();
        this.setBatches();
    }

    public Tensor[] batchAt(int batchIndex){
        return this.batches[batchIndex];
    }

    private void setIndeces(){
        for (int i=0;i<this.dataset.len();i++){
            this.indeces[i] = i;
        }
    }

    // before stacking units, samples array will equal to:
    // [[tensor(feature1),tensor(feature2),tensor(feature3)],[tensor(target1),tensor(target2),tensor(target3)]]
    //  |                       unit1                      | |                     unit2                     |
    //  ______________________________________________________________________________________________________
    // after stacking, one batch array will be like :
    // [tensor(features),tensor(targets)]
    // | stacked unit1 || stacked unit2 |
    // and the whole batch array will be like so :
    // [[tensor(features1),tensor(targets1)], [tensor(features2),tensor(targets2)]]
    //  |             batch 0              |  |               batch 1            |

    private void setBatches(){
        Tensor[][] samples = new Tensor[this.batchSpan][this.batchSize];
        BatchIndexWrapper batchIndexWrapper = new BatchIndexWrapper();
        loopOverIndeces(samples, batchIndexWrapper);
    }
    
    private void loopOverIndeces(Tensor[][] samples, BatchIndexWrapper batchIndexWrapper){
        for (int i=0;i<this.indeces.length;i++){
            Tensor[] sample = this.dataset.atIndex(this.indeces[i]);
            insertSampleTensorsInSamplesArray(samples, sample, i);
            handelFinishedBatch(i, samples, batchIndexWrapper);
            System.out.println("sample "+(i+1));
        }
    }

    private void insertSampleTensorsInSamplesArray(Tensor[][] samples,Tensor[] thisSample, int sampleIndex){
        for (int i=0;i<thisSample.length;i++){
            samples[i][sampleIndex%this.batchSize] = thisSample[i];
        }
    }

    private void handelFinishedBatch(int sampleIndex, Tensor[][] samples, BatchIndexWrapper batchIndexWrapper){
        if (sampleIndex%this.batchSize != (this.batchSize-1)){return;}
        Tensor[] batch = new Tensor[this.batchSpan];
        stackUnits(samples, batch);
        insertBatchInBatchesArray(batchIndexWrapper, batch);
        resetSamplesArray(samples);
    }

    private static void stackUnits(Tensor[][] samples, Tensor[] batch){
        for (int unit=0;unit<samples.length;unit++){
            Tensor stackedUnit = TensorStacking.stack(samples[unit]);
            batch[unit] = stackedUnit;
        }
    }

    private void insertBatchInBatchesArray(BatchIndexWrapper batchIndexWrapper, Tensor[] batch){
        this.batches[batchIndexWrapper.currentBatchIndex] = batch;
        batchIndexWrapper.currentBatchIndex ++;
    }

    private void resetSamplesArray(Tensor[][] samples){
        for (int i = 0; i < samples.length; i++) {
            for (int j = 0; j < samples[i].length; j++) {
                samples[i][j] = null;
            }
        }
    }
    
}