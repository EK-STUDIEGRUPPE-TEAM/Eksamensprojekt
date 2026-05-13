package thestudiegruppe.projectestimationtool.Exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        // Kastes hvis email allerede findes
        super("Email " + email + " er allerede i brug.");
    }
}
