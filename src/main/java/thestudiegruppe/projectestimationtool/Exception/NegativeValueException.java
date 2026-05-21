package thestudiegruppe.projectestimationtool.Exception;

public class NegativeValueException extends RuntimeException {
    public NegativeValueException(String value) {
        // Kastes når værdien ikke må være mindre end 1
        // value bruges som placeholder for fx 'Timer' eller 'Timepris'
        super(value + " skal være større end 0");
    }
}
