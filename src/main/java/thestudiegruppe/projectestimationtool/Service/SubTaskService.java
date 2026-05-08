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
        return subTaskRepository.getAllSubTask;

    }

    public void deleteSubTask(int id) {
        subTaskRepository.deleteSubTask(id);
    }
}
