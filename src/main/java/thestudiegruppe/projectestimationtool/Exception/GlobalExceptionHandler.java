package thestudiegruppe.projectestimationtool.Exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException exception, Model model) {

        model.addAttribute("message", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailAlreadyExistsException(EmailAlreadyExistsException exception, Model model){

        model.addAttribute("message", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(InvalidLoginException.class)
    public String handleInvalidLoginException(InvalidLoginException exception, Model model){

        model.addAttribute("message", exception.getMessage());
        return "error";
    }


    @ExceptionHandler(NegativeValueException.class)
    public String handleNegativeValueException(NegativeValueException exception, Model model){

        model.addAttribute("message", exception.getMessage());
        return "error";
    }


    @ExceptionHandler(Exception.class)
    public String handleGenericException(Model model) {

        model.addAttribute("message", "Der opstod en uventet fejl");
        return "error";
    }

}
