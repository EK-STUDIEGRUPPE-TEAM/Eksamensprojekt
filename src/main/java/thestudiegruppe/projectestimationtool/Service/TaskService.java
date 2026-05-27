package thestudiegruppe.projectestimationtool.Service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Exception.NegativeValueException;
import thestudiegruppe.projectestimationtool.Exception.NotFoundException;
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

    public void createTask(Task task) {

        if (task == null) {
            throw new IllegalArgumentException("Task må ikke være null");
        }

        if (task.getHourlyRate() <= 0) {
            throw new NegativeValueException("Timepris");
        }

        taskRepository.addTask(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    //Henter alle tasks, der hører til et bestemt subProject.
    public List<Task> getTasksBySubProjectId(int subProjectId) {

        if (subProjectId <= 0) {
            throw new IllegalArgumentException("SubProject id skal være større end 0");
        }

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

        if (task.getHourlyRate() <= 0) {
            throw new NegativeValueException("Timepris");
        }

        taskRepository.updateTask(task);
    }


    public int deleteTaskBySubProjectId(int subProjectId) {

        if (subProjectId <= 0) {
            throw new IllegalArgumentException("SubProject id skal være større end 0");
        }

        return taskRepository.deleteTaskBySubProjectId(subProjectId);
    }

    public Task findTaskById(int id) {

        try {
            Task task = taskRepository.findById(id);
            return task;
        } catch (EmptyResultDataAccessException e) {

            throw new NotFoundException("Opgaven", id);
        }
    }

    public Task findFullTask(int id){

        Task task;

        try {
            // Vi henter ét bestemt projekt ud fra projektets id
            task = taskRepository.findById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Opgaven", id);
        }
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
