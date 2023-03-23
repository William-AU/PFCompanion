package application.config;

import application.view.TitleView;
import application.view.View;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootConfiguration
public class SpringConfig {

    @Value("${SPRING_DATASOURCE_URL:#{null}}")
    private String url;

    @Value("${SPRING_DATASOURCE_USERNAME:#{null}}")
    private String username;

    @Value("${SPRING_DATASOURCE_PASSWORD:#{null}}")
    private String password;

    @Value("${SPRING_DATASOURCE_DRIVER_CLASS_NAME:com.mysql.cj.jdbc.Driver}")
    private String driverClassName;

    /**
     * Determines the {@link javax.sql.CommonDataSource} to use for this instance, method is run once during bean creation
     * and will ask the user for a database credential. If no database is given, will instead default to an H2 database
     * stored in the working directory
     * @throws IOException If the application does not have access to the console it is running in
     */
    @Bean
    public DataSource getDataSource() throws IOException {
        if (url == null || username == null || password == null) {
            return askForDataSource();
        }
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    private DataSource askForDataSource() throws IOException {

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("No data source was found, would you like to configure one manually? (y/N)");
        String response = bufferedReader.readLine();
        if (response.equalsIgnoreCase("n") || response.isBlank()) {
            return configureNoDataSource();
        }
        if (!response.equalsIgnoreCase("y")) {
            // We try again lol
            return askForDataSource();
        }
        System.out.println("Enter database URL");
        String url = bufferedReader.readLine();
        System.out.println("Enter driver class name. Press enter to continue with default driver class (com.mysql.cj.jdbc.Driver)");
        String driver = bufferedReader.readLine();
        System.out.println("Enter database username");
        String username = bufferedReader.readLine();
        System.out.println("Enter password");
        Console console = System.console();
        if (console == null) {
            System.out.println("Could not get Console instance for password validation");
            System.exit(0);
        }
        char[] passwordChar = console.readPassword("Password: ");
        String password = new String(passwordChar);

        if (driver.isBlank()) {
            driver = driverClassName;
        }

        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    private DataSource configureNoDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:h2:file:./db");
        dataSourceBuilder.username("sa");
        return dataSourceBuilder.build();
    }

    @Bean
    public View defaultView(BuildProperties buildProperties) {
        return new TitleView(buildProperties);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Terminal terminal() throws IOException {
        Terminal terminal = TerminalBuilder.builder().system(true).jansi(true).build();
        terminal.enterRawMode();
        return terminal;
    }

    @Bean
    public LineReader lineReader(Terminal terminal) throws IOException {
        return LineReaderBuilder.builder().terminal(terminal).build();
    }

}