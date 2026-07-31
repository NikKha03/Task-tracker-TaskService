package NikKha03.TaskService.controllers;

import NikKha03.TaskService.DTO.ProjectRequest;
import NikKha03.TaskService.model.Project;
import NikKha03.TaskService.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "project_controller")
@RestController
@RequestMapping("/task_service/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getMyProjects(@RequestParam String username) {
        ResponseEntity<List<Project>> myProjects = projectService.getMyProjects(username);
        ResponseEntity<List<Project>> otherProjects = projectService.getOtherProjects(username);

        Map<String, Object> response = new HashMap<>();
        response.put("myProjects", myProjects.getBody());
        response.put("otherProjects", otherProjects.getBody());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMyProjects(@PathVariable Long id, @RequestParam String username) {
        ResponseEntity<Project> project = projectService.getProject(id, username);
        return ResponseEntity.ok(project);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.createProject(request));
    }

    @PutMapping("/change/{projectId}")
    public ResponseEntity<?> changeProject(@PathVariable Long projectId, @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.changeProject(projectId, request));
    }

    @Transactional
    @DeleteMapping("/delete/{projectId}")
    public void deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
    }

    @PostMapping("/invite/{username}/{projectId}")
    public ResponseEntity<?> inviteProject(@PathVariable String username, @PathVariable Long projectId) {
        return projectService.inviteInProject(username, projectId);
    }

    @DeleteMapping("/kickedOut/{username}/{projectId}")
    public ResponseEntity<?> kickedOutProject(@PathVariable String username, @PathVariable Long projectId) {
        return projectService.kickedOut(username, projectId);
    }

}
