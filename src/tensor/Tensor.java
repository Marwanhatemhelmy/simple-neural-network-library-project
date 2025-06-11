package tensor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import utils.matixOperations.broadCasting.BroadCastShape;
import utils.matixOperations.broadCasting.FlattenMatrixBroadCastAddition;
import utils.matixOperations.elementWiseOperations.FlattenMatrixAddition;
import utils.matixOperations.elementWiseOperations.HadamardMultiplication;
import utils.matixOperations.matrixMultiplication.FlattenMatrixMultiplication;
import utils.matixOperations.scalarOperations.FlattenMatrixScalerAddition;
import utils.matixOperations.scalarOperations.ScalerMultiplication;
import utils.matixOperations.specialOperations.SummingOverDimension;
import utils.matixOperations.specialOperations.TransposeFlattenMatrix;
import utils.multiDimensionalArrays.AllocateSubArrayInFlattenArray;
import utils.multiDimensionalArrays.FlatteningArray;
import utils.multiDimensionalArrays.GenerateSequentialArray;
import utils.multiDimensionalArrays.ArrayProduct;
import utils.multiDimensionalArrays.FindIndecesOfElements;
import utils.multiDimensionalArrays.RegeneratingNdArray;
import utils.multiDimensionalArrays.RemoveElementInArray;
import utils.multiDimensionalArrays.VectorActualValuesAtIndeces;
import utils.tensors.inPlace.ApplySubArrayToMainTensor;
import utils.tensors.unSqueezing.TensorUnsqueez;

public class Tensor{

    @JsonIgnore
    public Tensor rootTensor = this;
    @JsonProperty
    private int[] indeces;
    @JsonProperty
    private boolean gradFlag = false;
    @JsonProperty
    public double[] data;
    @JsonIgnore
    public double[] grad;
    @JsonProperty
    public int dimensions;
    @JsonProperty
    public int[] shape;

    public Tensor(){}

    public Tensor(int[] shape){
        this.data = new double[ArrayProduct.getProduct(shape)];
        this.shape = shape;
        this.dimensions = this.shape.length;
        this.grad = new double[this.data.length];
        this.indeces = GenerateSequentialArray.sequentialArray(0, this.data.length);
    }

    public Tensor(double val){
        this.data = new double[]{val};
        this.dimensions = 0;
        this.shape = new int[]{};
        this.grad = new double[this.data.length];
        this.indeces = new int[]{0};
    }

    public Tensor(double[] data){
        this.data = data;
        this.dimensions = 1;
        this.shape = new int[]{data.length};
        this.grad = new double[this.data.length];
        this.indeces = GenerateSequentialArray.sequentialArray(0, this.data.length);
    }

    public Tensor(double[][] data){
        this.data = FlatteningArray.flatten(data).stream().mapToDouble(Double::doubleValue).toArray();
        this.dimensions = 2;
        this.shape = new int[]{data.length,data[0].length};
        this.grad = new double[this.data.length];
        this.indeces = GenerateSequentialArray.sequentialArray(0, this.data.length);
    }

    public Tensor(double[][][] data){
        this.data = FlatteningArray.flatten(data).stream().mapToDouble(Double::doubleValue).toArray();
        this.dimensions = 3;
        this.shape = new int[]{data.length,data[0].length,data[0][0].length};
        this.grad = new double[this.data.length];
        this.indeces = GenerateSequentialArray.sequentialArray(0, this.data.length);
    }

    public Tensor(double[][][][] data){
        this.data = FlatteningArray.flatten(data).stream().mapToDouble(Double::doubleValue).toArray();
        this.dimensions = 4;
        this.shape = new int[]{data.length,data[0].length,data[0][0].length,data[0][0][0].length};
        this.grad = new double[this.data.length];
        this.indeces = GenerateSequentialArray.sequentialArray(0, this.data.length);
    }

    // in-place operations

    public void zeroGrad_(){
        this.refreshTensor();
        for (int i=0;i<this.data.length;i++){
            if (this.gradFlag){
                this.data[i] = 0;
            }else{
                this.grad[i] = 0;
            }
        }
        this.refreshRootTensor();
    }

