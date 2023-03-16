package application.view;

import application.utils.ASCIIUtils;
import application.utils.ConsoleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;

public class TitleView implements View {
    @Autowired
    private BuildProperties buildProperties;

    @Override
    public void draw() {
        ConsoleUtils.clearConsole();
        // Small size breaks on mac for some reason?
        ASCIIUtils.print("Character Manager", ASCIIUtils.ASCIIArtSize.ART_SIZE_SMALL, ASCIIUtils.ASCIIArtFont.ART_FONT_DIALOG_INPUT, "█");
        // TODO: Make buildProperties actually work (most likely some IDE tomfoolery going on
        //System.out.println(buildProperties.getVersion());
    }
}
