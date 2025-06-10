package data;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import dense.Layer;
import model.Model;

public class DataHandler {

    public static void save(Model model, String filePath){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter()
            .writeValue(new File(filePath), model.layers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // this method is still not created yet!
    public static Layer[] load(String filePath){
        throw new UnsupportedOperationException("this method is still under maintainance");
    }

}