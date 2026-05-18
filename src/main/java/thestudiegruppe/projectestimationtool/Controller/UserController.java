package thestudiegruppe.projectestimationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Service.UserService;
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    // Modtager data fra signup-formularen
    public String signUp(@ModelAttribute User user) {

        /* @ModelAttribute tager data fra HTML-formularen
           og laver det om til et User-objekt
           Fx name, email og password bliver sat ind i user*/

        /* Vi sender user videre til service-laget
           Service håndterer logikken for at oprette brugeren*/
        userService.signUp(user);

        // Når brugeren er oprettet, sendes de videre til login-siden
        return "redirect:/login";
    }

    @PostMapping("/login")
    // Modtager data fra login-formularen
    public String login(@ModelAttribute User user, HttpSession session){

        /*@ModelAttribute tager email og password fra HTML-formularen
          og lægger dem ind i et User-objekt*/

        /* Vi bruger user.getEmail() og user.getPassword()
           til at tjekke om brugeren findes i databasen.
           Hvis brugeren findes, returnerer service-laget den fundne bruger*/
        User loggedInUser = userService.findUserForLogIn(user.getEmail(), user.getPassword());

        /* Når brugeren er logget ind, gemmer vi brugerens id i sessionen.
           Så kan systemet huske hvem brugeren er på de næste sider */
        session.setAttribute("userId", loggedInUser.getId());

        // Når login lykkes, sendes brugeren videre til dashboard
         return "redirect:/dashboard";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){

        /* invalidate() sletter hele sessionen.
           Det betyder at userId også bliver fjernet,
           og brugeren er derfor ikke længere logget ind */
        session.invalidate();
        // Efter logout sendes brugeren tilbage til login-siden
        return "redirect:/login";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
           Hvis der ikke findes et userId i sessionen,
           sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* Hvis brugeren er logget ind,
           kalder vi service-laget og sletter brugeren */
        userService.deleteUser(id);

        /* Når brugeren er slettet, sletter vi også sessionen.
           Ellers kan sessionen stadig tro at brugeren er logget ind */
        session.invalidate();

        // Brugeren sendes tilbage til login-siden
        return "redirect:/login";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute User user, HttpSession session) {

       /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* Vi sætter id på user-objektet,
           fordi id kommer fra URL'en og ikke fra formularen */
        user.setId(id);

         /* Vi sender user videre til service-laget,
           som håndterer selve opdateringen i databasen */
        userService.update(user);

        // Efter opdatering sendes brugeren tilbage til sin profilside
        return "redirect:/user/" + id;
    }

    @GetMapping("/{id}")
    // Finder og viser en bruger ud fra id
    public String findUserById(@PathVariable int id, Model model, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* Vi henter brugeren fra databasen ved hjælp af id'et fra URL'en */
        User user = userService.findUserById(id);

        /* Vi sender user videre til profile.html,
           så HTML-siden kan vise brugerens oplysninger */
        model.addAttribute("user", user);

        // Returnerer profile.html fra templates-mappen
        return "profile";
    }

}