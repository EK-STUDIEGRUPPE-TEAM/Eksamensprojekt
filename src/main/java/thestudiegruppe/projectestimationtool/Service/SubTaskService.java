package thestudiegruppe.projectestimationtool.Service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Exception.NegativeValueException;
import thestudiegruppe.projectestimationtool.Exception.NotFoundException;
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

        if (subTask.getEstimatedHours() <= 0) {
            throw new NegativeValueException("Timer");
        }

        subTaskRepository.addSubTask(subTask);
    }

    public List<SubTask> getSubTasksByTaskId(int taskId) {
        return subTaskRepository.findSubTaskByTaskId(taskId);
    }

    public SubTask getSubTaskById(int id) {

        try{
            return subTaskRepository.findSubTaskById(id);

        } catch (EmptyResultDataAccessException e){

            throw new NotFoundException("Delopgaven", id);
        }
    }

    public void deleteSubTask(int id) {
        subTaskRepository.deleteSubTask(id);
    }

    public void updateSubTask(SubTask subTask) {

        if (subTask.getEstimatedHours() <= 0) {
            throw new NegativeValueException("Timer");
        }

        subTaskRepository.updateSubTask(subTask);
    }

    public void deleteSubTaskByTaskId(int taskId) {
        subTaskRepository.deleteSubTaskByTaskId(taskId);
    }

    // Udregner det totale antal estimerede timer for en Task (taskId) baseret på dens subtask
    public int calculateEstimatedHours(int taskId) {

        List<SubTask> subTasks = subTaskRepository.findSubTaskByTaskId(taskId);

        int totalHours = 0;

        for (SubTask subTask : subTasks) {

            totalHours += subTask.getEstimatedHours();
        }

        return totalHours;
    }
}
