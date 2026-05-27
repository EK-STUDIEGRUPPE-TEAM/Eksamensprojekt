package thestudiegruppe.projectestimationtool.sessions;

import jakarta.servlet.http.HttpSession;

public class SessionHelper {

    private SessionHelper(){
    }


    //Tjekker om brugeren er logget ind.
    public static boolean isLoggedIn(HttpSession session){

         /* Hvis sessionen indeholder et userId,
           betyder det at brugeren er logget ind */
        return session.getAttribute("userId") != null;
    }

    //Henter userId fra sessionen
    public static Integer getLoggedInUserId(HttpSession session){

        /* Vi henter userId fra sessionen.
           Det kan senere bruges til at koble data til den bruger,
           der er logget ind */
        return (Integer) session.getAttribute("userId");
    }

    /* Vi bruger SessionHelper for at undgå gentagelse af session-kode
       i flere controllers.*/
}
