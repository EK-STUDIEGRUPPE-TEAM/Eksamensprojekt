package thestudiegruppe.projectestimationtool.Exception;

public class NegativeValueException extends RuntimeException {
    public NegativeValueException(String value) {
        // Kastes når værdien ikke må være mindre end 0
        // value bruges som placeholder for fx 'Timer' eller 'Timepris'
        super(value + " må ikke være mindre end 0.");
    }
}
