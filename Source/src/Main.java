import org.apache.commons.imaging.ImageReadException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ImageReadException {
        SilhouetteGenerator silhouetteGenerator = new SilhouetteGenerator();
        silhouetteGenerator.run();
    }
}