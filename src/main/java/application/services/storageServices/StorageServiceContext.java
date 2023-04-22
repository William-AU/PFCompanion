package application.services.storageServices;


public class StorageServiceContext {
    private final CredentialsService credentialsService;

    public StorageServiceContext(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }
}
