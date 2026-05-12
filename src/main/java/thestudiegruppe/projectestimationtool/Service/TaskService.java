package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final SubTaskService subTaskService;

    public TaskService (TaskRepository taskRepository, SubTaskService subTaskService){
        this.taskRepository = taskRepository;
        this.subTaskService = subTaskService;
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
                   if (task.getHourlyRate() < 0){
                       throw new IllegalArgumentException("Calculated price can't be less than zero!");
                   }
                  totalPrice += task.getHourlyRate() * subTaskService.calculateEstimatedHours(task.getId());
               }
               return totalPrice;
    }
}
