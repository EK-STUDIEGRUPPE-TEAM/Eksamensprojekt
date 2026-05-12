package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String show(Model model){
        return null;
    }

    @PostMapping("/add")
    public User createUser(User user, Model model){

        userService.createUser(user);

        return user;
    }

    @PostMapping("/delete")
    public String delete(int id){
        return null;
    }

    @PostMapping("/save")
    public String save(User user){
        return null;
    }

    @PostMapping("/update")
    public String update(User user){
        return null;
    }

}
