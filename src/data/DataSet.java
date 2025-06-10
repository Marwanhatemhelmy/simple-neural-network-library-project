package data;

import tensor.Tensor;

public interface DataSet {
    
    public Tensor[] atIndex(int index);
    public int len();

}