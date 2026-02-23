package NikKha03.TaskService.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskWebSocketMessage<T> {
    MessageType type;

    Method method;

    Long taskId;

    T changedParams; // T может быть ProjectRequest, TaskRequest...

    public enum Method {
        POST, PUT, DELETE
    }

    public enum MessageType {
        TASK, DOCUMENT, COLUMN, PROJECT, TAB
    }

}