    public void fill_(double val){
        this.refreshTensor();
        for (int i=0;i<this.data.length;i++){
            this.data[i] = val;
        }
        this.refreshRootTensor();
    }

    public void unsqueez_(int dim){
        if (this.rootTensor != this){
            throw new IllegalArgumentException("tensor must be the root tensor in order to perform unsqueez operation in-place");
        }

        this.shape = TensorUnsqueez.unsqueeze(this.shape, dim);

        this.dimensions = this.shape.length;
    }

    public void squeez_(int dim){
        if (this.rootTensor != this){
            throw new IllegalArgumentException("tensor must be the root tensor in order to perform squeez operation in-place");
        }

        this.shape = RemoveElementInArray.removeElements(this.shape, new int[]{dim});

        this.dimensions = this.shape.length;
    }

    public void squeez_(){
        if (this.rootTensor != this){
            throw new IllegalArgumentException("tensor must be the root tensor in order to perform squeez operation in-place");
        }

        this.shape = RemoveElementInArray.removeElements(this.shape, FindIndecesOfElements.findIndecesOfElement(this.shape, 1));

        this.dimensions = this.shape.length;
    }

    public void mul_(double scalar){
        this.refreshTensor();
        this.data = ScalerMultiplication.mul(this.data.clone(), scalar);
        this.refreshRootTensor();
    }

    public void add_(Tensor tensor){
        this.refreshTensor();
        this.data = FlattenMatrixAddition.matrixAdd(this.data.clone(), this.shape.clone(), tensor.data.clone(), tensor.shape.clone());
        this.refreshRootTensor();
    }

    // functional operations

    public Tensor mul(double scalar) {
        this.refreshTensor();
        Tensor result = this.copy();
        result.data = ScalerMultiplication.mul(this.data.clone(), scalar);
        
        return result;
    }

    public Tensor mul(Tensor tensor) {
        this.refreshTensor();
        Tensor result = this.copy();
        result.data = HadamardMultiplication.multiply(this.data.clone(), this.shape, tensor.data.clone(), tensor.shape.clone());
        
        return result;
    }

    public Tensor matMul(Tensor tensor){
        this.refreshTensor();
        Tensor result = new Tensor(FlattenMatrixMultiplication.matrixMultiply(this.data.clone(), this.shape.clone(), tensor.data.clone(), tensor.shape.clone()));
        result.shape = FlattenMatrixMultiplication.shapeAfterMatMultiplication(this.shape.clone(), tensor.shape.clone());
        result.dimensions = result.shape.length;

        return result;
    }

    public Tensor broadCastAdd(Tensor tensor){
        this.refreshTensor();
        Tensor result = new Tensor(FlattenMatrixBroadCastAddition.broadcastAdd(this.data.clone(), this.shape.clone(), tensor.data.clone(), tensor.shape.clone()));
        result.shape = BroadCastShape.broadcastShape(this.shape.clone(), tensor.shape.clone());
        result.dimensions = result.shape.length;

        return result;
    }

    public Tensor sumOverDimension(int dim){
        this.refreshTensor();
        Tensor result = new Tensor(SummingOverDimension.sumOverDimension(dim, this.shape.clone(), this.data.clone()));
        result.shape = SummingOverDimension.sumOverDimensionShape(dim, this.shape.clone());
        result.dimensions = result.shape.length;

        return result;
    }

    public Tensor add(double scaler) {
        this.refreshTensor();
        Tensor result = this.copy();
        result.data = FlattenMatrixScalerAddition.add(this.data.clone(), scaler);
        
        return result;
    }

    public Tensor add(Tensor tensor) {
        this.refreshTensor();
        Tensor result = this.copy();
        result.data = FlattenMatrixAddition.matrixAdd(this.data.clone(), this.shape.clone(), tensor.data.clone(), tensor.shape.clone());
        
        return result;
    }

    public Tensor sqrt() {
        this.refreshTensor();
        Tensor result = this.copy();

        for (int i = 0; i < this.data.length; i++) {
            result.data[i] = Math.sqrt(data[i]);
        }

        return result;
    }

