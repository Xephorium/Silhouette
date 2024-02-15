package detection;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EdgeDetectorOne {


    /*--- Variable Declarations ---*/

    private static final int edgeRadius = 2;
    private static final int sideLength = 10;

    private static final int totalScanDistance = edgeRadius + sideLength;

    private static final int maxSidePixelVariation = 10; // 256 * .025
    private static final int minEdgeColorDifference = 6; // 256 * .05


    /*--- Detection Method ---*/

    public static BufferedImage detectEdges(BufferedImage input, BufferedImage output) {

        // Get Image Dimensions
        int imageWidth = input.getWidth();
        int imageHeight = input.getHeight();

        for (int x = totalScanDistance; x < imageWidth - totalScanDistance; x++) {
            for (int y = totalScanDistance; y < imageHeight - totalScanDistance; y++) {


                /*--- Vertical Edge Check ---*/

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

                // Check Vertical Edge Color Difference
                boolean doesVerticalEdgeSeparateColors = false;
                int variation = new Color(input.getRGB(x, y - edgeRadius)).getRed() - new Color(input.getRGB(x, y + edgeRadius)).getRed();
                if (Math.abs(variation) > minEdgeColorDifference) {
                    doesVerticalEdgeSeparateColors = true;
                }

                boolean isVerticalEdge = isTopSideContinuous && isBottomSideContinuous && doesVerticalEdgeSeparateColors;


                /*--- Horizontal Edge Check ---*/

                // Check Left Continuity
                boolean isLeftSideContinuous = true;
                for (int xTest = x - totalScanDistance; xTest < x - (edgeRadius - 1); xTest++) {
                    variation = new Color(input.getRGB(xTest, y)).getRed() - new Color(input.getRGB(xTest + 1, y)).getRed();
                    if (Math.abs(variation) > maxSidePixelVariation) {
                        isLeftSideContinuous = false;
                    }
                }

                // Check Right Continuity
                boolean isRightSideContinuous = true;
                for (int xTest = x + edgeRadius; xTest < x + (totalScanDistance - 1); xTest++) {
                    variation = new Color(input.getRGB(xTest, y)).getRed() - new Color(input.getRGB(xTest + 1, y)).getRed();
                    if (Math.abs(variation) > maxSidePixelVariation) {
                        isRightSideContinuous = false;
                    }
                }

                // Check Horizontal Edge Color Difference
                boolean doesHorizontalEdgeSeparateColors = false;
                variation = new Color(input.getRGB(x - edgeRadius, y)).getRed() - new Color(input.getRGB(x + edgeRadius, y)).getRed();
                if (Math.abs(variation) > minEdgeColorDifference) {
                    doesHorizontalEdgeSeparateColors = true;
                }

                boolean isHorizontalEdge = isLeftSideContinuous && isRightSideContinuous && doesHorizontalEdgeSeparateColors;


                /*--- Backslash Edge Check ---*/

                // Check Backslash Bottom Continuity
                boolean isBackslashBottomSideContinuous = true;
                for (int xOffset = 0 - edgeRadius - 1; xOffset > 0 - totalScanDistance; xOffset--) {
                    int xCoord = x + xOffset;
                    int yCoord = y + (Math.abs(xOffset) - 1);
                    variation = new Color(input.getRGB(xCoord, yCoord)).getRed()
                            - new Color(input.getRGB(xCoord - 1, yCoord + 1)).getRed();
                    if (Math.abs(variation) > maxSidePixelVariation) {
                        isBackslashBottomSideContinuous = false;
                    }
                }

                // Check Backslash Top Continuity
                boolean isBackslashTopSideContinuous = true;
                for (int xOffset = edgeRadius; xOffset < totalScanDistance - 1; xOffset++) {
                    int xCoord = x + xOffset;
                    int yCoord = y - (Math.abs(xOffset) + 1);
                    variation = new Color(input.getRGB(xCoord, yCoord)).getRed()
                            - new Color(input.getRGB(xCoord + 1, yCoord - 1)).getRed();
                    if (Math.abs(variation) > maxSidePixelVariation) {
                        isBackslashTopSideContinuous = false;
                    }
                }

                // Check Backslash Edge Color Difference
                boolean doesBackslashEdgeSeparateColors = false;
                variation = new Color(input.getRGB(x - edgeRadius, y + (edgeRadius - 1))).getRed()
                        - new Color(input.getRGB(x - (edgeRadius - 1), y + edgeRadius)).getRed();
                if (Math.abs(variation) > minEdgeColorDifference) {
                    doesBackslashEdgeSeparateColors = true;
                }

                boolean isBackslashEdge = isBackslashTopSideContinuous && isBackslashBottomSideContinuous && doesBackslashEdgeSeparateColors;


                // Write Color
                if (isVerticalEdge || isHorizontalEdge) {
                    output.setRGB(x, y, new Color(255, 255, 255).getRGB());
                }

                if (isBackslashEdge) {
                    output.setRGB(x - 2, y + 1, new Color(255, 255, 255).getRGB());
                }
            }
        }

        return output;
    }
}
