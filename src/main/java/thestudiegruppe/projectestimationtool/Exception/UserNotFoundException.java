package thestudiegruppe.projectestimationtool.Exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int id) {
        super("Bruger med ID " + id + " blev ikke fundet");
    }
}

