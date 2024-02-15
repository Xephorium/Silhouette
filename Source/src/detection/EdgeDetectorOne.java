package detection;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EdgeDetectorOne {


    /*--- Variable Declarations ---*/

    private static final int edgeRadius = 2;
    private static final int sideLength = 4;

    private static final int totalScanDistance = edgeRadius + sideLength;

    private static final int maxSidePixelVariation = 5; // 256 * .025
    private static final int minEdgeColorDifference = 13; // 256 * .05


    /*--- Detection Method ---*/

    public static BufferedImage detectEdges(BufferedImage input, BufferedImage output) {

        // Get Image Dimensions
        int imageWidth = input.getWidth();
        int imageHeight = input.getHeight();

        for (int x = totalScanDistance; x < imageWidth - totalScanDistance; x++) {
            for (int y = totalScanDistance; y < imageHeight - totalScanDistance; y++) {

                // Check Top Continuity
                boolean isTopSideContinuous = true;
                for (int yTest = y - totalScanDistance; yTest < y - (edgeRadius - 1); yTest++) {
                    int variation = new Color(input.getRGB(x, yTest)).getRed() - new Color(input.getRGB(x, yTest + 1)).getRed();
                    if (Math.abs(variation) > maxSidePixelVariation) {
                        isTopSideContinuous = false;
                    }
                }

                // Check Bottom Continuity
                boolean isBottomSideContinuous = true;
                for (int yTest = y + edgeRadius; yTest < y + (totalScanDistance - 1); yTest++) {
                    int variation = new Color(input.getRGB(x, yTest)).getRed() - new Color(input.getRGB(x, yTest + 1)).getRed();
                    if (Math.abs(variation) > maxSidePixelVariation) {
                        isBottomSideContinuous = false;
                    }
                }

                // Check Edge Color Difference
                boolean doesEdgeSeparateColors = false;
                int variation = new Color(input.getRGB(x, y - edgeRadius)).getRed() - new Color(input.getRGB(x, y + edgeRadius)).getRed();
                if (Math.abs(variation) > minEdgeColorDifference) {
                    doesEdgeSeparateColors = true;
                }

                // Write Color
                if (isTopSideContinuous && isBottomSideContinuous && doesEdgeSeparateColors) {
                    output.setRGB(x, y, new Color(255, 255, 255).getRGB());
                }
            }
        }

        return output;
    }
}
