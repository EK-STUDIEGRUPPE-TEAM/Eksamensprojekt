package thestudiegruppe.projectestimationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Exception.InvalidLoginException;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Service.UserService;
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute User user) {

        userService.signUp(user);

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model){

        try {

            User loggedInUser = userService.findUserForLogIn(user.getEmail(), user.getPassword());

        /* Når brugeren er logget ind, gemmer vi brugerens id i sessionen.
           Så kan systemet huske hvem brugeren er på de næste sider */
            session.setAttribute("userId", loggedInUser.getId());

            // Når login lykkes, sendes brugeren videre til dashboard
            return "redirect:/dashboard";

        } catch (InvalidLoginException e){
            // Hvis login er forkert displayer vi vores custom exception på siden
            model.addAttribute("error", e.getMessage());
            //Vi adder user til modellen da vi bypasser
            model.addAttribute("user", new User());
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){

        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        userService.deleteUser(id);

        session.invalidate();

        return "redirect:/signup";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute User user, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        user.setId(id);

        userService.update(user);

        return "redirect:/dashboard";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session){

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        Integer userId = SessionHelper.getLoggedInUserId(session);

        User user = userService.findUserById(userId);

        model.addAttribute("user", user);

        return "profile";
    }

}