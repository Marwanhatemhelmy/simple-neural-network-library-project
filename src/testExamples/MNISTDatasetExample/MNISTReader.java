package testExamples.MNISTDatasetExample;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class MNISTReader {

    public double[][] trainingImages;
    public int[] trainingLabels;

    public double[][] validationImages;
    public int[] validationLabels;

    public MNISTReader(int from, int to){
        try{
            String trainImagesPath = "src/testExamples/MNISTDatasetExample/data/train-images-idx3-ubyte";
            String trainLabelsPath = "src/testExamples/MNISTDatasetExample/data/train-labels-idx1-ubyte";
            
            String validationImagesPath = "src/testExamples/MNISTDatasetExample/data/t10k-images-idx3-ubyte";
            String validationLabelsPath = "src/testExamples/MNISTDatasetExample/data/t10k-labels-idx1-ubyte";

            this.trainingImages = readImages(trainImagesPath, from, to);
            this.trainingLabels = readLabels(trainLabelsPath, from, to);

            this.validationImages = readImages(validationImagesPath, 0, 10000);
            this.validationLabels = readLabels(validationLabelsPath, 0, 10000);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static double[][] readImages(String filePath, int from, int to) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(filePath));

        int magicNumber = dis.readInt();
        int numberOfImages = dis.readInt();
        int rows = dis.readInt();
        int cols = dis.readInt();

        if (magicNumber != 2051) throw new IOException("Invalid image file magic number");
        if (from < 0 || to > numberOfImages || from >= to) throw new IOException("Invalid from/to range");

        int imageSize = rows * cols;
        int count = to - from;

        // Skip images before 'from'
        dis.skipBytes(from * imageSize);

        double[][] images = new double[count][imageSize];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < imageSize; j++) {
                images[i][j] = dis.readUnsignedByte() / 255.0;
            }
        }

        dis.close();
        return images;
    }

    public static int[] readLabels(String filePath, int from, int to) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(filePath));

        int magicNumber = dis.readInt();
        int numberOfLabels = dis.readInt();

        if (magicNumber != 2049) throw new IOException("Invalid label file magic number");
        if (from < 0 || to > numberOfLabels || from >= to) throw new IOException("Invalid from/to range");

        int count = to - from;

        // Skip labels before 'from'
        dis.skipBytes(from);

        int[] labels = new int[count];
        for (int i = 0; i < count; i++) {
            labels[i] = dis.readUnsignedByte();
        }

        dis.close();
        return labels;
    }

}