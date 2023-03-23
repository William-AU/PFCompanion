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

    @Bean
    public Attribute highlightTextColor() {
        return Attribute.BLACK_TEXT();
    }

    @Bean
    public Attribute highlightTextBackground() {
        return Attribute.BRIGHT_WHITE_BACK();
    }
}
