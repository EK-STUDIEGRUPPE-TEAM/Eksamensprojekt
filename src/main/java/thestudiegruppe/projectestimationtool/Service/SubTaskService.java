package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Exception.NegativeValueException;
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

    public List<SubTask> getSubTasksByTaskId(int taskId) {
        return subTaskRepository.findSubTaskByTaskId(taskId);
    }

    public void deleteSubTask(int id) {
        subTaskRepository.deleteSubTask(id);
    }

    public void updateSubTask(SubTask subTask) {
        subTaskRepository.updateSubTask(subTask);
    }

    public void deleteSubTaskByTaskId(int taskId) {
        subTaskRepository.deleteSubTaskByTaskId(taskId);
    }

    // Udregner det totale antal estimerede timer for en Task (taskId) baseret på dens subtask
    public int calculateEstimatedHours(int taskId) {

        // Henter alle subtasks
        List<SubTask> subTasks = subTaskRepository.findSubTaskByTaskId(taskId);

        // Variabel for samlede antal timer
        int totalHours = 0;

        //Går igennem hver subTask i listen mens den beregner timerne.
        for (SubTask subTask : subTasks) {

            // Hvis estimatedHours er mindre end 0 kaster vi en custom NegativeValueException
            // fordi negative timer ikke giver mening i tidsberegning.
            if (subTask.getEstimatedHours() < 0) {
                throw new NegativeValueException("Timer");
            }
            // Lægger timerne sammen i vores variabel som sum.
            totalHours += subTask.getEstimatedHours();
        }
        // returnere den samlede antal timer for én task
        return totalHours;
    }
}
