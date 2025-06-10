package testExamples.PimaIndiansDiabetesDatasetExample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class diabetesReader {

    public String filePath = "src/testExamples/PimaIndiansDiabetesDatasetExample/diabetes.csv";

    public double[][] data = new double[768][9];
    public double[] targets = new double[768];

    public diabetesReader(){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int rowIndex = 0;

            while ((line = br.readLine()) != null) {
                // canceling first raw as it's lables row
                if (rowIndex == 0) {
                    // but also increamenting as it's still considered a row in the dataset
                    rowIndex++;
                    continue;
                }

                // spliting the row
                String[] values = line.split(",");
                // setting the new inputs to train the model on
                double[] inputs = new double[8];

                for (int v = 0; v < inputs.length; v++) {
                    inputs[v] = (Double.parseDouble(values[v]));
                }

                data[rowIndex-1] = inputs;
                targets[rowIndex-1] = (Double.parseDouble(values[8]));
                
                rowIndex++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
