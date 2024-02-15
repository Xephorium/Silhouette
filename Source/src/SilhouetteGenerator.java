import detection.EdgeDetectorOne;
import io.FileManager;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SilhouetteGenerator {


    /*--- Variable Declarations ---*/

    private BufferedImage bufferedInputImage;
    private BufferedImage bufferedOutputImage;

    private int imageWidth;
    private int imageHeight;


    /*--- Public Methods ---*/

    public void run() throws IOException, ImageReadException {
        readInputImage();
        initializeOutputImage();
        detectEdges();
        writeOutputImageToFile();

    }


    /*--- Private Methods ---*/

    private void readInputImage() throws IOException, ImageReadException {
        File inputImage = FileManager.getDirectoryFiles(new File("input")).get(0);
        bufferedInputImage = Imaging.getBufferedImage(inputImage);
        imageWidth = bufferedInputImage.getWidth();
        imageHeight = bufferedInputImage.getHeight();
    }

    private void initializeOutputImage() {
        bufferedOutputImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_BGR);
        int blackColor = new Color(0, 0, 0).getRGB();
        for(int x = 0; x < imageWidth; x++) {
            for(int y = 0; y < imageHeight; y++) {
                bufferedOutputImage.setRGB(x, y, blackColor);
            }
        }
    }

    private void detectEdges() {
        bufferedOutputImage = EdgeDetectorOne.detectEdges(bufferedInputImage, bufferedOutputImage);
    }

    private void writeOutputImageToFile() {
        try {
            System.out.println("Writing to Output File");
            ImageIO.write(bufferedOutputImage, "PNG", new File("output/output.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
