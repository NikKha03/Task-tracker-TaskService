package sharpBubbles.taskTracker.service;

import org.springframework.stereotype.Service;
import sharpBubbles.taskTracker.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();
    Task createNewTask(Task task);
    void deleteTask(Long taskId);
    Task changeTask(Task task);

}
