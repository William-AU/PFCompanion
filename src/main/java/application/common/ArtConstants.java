package application.common;

import application.utils.ASCIIUtils;

public class ArtConstants {

    public static final ASCIIUtils.ASCIIArtSize ART_SIZE = ASCIIUtils.ASCIIArtSize.ART_SIZE_MEDIUM;
    public static int getColumns() {
        return switch (ART_SIZE) {
            case ART_SIZE_MEDIUM -> 105;
            default -> -1;
        };
    }

    public static int getArtHeight() {
        return switch (ART_SIZE) {
            case ART_SIZE_MEDIUM -> 13;
            default -> -1;
        };
    }
}
