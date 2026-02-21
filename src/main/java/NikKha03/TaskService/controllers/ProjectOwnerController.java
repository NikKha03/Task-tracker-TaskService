package NikKha03.TaskService.controllers;

import NikKha03.TaskService.DTO.UserRequest;
import NikKha03.TaskService.model.User;
import NikKha03.TaskService.repository.ProjectOwnerRepository;
import NikKha03.TaskService.service.ProjectOwnerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "project_owner_controller")
@RestController
@RequestMapping("/task_service/project_owner/")
public class ProjectOwnerController {

    private final ProjectOwnerService projectOwnerService;

    public ProjectOwnerController(ProjectOwnerService projectOwnerService) {
        this.projectOwnerService = projectOwnerService;
    }

    @PostMapping("/user")
    public void addUser(@RequestBody UserRequest userRequest) {
        projectOwnerService.saveUser(userRequest.getKeycloakId());
    }

    @GetMapping("/user/{username}")
    public boolean isUserDataHave(@PathVariable("username") String keycloakId) {
        System.out.println(keycloakId);
        System.out.println(projectOwnerService.findByKeycloakId(keycloakId));
        return projectOwnerService.findByKeycloakId(keycloakId) != null;
    }

}
