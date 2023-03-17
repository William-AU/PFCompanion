package application.storage.services;

import application.model.exceptions.InvalidPasswordException;
import application.model.exceptions.UsernameDoesNotExistException;
import application.storage.entities.CredentialsEntity;
import application.storage.repositories.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.swing.text.html.parser.Entity;

/**
 * Service for handling the storage and retrieval of database credentials.
 * Responsible for either storing passwords as plaintext for automatic log-on, or hashing using BCrypt for some resemblance of security
 */
@Service
public class CredentialsService {
    private final CredentialsRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public CredentialsService(CredentialsRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    /**
     * Checks to see if an instance of the username is stored in the database
     * @param username Username to look up
     * @return True if the username is found, false otherwise
     */
    public boolean usernameExists(String username) {
        return repository.existsByUsername(username);
    }

    /**
     * Checks to see if a password for a user is hashed or not
     * @param username Username to check
     * @return True if the username has an associated hash, false otherwise.
     * @throws UsernameDoesNotExistException if the username is not found in the database
     */
    public boolean isHashed(String username) throws UsernameDoesNotExistException {
        checkName(username);
        CredentialsEntity entity = repository.getByUsername(username);
        return entity.getIsHashed();
    }


    /**
     * Retrieves the {@link javax.sql.DataSource} for a hashed password login, requires the correct password and authenticates using {@link org.springframework.security.crypto.bcrypt}
     * @param username Username for the database
     * @param password Password for the database
     * @return {@link javax.sql.DataSource} for the given database if the password authenticates, null otherwise
     * @throws UsernameDoesNotExistException if the username is not found in the database
     * @throws InvalidPasswordException if the given password fails to validate the hashed password
     */
    public DataSource getDataSourceFromHashedPassword(String username, String password) throws UsernameDoesNotExistException, InvalidPasswordException {
        checkName(username);
        CredentialsEntity entity = repository.getByUsername(username);
        if (!entity.getIsHashed()) {
            return getFromNoPassword(username);
        }
        String hash = entity.getHash();
        boolean invalid = !validatePassword(password, hash);
        if (invalid) throw new InvalidPasswordException("Invalid password for username: " + username);
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(entity.getUrl());
        dataSourceBuilder.username(entity.getUsername());
        dataSourceBuilder.password(password);
        dataSourceBuilder.driverClassName(entity.getDriver());
        return dataSourceBuilder.build();
    }

    /**
     * Retrieves the {@link javax.sql.DataSource} for an automatic login, no password is required since it will be stored in plaintext anyway
     * @param username {@link javax.sql.DataSource} username
     * @return The {@link javax.sql.DataSource} needed to connect to the database
     * @throws UsernameDoesNotExistException if the username is not found in the database
     * @throws InvalidPasswordException if the stored credentials are hashed
     */
    public DataSource getFromNoPassword(String username) throws UsernameDoesNotExistException, InvalidPasswordException {
        checkName(username);
        CredentialsEntity entity = repository.getByUsername(username);
        if (entity.getIsHashed()) throw new InvalidPasswordException("User: " + username + " is hashed but tried to get password as plaintext");
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(entity.getUrl());
        dataSourceBuilder.username(entity.getUsername());
        dataSourceBuilder.password(entity.getPlaintextPassword());
        dataSourceBuilder.driverClassName(entity.getDriver());
        return dataSourceBuilder.build();
    }

    /**
     * Stores credentials needed to access a database, storing any password in plaintext for use as an auto login feature
     * @param url Database URL
     * @param username Username for the database
     * @param password Password for the database
     * @param driver Datasource driver required to connect to the database
     */
    public void storeCredentialsPlaintext(String url, String username, String password, String driver) {
        CredentialsEntity entity = new CredentialsEntity();
        entity.setUrl(url);
        entity.setUsername(username);
        entity.setPlaintextPassword(password);
        entity.setIsHashed(false);
        entity.setDriver(driver);
        repository.save(entity);
    }

    /**
     * Stores the credentials needed to access a database using hashing through {@link org.springframework.security.crypto.bcrypt}.
     * @param url Database URL
     * @param username Username for the database login
     * @param password Password for the database login
     * @param driver DataSource driver required to connect to the database
     */
    public void storeCredentialsHashed(String url, String username, String password, String driver) {
        CredentialsEntity entity = new CredentialsEntity();
        entity.setUrl(url);
        entity.setUsername(username);
        entity.setHash(hashPassword(password));
        entity.setIsHashed(true);
        entity.setDriver(driver);
        repository.save(entity);
    }

    /**
     * Internal method for hashing passwords, note that hashing uses BCrypt and salt is generated within the encryption function itself and appended to the hash, thus this should only be called once on registration, otherwise use {@link application.storage.services.CredentialsService#validatePassword(String, String)}
     * @param password The password to be hashed
     * @return The hash for the given password using {@link org.springframework.security.crypto.bcrypt}
     */
    private String hashPassword(String password) {
        return encoder.encode(password);
    }

    /**
     * Verifies the given password against the hash, required since {@link org.springframework.security.crypto.bcrypt} uses internal salt.
     * @param password The password to check
     * @param hash The hash stored in the database on registration
     * @return True if the password matches the hash, false otherwise
     */
    private boolean validatePassword(String password, String hash) {
        return encoder.matches(password, hash);
    }

    private void checkName(String username) throws UsernameDoesNotExistException {
        if (usernameExists(username)) return;
        throw new UsernameDoesNotExistException("A user by the name: " + username + " could not be found in the database");
    }
}
