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
        // Hvis estimatedHours er 0 eller mindre kaster vi en custom NegativeValueException
        // fordi negative timer eller 0 timer ikke giver mening i tidsberegning.
        if (subTask.getEstimatedHours() <= 0) {
            throw new NegativeValueException("Timer");
        }
        subTaskRepository.addSubTask(subTask);
    }

    public List<SubTask> getSubTasksByTaskId(int taskId) {
        return subTaskRepository.findSubTaskByTaskId(taskId);
    }

    public SubTask getSubTaskById(int id) {
         /* Hvis repository ikke finder et projekt,
           kaster vi vores egen NotFoundException. */

        // Vi bruger try/catch, fordi vi først skal prøve at finde projektet i databasen.
        // Hvis databasen ikke finder et projekt, fanger catch fejlen.
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
        // Hvis estimatedHours er 0 eller mindre kaster vi en custom NegativeValueException
        // fordi negative timer eller 0 timer ikke giver mening i tidsberegning.
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

        // Henter alle subtasks
        List<SubTask> subTasks = subTaskRepository.findSubTaskByTaskId(taskId);

        // Variabel for samlede antal timer
        int totalHours = 0;

        //Går igennem hver subTask i listen mens den beregner timerne.
        for (SubTask subTask : subTasks) {

            // Lægger timerne sammen i vores variabel som sum.
            totalHours += subTask.getEstimatedHours();
        }
        // returnere den samlede antal timer for én task
        return totalHours;
    }
}
