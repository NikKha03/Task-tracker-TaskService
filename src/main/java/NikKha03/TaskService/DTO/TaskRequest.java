package NikKha03.TaskService.DTO;

import NikKha03.TaskService.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TaskRequest {

    private Long tabId;

    private String header;

    private String comment;

    private String deadline;

    private TaskStatus taskStatus;

    private String implementer;

    private String urlsObj;

    private String tags;

    public TaskRequest() {
    }

    public TaskRequest(Long tabId, String header) {
        this.tabId = tabId;
        this.header = header;
    }
}
