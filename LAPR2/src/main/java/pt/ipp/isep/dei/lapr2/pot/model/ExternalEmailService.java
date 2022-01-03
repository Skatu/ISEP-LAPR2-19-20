package pt.ipp.isep.dei.lapr2.pot.model;

/**
 * Contact point between the application and the external e-mail service.
 * This interface is implemented any any adaptor class that will process the
 * communication between the application and the external e-mail service.
 */
public interface ExternalEmailService {
    /**
     * Method responsible for sending the login credentials of the registered user.
     * @param email The e-mail to send the credentials to.
     * @param password The password to be sent.
     * @return If the sending was successful.
     */
    boolean sendPassword(String email, String password);

    boolean sendWarningEmail(String email);
}
