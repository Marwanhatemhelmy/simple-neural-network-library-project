package utils.multiDimensionalArrays;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FlatteningArray {

    public static List<Double> flatten(Object array) {
        if (!array.getClass().isArray()){
            throw new IllegalArgumentException("Expected object of type array but got \"" + array.getClass().getSimpleName()+"\"");
        }
        List<Double> result = new ArrayList<>();
        recursiveFlatten(array, result);
        return result;
    }

    private static void recursiveFlatten(Object obj, List<Double> result) {
        if (obj.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(obj); i++) {
                recursiveFlatten(Array.get(obj, i), result);
            }
        } else {
            result.add(((Number) obj).doubleValue());
        }
    }
}