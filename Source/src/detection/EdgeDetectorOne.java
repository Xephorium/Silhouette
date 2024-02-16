package detection;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EdgeDetectorOne {


    /*--- Variable Declarations ---*/

    private static final int edgeRadius = 2;
    private static final int sideLength = 4;

    private static final int totalScanDistance = edgeRadius + sideLength;

    private static final int maxSidePixelVariation = 10;
    private static final int maxDiagPixelVariation = 7;
    private static final int maxSideOverallVariation = 3;
    private static final int minSideColorDifference = 4;
    private static final int minDiagColorDifference = 30;


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

                // Check Top Overall Change
                boolean isTopSideUnchanging = true;
                int diff = new Color(input.getRGB(x, y - totalScanDistance)).getRed() - new Color(input.getRGB(x, y - edgeRadius - 1)).getRed();
                if (Math.abs(diff) > maxSideOverallVariation) {
                    isTopSideUnchanging = false;
                }

                // Check Bottom Continuity
                boolean isBottomSideContinuous = true;
                for (int yTest = y + edgeRadius; yTest < y + (totalScanDistance - 1); yTest++) {
                    int variation = new Color(input.getRGB(x, yTest)).getRed() - new Color(input.getRGB(x, yTest + 1)).getRed();
                    if (Math.abs(variation) > maxSidePixelVariation) {
                        isBottomSideContinuous = false;
                    }
                }

                // Check Bottom Overall Change
                boolean isBottomSideUnchanging = true;
                diff = new Color(input.getRGB(x, y + edgeRadius + 1)).getRed() - new Color(input.getRGB(x, y + totalScanDistance)).getRed();
                if (Math.abs(diff) > maxSideOverallVariation) {
                    isBottomSideUnchanging = false;
                }

                // Check Vertical Edge Color Difference
                boolean doesVerticalEdgeSeparateColors = false;
                int variation = new Color(input.getRGB(x, y - edgeRadius)).getRed() - new Color(input.getRGB(x, y + edgeRadius)).getRed();
                if (Math.abs(variation) > minSideColorDifference) {
                    doesVerticalEdgeSeparateColors = true;
                }

                boolean isVerticalEdge = isTopSideContinuous && isTopSideUnchanging &&
                                isBottomSideContinuous && isBottomSideUnchanging &&
                                doesVerticalEdgeSeparateColors;


                /*--- Horizontal Edge Check ---*/

                // Check Left Continuity
                boolean isLeftSideContinuous = true;
                for (int xTest = x - totalScanDistance; xTest < x - (edgeRadius - 1); xTest++) {
                    variation = new Color(input.getRGB(xTest, y)).getRed() - new Color(input.getRGB(xTest + 1, y)).getRed();
                    if (Math.abs(variation) > maxSidePixelVariation) {
                        isLeftSideContinuous = false;
                    }
                }

                // Check Left Overall Change
                boolean isLeftSideUnchanging = true;
                diff = new Color(input.getRGB(x - totalScanDistance, y)).getRed() - new Color(input.getRGB(x - (edgeRadius - 1), y)).getRed();
                if (Math.abs(diff) > maxSideOverallVariation) {
                    isLeftSideUnchanging = false;
                }

                // Check Right Continuity
                boolean isRightSideContinuous = true;
                for (int xTest = x + edgeRadius; xTest < x + (totalScanDistance - 1); xTest++) {
                    variation = new Color(input.getRGB(xTest, y)).getRed() - new Color(input.getRGB(xTest + 1, y)).getRed();
                    if (Math.abs(variation) > maxSidePixelVariation) {
                        isRightSideContinuous = false;
                    }
                }

                // Check Right Overall Change
                boolean isRightSideUnchanging = true;
                diff = new Color(input.getRGB(x + edgeRadius + 1, y)).getRed() - new Color(input.getRGB(x + totalScanDistance, y)).getRed();
                if (Math.abs(diff) > maxSideOverallVariation) {
                    isRightSideUnchanging = false;
                }

                // Check Horizontal Edge Color Difference
                boolean doesHorizontalEdgeSeparateColors = false;
                variation = new Color(input.getRGB(x - edgeRadius, y)).getRed() - new Color(input.getRGB(x + edgeRadius, y)).getRed();
                if (Math.abs(variation) > minSideColorDifference) {
                    doesHorizontalEdgeSeparateColors = true;
                }

                boolean isHorizontalEdge = isLeftSideContinuous && isLeftSideUnchanging &&
                        isRightSideContinuous && isRightSideUnchanging &&
                        doesHorizontalEdgeSeparateColors;


                /*--- Forwardslash Edge Check ---*/

                boolean isForwardslashBottomSideContinuous = true;
                boolean isForwardslashTopSideContinuous = true;

                for (int offset = edgeRadius; offset < totalScanDistance - 1; offset++) {
                    int xDownCoord = x - offset;
                    int yDownCoord = y + offset;
                    int xUpCoord = x + offset;
                    int yUpCoord = y - offset;

                    // Check Bottom Left
                    variation = new Color(input.getRGB(xDownCoord, yDownCoord)).getRed()
                            - new Color(input.getRGB(xDownCoord - 1, yDownCoord + 1)).getRed();
                    if (Math.abs(variation) > maxDiagPixelVariation) {
                        isForwardslashBottomSideContinuous = false;
                    }

                    // Check Top Right
                    variation = new Color(input.getRGB(xUpCoord, yUpCoord)).getRed()
                            - new Color(input.getRGB(xUpCoord + 1, yUpCoord - 1)).getRed();
                    if (Math.abs(variation) > maxDiagPixelVariation) {
                        isForwardslashTopSideContinuous = false;
                    }
                }

                // Check Forwardslash Edge Color Difference
                boolean doesForwardslashEdgeSeparateColors = false;
                variation = new Color(input.getRGB(x - (edgeRadius - 1), y + (edgeRadius - 1))).getRed()
                        - new Color(input.getRGB(x + (edgeRadius - 1), y - (edgeRadius - 1))).getRed();
                if (Math.abs(variation) > minDiagColorDifference) {
                    doesForwardslashEdgeSeparateColors = true;
                }

                boolean isForwardslashEdge = isForwardslashTopSideContinuous && isForwardslashBottomSideContinuous && doesForwardslashEdgeSeparateColors;


                /*--- Backslash Edge Check ---*/

                boolean isBackslashBottomSideContinuous = true;
                boolean isBackslashTopSideContinuous = true;

                for (int offset = edgeRadius; offset < totalScanDistance - 1; offset++) {
                    int xDownCoord = x + offset;
                    int yDownCoord = y + offset;
                    int xUpCoord = x - offset;
                    int yUpCoord = y - offset;

                    // Check Bottom Right
                    variation = new Color(input.getRGB(xDownCoord, yDownCoord)).getRed()
                            - new Color(input.getRGB(xDownCoord + 1, yDownCoord + 1)).getRed();
                    if (Math.abs(variation) > maxDiagPixelVariation) {
                        isBackslashBottomSideContinuous = false;
                    }

                    // Check Top Left
                    variation = new Color(input.getRGB(xUpCoord, yUpCoord)).getRed()
                            - new Color(input.getRGB(xUpCoord - 1, yUpCoord - 1)).getRed();
                    if (Math.abs(variation) > maxDiagPixelVariation) {
                        isBackslashTopSideContinuous = false;
                    }
                }

                // Check Backslash Edge Color Difference
                boolean doesBackslashEdgeSeparateColors = false;
                variation = new Color(input.getRGB(x + (edgeRadius - 1), y + (edgeRadius - 1))).getRed()
                        - new Color(input.getRGB(x - (edgeRadius - 1), y - (edgeRadius - 1))).getRed();
                if (Math.abs(variation) > minDiagColorDifference) {
                    doesBackslashEdgeSeparateColors = true;
                }

                boolean isBackslashEdge = isBackslashTopSideContinuous && isBackslashBottomSideContinuous && doesBackslashEdgeSeparateColors;


                /*--- Final Color Write ---*/

                // Write Color
                if (isVerticalEdge || isHorizontalEdge || isForwardslashEdge || isBackslashEdge) {
                    output.setRGB(x, y, new Color(255, 255, 255).getRGB());
                }
            }
        }

        return output;
    }
}
