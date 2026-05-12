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

    public List<Task> getAllTasks(){
        return taskRepository.findAllTasks();
    }

    public List<Task> getTasksBySubProject(int subProjectId) {
        return taskRepository.findTasksBySubProjectId(subProjectId);
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

    public double calculateTaskPrice (int subProjectId){

        double totalPrice = 0;
        List<Task> tasks = taskRepository.findTasksBySubProjectId(subProjectId);

               for (Task task : tasks) {
                  totalPrice += task.getHourlyRate();
               }
                   return totalPrice;
    }
}
