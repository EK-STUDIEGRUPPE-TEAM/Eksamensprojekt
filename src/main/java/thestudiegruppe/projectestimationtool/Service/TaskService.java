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

    // Metode til at hente alle tasks på et subproject og beregne total price
    public List<Task> getTasksWithTotalPrice(int subProjectId) {

        /* Vi henter alle tasks der tilhører subprojektet */
        List<Task> tasks = taskRepository.getTasksBySubProjectId(subProjectId);

         /*
         Vi looper igennem hver task for at beregne totalprisen for alle tasks.
          */
        for (Task task : tasks) {

            /* Vi ganger hourlyRate med estimatedHours
                Vi bruger setter til at indsætte summen på vores totalprice variabel
                */
            int estimatedHours = subTaskService.calculateEstimatedHours(task.getId());
            task.setEstimatedHours(estimatedHours);
            task.setTotalPrice(task.getHourlyRate() * estimatedHours);
        }

        /* Vi returnerer tasks med totalprisen sat på hvert task objekt */
        return tasks;
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
}
