package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Model.SubTask;
import thestudiegruppe.projectestimationtool.Repository.SubTaskRepository;

import java.util.List;

@Service
public class SubTaskService {

    private SubTaskRepository subTaskRepository;

    public SubTaskService(SubTaskRepository subTaskRepository) {
        this.subTaskRepository = subTaskRepository;
    }

    public void createSubTask(SubTask subTask) {
        subTaskRepository.addSubTask(subTask);
    }

    public List<SubTask> getAllSubTasks() {
        return subTaskRepository.findAllSubTask();
    }

    public List<SubTask> getSubTasksByTaskId(int taskId){
        return subTaskRepository.findSubTaskByTaskId(taskId);
    }

    public void deleteSubTask(int id) {
        subTaskRepository.deleteSubTask(id);
    }

    public void updateTask(SubTask subTask){
        subTaskRepository.updateSubTask(subTask);
    }

    public void deleteSubTaskByTaskId (int taskId){
        subTaskRepository.deleteSubTaskByTaskId(taskId);
    }

    // Udregner det totale antal estimerede timer for en Task (taskId) baseret på dens subtask
    // Hvis estimatedHours er mindre end 0 kaster vi en standard exception
    // Kan erstattes af custom exception
    public int calculateEstimatedHours(int taskId){
        List<SubTask> subTasks = subTaskRepository.findSubTaskByTaskId(taskId);
        int totalHours = 0;
        for (SubTask subTask : subTasks){
            if (subTask.getEstimatedHours() < 0){
                throw new IllegalArgumentException("Estimated hours can't be less than zero!");
            }
            totalHours += subTask.getEstimatedHours();
        }
        return totalHours;
    }
}
