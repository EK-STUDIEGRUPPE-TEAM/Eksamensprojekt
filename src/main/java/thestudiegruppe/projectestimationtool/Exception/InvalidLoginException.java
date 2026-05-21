package thestudiegruppe.projectestimationtool.Exception;

public class InvalidLoginException extends RuntimeException {

    // Kastes hvis login fejler pga forkert adgangskode
    public InvalidLoginException(){
        super("Forkert Email eller adgangskode.");
    }
}



