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

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute User user) {
        userService.signUp(user);
        return "redirect:/login";
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

    @GetMapping("/{id}")
    public String findUserById(@PathVariable int id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "profile";
    }
}