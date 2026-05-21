package thestudiegruppe.projectestimationtool.Exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Catcher vores custom NotFoundException når et objekts ID ikke kan findes
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException exception, Model model) {
        // Vi kan bruges model objektet message i vores html error template til at få fejlbeskeden
        model.addAttribute("message", exception.getMessage());
        return "error";
    }

    // Catcher vores custom EmailAlreadyExistsException når en brugers email allerede eksisterer i databasen under oprettelse
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailAlreadyExistsException(EmailAlreadyExistsException exception, Model model){
        // Vi kan bruges model objektet message i vores html error template til at få fejlbeskeden
        model.addAttribute("message", exception.getMessage());
        return "error";
    }

    // Catcher vores custom InvalidLoginException når en bruger prøver at logge ind med forkert email eller adgangskode
    @ExceptionHandler(InvalidLoginException.class)
    public String handleInvalidLoginException(InvalidLoginException exception, Model model){
        // Vi kan bruges model objektet "message" i vores html error template til at få fejlbeskeden
        model.addAttribute("message", exception.getMessage());
        return "error";
    }

    // Catcher vores custom NegativeValueException når en delopgave prøver at hente negative timer
    @ExceptionHandler(NegativeValueException.class)
    public String handleNegativeValueException(NegativeValueException exception, Model model){
        // Vi kan bruges model objektet "message" i vores html error template til at få fejlbeskeden
        model.addAttribute("message", exception.getMessage());
        return "error";
    }

    // Catcher alle generic exceptions som ikke bliver fanget af vores custom exceptions
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Model model) {
        model.addAttribute("message", "Noget gik galt");
        return "error";
    }

}
