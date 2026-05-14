package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Service.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Tror ikke vi har brug for en showallusers ?? men tør ikke slette endnu
//    @GetMapping
//    public String showAllUsers(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//        return "user";
//    }

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
    public String logIn(@ModelAttribute User user){

        /*@ModelAttribute tager email og password fra HTML-formularen
          og lægger dem ind i et User-objekt*/

        /* Vi bruger user.getEmail() og user.getPassword()
           til at tjekke om brugeren findes i databasen*/
        userService.findUserForLogIn(user.getEmail(), user.getPassword());

        /* Midlertidig redirect til signup for at teste at login virker
           Senere kan den ændres til fx dashboard eller profilside*/
         return "redirect:/signup";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/login";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute User user) {
        user.setId(id);
        userService.update(user);
        return "redirect:/user/" + id;
    }

    @GetMapping("/user/{id}")
    public String findUserById(@PathVariable int id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "profile";
    }
}