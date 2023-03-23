package application.services;

import application.config.ColorConfig;
import com.diogonunes.jcolor.Ansi;
import org.springframework.stereotype.Service;

@Service
public class ColorService {
    private final ColorConfig.ColorContext colorContext;

    public ColorService(ColorConfig.ColorContext colorContext) {
        this.colorContext = colorContext;
    }

    /**
     * Functions like {@link java.io.PrintStream#print(String)} except uses formatting as specified in {@link ColorConfig}
     * @param toPrint String to print using given highlighting
     * @param highlight Flag for using normal text formatting or highlighting
     */
    public void print(String toPrint, boolean highlight) {
        if (!highlight) {
            System.out.print(toPrint);
            return;
        }
        System.out.print(Ansi.colorize(toPrint, colorContext.text(), colorContext.background()));
    }

    /**
     * Functions like {@link java.io.PrintStream#print(String)} except uses formatting as specified in {@link ColorConfig}
     * @param toPrint String to print using given highlighting
     * @param highlight Flag for using normal text formatting or highlighting
     */
    public void printLn(String toPrint, boolean highlight) {
        if (!highlight) {
            System.out.println(toPrint);
            return;
        }
        System.out.println(Ansi.colorize(toPrint, colorContext.text(), colorContext.background()));
    }
}
