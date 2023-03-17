package application.storage.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class CredentialsEntity {
    @Id
    private int id;

    private String username;
    private String plaintextPassword = null;
    private String hash = null;

    private String url;
    private String driver;

    private Boolean isHashed = false;

}
