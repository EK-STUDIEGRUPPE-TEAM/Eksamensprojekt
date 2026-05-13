package thestudiegruppe.projectestimationtool.Exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String object, int id) {
        // Kastes hvis userId eller projectId ikke bliver fundet
        // object bruges som placeholder for fx 'Bruger' eller 'Projekt'
        super(object + " med ID " + id + " blev ikke fundet.");
    }
}

