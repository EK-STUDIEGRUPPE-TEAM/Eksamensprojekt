package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService (TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public int createTask(Task task) {
        return taskRepository.addTask(task);
    }

    public List<Task> findTasksBySubProject(int subProjectId) {
        return taskRepository.findTasksBySubProjectId(subProjectId);
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public void deleteTask(int id){
        taskRepository.deleteTask(id);
    }

    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    public int deleteTaskBySubProjectId (int subProjectId) {
        return taskRepository.deleteTaskBySubProjectId(subProjectId);
    }

    public List<Task> calculateTaskPrice (double hourlyRate){
        return taskRepository.findTaskPrice(hourlyRate);
    }
}
