package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Exception.NotFoundException;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Model.SubTask;
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

    //Opretter en ny task i databasen.
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

    // Metode til at hente alle tasks på et subproject
    // Deudover henter metoden også alle subtasks på en task og beregner total price for en task
    public List<Task> getFullTasks(int subProjectId) {

        /* Vi henter alle tasks der tilhører subprojektet */
        List<Task> tasks = taskRepository.getTasksBySubProjectId(subProjectId);

         // Vi looper igennem hver task for at hente subtasks og beregne totalprisen for alle tasks.
        for (Task task : tasks) {

            /* Vi ganger hourlyRate med estimatedHours for at beregne total price for en task
                Vi bruger setter til at indsætte summen i vores totalprice variabel i task objektet
                */
            int estimatedHours = subTaskService.calculateEstimatedHours(task.getId());
            task.setEstimatedHours(estimatedHours);
            task.setTotalPrice(task.getHourlyRate() * estimatedHours);

            // Vi bruger setter til at indsætte relevante subtasks i task objektet
            task.setSubTasks(subTaskService.getSubTasksByTaskId(task.getId()));
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

    public Task findTaskById(int id) {

        /* Denne metode bruges til at finde en bestemt task
           ud fra taskens id */

        Task task = taskRepository.findById(id);

        /* Hvis repository ikke finder et projekt,
           kaster vi vores egen NotFoundException.

           Det gør vi, så systemet kan håndtere fejlen pænt,
           fx med en 404-side */
        if (task == null) {
            throw new NotFoundException("Task", id);
        }

        /* Hvis projektet findes, returnerer vi det */
        return task;
    }

    public Task findFullTask(int id){

        // Vi henter ét bestemt projekt ud fra projektets id
        Task task = taskRepository.findById(id);

        /*
        Vi henter alle subprojects der tilhører projektet ud fra projektets id
        Service metoden returnerer fulde subproject objekter med tasks og subtasks indsat
         */
        List<SubTask> subTasks = subTaskService.getSubTasksByTaskId(id);

        // Vi bruger setter til at indsætte subproject listen på project
        task.setSubTasks(subTasks);

        // Til sidst returnerer vi project med subprojects og tasks indsat
        return task;
    }


}
