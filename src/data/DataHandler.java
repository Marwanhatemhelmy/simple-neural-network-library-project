package data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public static Layer[] load(String filePath){
        ObjectMapper mapper = new ObjectMapper();
        try{
            String jsonString = Files.readString(Paths.get(filePath));
            Layer[] layers = mapper.readValue(jsonString, Layer[].class);
            return layers;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}