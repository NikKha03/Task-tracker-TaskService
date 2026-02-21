package NikKha03.TaskService.controllers;

import NikKha03.TaskService.DTO.TaskRequest;
import NikKha03.TaskService.model.Task;
import NikKha03.TaskService.model.TaskStatus;
import NikKha03.TaskService.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Tag(name = "task_controller")
@RestController
@RequestMapping("/task_service/task")
public class TaskController {

    private final TaskService taskService;

    private final SocketConnectionHandler websocket;

    public TaskController(TaskService taskService, SocketConnectionHandler websocket) {
        this.taskService = taskService;
        this.websocket = websocket;
    }

    /*
    Есть задачи, которые запрашиваю по userId,
    а есть те, которые можно запросить и по userId и по categoryId!!!
    Поэтому, один из параметров может быть null!
    */

    // задачи по канбан-доске
    @GetMapping("/byTab/{tabId}")
    public ResponseEntity<?> getByTab(@PathVariable("tabId") Long tabId,
                                      @RequestParam("projectId") Long projectId,
                                      @RequestParam("username") String username,
                                      @RequestParam("testSocketId") String testSocketId) {
        try {
            websocket.handleData(testSocketId, taskService.getByTab(projectId, tabId, username).toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return taskService.getByTab(projectId, tabId, username);
    }

    // задачи, которые нужно сделать
    @GetMapping("/awaitingCompletionTasks")
    public List<Task> getAwaitingCompletionTasks(@RequestParam String implementer) {
        List<Task> tasks = taskService.getTasksByStatus(implementer, TaskStatus.AWAITING_COMPLETION.toString());
        return tasks.stream()
                .filter(task -> task.getDeadline() != null
                        // && (LocalDate.now().isBefore(task.getDeadline().toLocalDate())
                        // || LocalDate.now().isEqual(task.getDeadline().toLocalDate()))
                )
                .sorted(Comparator.comparing(Task::getDeadline).thenComparing(Task::getCreationDate))
                .toList();
    }

    // задачи без даты выполнения
    @GetMapping("/withoutDateImplTasks")
    public List<Task> getTaskWithoutDateImpl(@RequestParam String implementer) {
        List<Task> tasks1 = taskService.getTasksByStatus(implementer, TaskStatus.AWAITING_COMPLETION.toString());
        List<Task> tasks2 = taskService.getTasksByStatus(implementer, TaskStatus.IN_PROGRESS.toString());
        tasks2.addAll(tasks1);

        return tasks2.stream()
                .filter(task -> task.getDeadline() == null)
                .sorted(Comparator.comparing(Task::getCreationDate))
                .toList();
    }

    // задачи, которые в работе
    @GetMapping("/inProgressTasks")
    public List<Task> getInProgressTasks(@RequestParam String implementer) {
        return taskService.getInProgressTasks(implementer);
    }

    // задачи, которые выполнены
    @GetMapping("/completedTasks")
    public List<Task> getCompletedTask(@RequestParam String implementer) {
        List<Task> tasks = taskService.getTasksByStatus(implementer, TaskStatus.COMPLETED.toString());
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getExecutionDate).reversed().thenComparing(Task::getCreationDate))
                .toList();
    }

    // задачи, которые не были выполнены
    @GetMapping("/incompleteTasks")
    public List<Task> getTasksIncomplete(@RequestParam String implementer) {
        return taskService.getTasksIncomplete(implementer);
    }

    @PostMapping("/create/{creator}")
    public ResponseEntity<?> createTask(@PathVariable("creator") String creator, @RequestBody TaskRequest request) {
        return taskService.createTask(creator, request);
    }

    @Transactional
    @PutMapping("/change/{taskId}")
    public ResponseEntity<?> changeTask(@PathVariable("taskId") Long taskId, @RequestBody TaskRequest request) {
        return taskService.changeTask(taskId, request);
    }

    @Transactional
    @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
    }

    @PutMapping("/setStatusOnCompleted/{taskId}")
    public Task setStatusOnCompleted(@PathVariable("taskId") Long taskId) {
        return taskService.setStatus(taskId, TaskStatus.COMPLETED, false);
    }

    @PutMapping("/setStatusOnInProgress/{taskId}")
    public Task setStatusOnInProgress(@PathVariable("taskId") Long taskId) {
        return taskService.setStatus(taskId, TaskStatus.IN_PROGRESS, true);
    }

    @PutMapping("/setStatusOnAwaitingCompletion/{taskId}")
    public Task setStatusOnAwaitingCompletion(@PathVariable("taskId") Long taskId) {
        return taskService.setStatus(taskId, TaskStatus.AWAITING_COMPLETION, true);
    }

}