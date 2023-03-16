package application.view;

import application.utils.ASCIIUtils;
import application.utils.ConsoleUtils;
import org.springframework.boot.info.BuildProperties;

public class TitleView implements View {
    private BuildProperties buildProperties;
    public TitleView(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
        System.out.println("NO ARGS CONSTRUCTOR");
    }

    @Override
    public void draw() {
        // Small size breaks on mac for some reason?
        ASCIIUtils.print("Companion", ASCIIUtils.ASCIIArtSize.ART_SIZE_SMALL, ASCIIUtils.ASCIIArtFont.ART_FONT_DIALOG_INPUT, "█");
        System.out.println(buildProperties.getVersion());
    }
}
