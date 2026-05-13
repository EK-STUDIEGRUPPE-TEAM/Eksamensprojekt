package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Exception.NegativeValueException;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final SubTaskService subTaskService;

    public TaskService(TaskRepository taskRepository, SubTaskService subTaskService) {
        this.taskRepository = taskRepository;
        this.subTaskService = subTaskService;
    }

    //Opretter en ny tasj i databasen.
    public void createTask(Task task) {
        //Tjekker om task er null.
        //Hvis task er null, kaste en exception,
        //fordi metoden ikke kan oprette en tom task.
        if (task == null) {
            throw new IllegalArgumentException("Task må ikke være null");
        }
        //Sender task videre til repository, som gemmer den i databasen.
        taskRepository.addTask(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    //Henter alle tasks, der hører til et bestemt subProject.
    public List<Task> getTasksBySubProjectId(int subProjectId) {
        //Tjekker om subProjectId er ugyldigt.
        //Hvis id er 0 eller mindre, kastes en exception,
        //fordi der skal bruges et gyldigt subProject-id.
        if (subProjectId <= 0) {
            throw new IllegalArgumentException("SubProject id skal være større end 0");
        }

        //Returnerer alle tasks for det valgte subProject.
        return taskRepository.getTasksBySubProjectId(subProjectId);
    }

    public void deleteTask(int id) {
        taskRepository.deleteTask(id);
    }

    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    //Sletter alle tasks, der hører til et bestemt subProject.
    public int deleteTaskBySubProjectId(int subProjectId) {
        //Tjekker om subProjectId er ugyldigt.
        //Hvis id er 0 eller mindre, kastes en exception,
        //fordi der skal bruges et gyldigt subProject-id.
        if (subProjectId <= 0) {
            throw new IllegalArgumentException("SubProject id skal være større end 0");
        }

        //Sletter tasks i repository og returnerer resultatet.
        return taskRepository.deleteTaskBySubProjectId(subProjectId);
    }

    //Beregner den samlede pris for alle tasks i et bestemt subProject.
    public double calculateTaskPrice(int subProjectId) {
        //Tjekker om subProjectId er ugyldigt.
        //Hvis id er 0 eller mindre, kastes en exception,
        //fordi der skal bruges et gyldigt subProject-id.
        if (subProjectId <= 0) {
            throw new IllegalArgumentException("SubProject id skal være større end 0");
        }

        //Variabel til at gemme den samlede pris.
        double totalPrice = 0;

        //Henter alle tasks, der hører til det valgte subProject.
        List<Task> tasks = taskRepository.getTasksBySubProjectId(subProjectId);

        //Går igennem hver task i listen.
        for (Task task : tasks) {

            //Tjekker om taskens timeløn er ugyldig.
            //Hvis hourlyRate er mindre end 0, kaster vi en custom NegativeValueException
            //fordi en negativ timepris ikke giver mening i prisberegning.
            if (task.getHourlyRate() < 0) {
                throw new NegativeValueException("Timepris");
            }

            //Lægger taskens pris til den samlede pris.
            //Prisen beregnes som hourlyRate * estimerede timer for tasken.
            totalPrice += task.getHourlyRate() * subTaskService.calculateEstimatedHours(task.getId());
        }

        //Returnerer den samlede beregnede pris.
        return totalPrice;
    }
}
