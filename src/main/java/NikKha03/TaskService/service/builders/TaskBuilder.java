package NikKha03.TaskService.service.builders;

import NikKha03.TaskService.model.Tab;
import NikKha03.TaskService.model.Task;
import NikKha03.TaskService.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskBuilder {
    private final Task task;

    private List<String> implementers;

    public TaskBuilder() {
        this.task = new Task();
        this.implementers = new ArrayList<>();
    }

    public TaskBuilder(Task task) {
        this.task = task;
        this.implementers = task.getImplementers();
    }

    public TaskBuilder setHeader(String header) {
        task.setHeader(header);
        return this;
    }

    public TaskBuilder setComment(String comment) {
        task.setComment(comment);
        return this;
    }

    public TaskBuilder setCreator(String creator) {
        task.setCreator(creator);
        return this;
    }

    public TaskBuilder setImplementers(List<String> implementers) {
        this.implementers = implementers;
        return this;
    }

    public TaskBuilder addImplementer(String implementer) {
        this.implementers.add(implementer);
        return this;
    }

    public TaskBuilder setTab(Tab tab) {
        task.setTab(tab);
        return this;
    }

    public TaskBuilder setDeadline(LocalDateTime deadline) {
        task.setDeadline(deadline);
        return this;
    }

    public TaskBuilder setCreationDate() {
        task.setCreationDate(LocalDateTime.now());
        return this;
    }

    public TaskBuilder setExecutionDate(LocalDateTime executionDate) {
        task.setExecutionDate(executionDate);
        return this;
    }

    public TaskBuilder setTaskStatus(TaskStatus taskStatus) {
        task.setTaskStatus(taskStatus);
        return this;
    }

    public TaskBuilder setUrlsObj(String urlsObj) {
        task.setUrlsObj(urlsObj);
        return this;
    }

    public TaskBuilder setTag(String tag) {
        task.setTags(tag);
        return this;
    }


    public Task build() {
        task.setImplementers(this.implementers);
        return task;
    }
}
