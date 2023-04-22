package application.services.storageServices;
import lombok.Data;
import lombok.Getter;

@Getter
public class StorageServiceContext {
    private final CredentialsService credentialsService;

    public StorageServiceContext(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }
}