    public Tensor div(double scalar){
        this.refreshTensor();
        Tensor result = this.copy();

        for (int i=0;i<this.data.length;i++){
            result.data[i] = (this.data[i]/scalar);
        }

        return result;
    }

    public Tensor div(Tensor other) {
        this.refreshTensor();
        Tensor result = this.copy();

        for (int i = 0; i < this.data.length; i++) {
            result.data[i] = (this.data[i] / other.data[i]);
        }
        return result;
    }

    public Tensor transpose(){
        this.refreshTensor();
        Tensor transposedTensor = new Tensor(TransposeFlattenMatrix.transposeFlattenedMatrix(this.data.clone(), this.shape.clone()));
        transposedTensor.shape = new int[]{this.shape[1],this.shape[0]};
        transposedTensor.dimensions = transposedTensor.shape.length;

        return transposedTensor;
    }

    public Tensor copy(){
        this.refreshTensor();
        Tensor copy = new Tensor(this.data.clone());
        copy.grad = this.grad.clone();
        copy.shape = this.shape.clone();
        copy.dimensions = this.dimensions;
        copy.indeces = GenerateSequentialArray.sequentialArray(0, copy.data.length);

        return copy;
    }

    public Tensor gradTensor(){
        this.refreshTensor();
        Tensor result = new Tensor(this.grad);
        result.shape = this.shape;
        result.dimensions = this.dimensions;
        result.rootTensor = this.rootTensor;
        result.indeces = this.indeces;
        result.gradFlag = true;

        return result;
    }

    public Tensor get(int indeces[]){
        this.refreshTensor();
        Tensor subTensor = new Tensor(VectorActualValuesAtIndeces.vectorActualValuesAtIndeces(AllocateSubArrayInFlattenArray.getIndices(this.shape, indeces), this.data));
        subTensor.grad = VectorActualValuesAtIndeces.vectorActualValuesAtIndeces(AllocateSubArrayInFlattenArray.getIndices(this.shape, indeces), this.grad);
        subTensor.indeces = VectorActualValuesAtIndeces.vectorActualValuesAtIndeces(AllocateSubArrayInFlattenArray.getIndices(this.shape, indeces), this.indeces);
        subTensor.dimensions = (this.dimensions - indeces.length);
        subTensor.shape = AllocateSubArrayInFlattenArray.getShape(indeces.length, this.shape);
        subTensor.rootTensor = this.rootTensor;
        return subTensor;
    }

    public void refreshTensor(){
        if (!this.gradFlag){
            this.data = VectorActualValuesAtIndeces.vectorActualValuesAtIndeces(this.indeces, this.rootTensor.data);
            this.grad = VectorActualValuesAtIndeces.vectorActualValuesAtIndeces(this.indeces, this.rootTensor.grad);
        }else{
            this.data = VectorActualValuesAtIndeces.vectorActualValuesAtIndeces(this.indeces, this.rootTensor.grad);
        }
    }

    public void refreshRootTensor(){
        ApplySubArrayToMainTensor.applySubArrayToMainTensor(this.indeces, this.data, this.rootTensor, this.gradFlag);
        
        if (this.gradFlag){
            ApplySubArrayToMainTensor.applySubArrayToMainTensor(this.indeces, this.data, this.rootTensor, true);
        }else{
            ApplySubArrayToMainTensor.applySubArrayToMainTensor(this.indeces, this.grad, this.rootTensor, true);
        }
    }

    public void print(){
        this.refreshTensor();
        System.out.println("tensor("+this.val()+")");
    }

    public List<Object> val(){
        this.refreshTensor();
        return RegeneratingNdArray.convertToArrayList(this.data, this.shape);
    }

    @JsonProperty
    public void setData(double[] newData) {
        if (this.rootTensor != this){
            this.refreshTensor();
        }

        this.data = newData;

        if (this.grad == null) {
            this.grad = new double[newData.length];
        }

        ApplySubArrayToMainTensor.applySubArrayToMainTensor(this.indeces, this.data, this.rootTensor, this.gradFlag);
    }
    
}