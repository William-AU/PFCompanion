package application.view;

import application.utils.ASCIIUtils;
import application.utils.ConsoleUtils;
import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import org.jline.builtins.SyntaxHighlighter;
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
        System.out.println(Ansi.colorize("Highlighted option", Attribute.BLACK_TEXT(), Attribute.BRIGHT_WHITE_BACK()));
    }
}
