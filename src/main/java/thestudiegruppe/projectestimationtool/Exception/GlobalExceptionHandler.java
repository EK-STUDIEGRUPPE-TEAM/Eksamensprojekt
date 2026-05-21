package thestudiegruppe.projectestimationtool.Exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Catcher vores custom NotFoundException når en bruger eller et projekt ikke kan findes på id'et
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
}
