package application.common;

import application.utils.ASCIIUtils;

public class Constants {

    public static final ASCIIUtils.ASCIIArtSize ART_SIZE = ASCIIUtils.ASCIIArtSize.ART_SIZE_MEDIUM;
    public static int getColumns() {
        return switch (ART_SIZE) {
            case ART_SIZE_MEDIUM -> 105;
            default -> -1;
        };
    }
}
