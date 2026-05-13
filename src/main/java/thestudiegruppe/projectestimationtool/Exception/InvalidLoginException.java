package thestudiegruppe.projectestimationtool.Exception;

public class InvalidLoginException extends RuntimeException {

    // Kastes hvis login fejler pga Email ikke eksisterer
    // Bruger  email  som parameter
    public InvalidLoginException(String email) {
        super("Email " + email + " blev ikke fundet.");
    }

    // Kastes hvis login fejler pga forkert adgangskode
    // Overloader ved brug af ingen parameter
    public InvalidLoginException(){
        super("Forkert adgangskode.");
    }
}



