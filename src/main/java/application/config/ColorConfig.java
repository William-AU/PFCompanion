package application.config;

import com.diogonunes.jcolor.Attribute;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for various text colors
 * For now only has hard coded values for highlighting
 */
@Configuration
public class ColorConfig {
    public record ColorContext(Attribute text, Attribute background) {};

    @Bean
    public ColorContext colorContext() {
        return new ColorContext(Attribute.BLACK_TEXT(), Attribute.BRIGHT_WHITE_BACK());
    }
}
