package testExamples.MNISTDatasetExample;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class MNISTReader {

    public double[][] images;
    public int[] labels;

    public MNISTReader(int numberOfImages){
        try{
            String trainImagesPath = "src/testExamples/MNISTDatasetExample/data/train-images-idx3-ubyte";
            String trainLabelsPath = "src/testExamples/MNISTDatasetExample/data/train-labels-idx1-ubyte";

            int numImages = numberOfImages;
            this.images = readImages(trainImagesPath, numImages);
            this.labels = readLabels(trainLabelsPath, numImages);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static double[][] readImages(String filePath, int numImages) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(filePath));

        int magicNumber = dis.readInt();
        int numberOfImages = dis.readInt();
        
        int rows = dis.readInt();
        int cols = dis.readInt();

        double[][] images = new double[numImages][rows * cols];

        for (int i = 0; i < numImages; i++) {
            for (int j = 0; j < rows * cols; j++) {
                images[i][j] = dis.readUnsignedByte() / 255.0; // normalize
            }
        }
        dis.close();
        return images;
    }

    public static int[] readLabels(String filePath, int numLabels) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(filePath));

        int magicNumber = dis.readInt();
        int numberOfLabels = dis.readInt();

        int[] labels = new int[numLabels];

        for (int i = 0; i < numLabels; i++) {
            labels[i] = dis.readUnsignedByte();
        }
        dis.close();
        return labels;
    }

}