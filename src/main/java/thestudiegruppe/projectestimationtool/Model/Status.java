package thestudiegruppe.projectestimationtool.Model;

public enum Status {

    TODO("Todo"), IN_PROGRESS("In Progress"), DONE("Done");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

//    public static Status fromDisplayName(String text){
//     for (Status status : Status.values()){
//         if (status.displayName.equalsIgnoreCase(text)){
//             return status;
//         }
//     }
//     throw new IllegalArgumentException("Unknown status: " + text);
//    }
}
