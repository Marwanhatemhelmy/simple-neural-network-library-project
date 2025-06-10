package utils.multiDimensionalArrays;

import java.util.ArrayList;
import java.util.List;

public class IndecesAtDimension {

    public static int[][] getIndicesAtDimension(int[] shape, int dim) {
        int totalSize = 1;
        for (int s : shape) totalSize *= s;

        int[] strides = new int[shape.length];
        strides[shape.length - 1] = 1;
        for (int i = shape.length - 2; i >= 0; i--) {
            strides[i] = strides[i + 1] * shape[i + 1];
        }

        List<List<Integer>> groups = new ArrayList<>();
        for (int i = 0; i < shape[dim]; i++) {
            groups.add(new ArrayList<>());
        }

        for (int flat = 0; flat < totalSize; flat++) {
            int[] coords = new int[shape.length];
            int remaining = flat;
            for (int i = 0; i < shape.length; i++) {
                coords[i] = remaining / strides[i];
                remaining = remaining % strides[i];
            }

            int key = coords[dim];
            groups.get(key).add(flat);
        }

        int[][] result = new int[shape[dim]][];
        for (int i = 0; i < shape[dim]; i++) {
            List<Integer> group = groups.get(i);
            result[i] = group.stream().mapToInt(Integer::intValue).toArray();
        }

        return result;
    }

    public static int[][] getIndecesGroups(int[] shape, int dim) {
        
        List<List<Integer>> groups = new ArrayList<>();
        
        int stride = 1;
        for (int i = dim + 1; i < shape.length; i++) {
            stride *= shape[i];
        }
        
        int repeat = 1;
        for (int i = 0; i < dim; i++) {
            repeat *= shape[i];
        }
        
        for (int r = 0; r < repeat; r++) {
            for (int offset = 0; offset < stride; offset++) {
                List<Integer> group = new ArrayList<>();
                for (int i = 0; i < shape[dim]; i++) {
                    int index = r * shape[dim] * stride + i * stride + offset;
                    group.add(index);
                }
                groups.add(group);
            }
        }
        
        int[][] result = new int[groups.size()][];
        for (int i = 0; i < groups.size(); i++) {
            result[i] = groups.get(i).stream().mapToInt(Integer::intValue).toArray();
        }
        
        return result;
    }
}