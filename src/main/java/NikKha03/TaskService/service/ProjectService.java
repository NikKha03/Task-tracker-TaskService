package NikKha03.TaskService.service;

import NikKha03.TaskService.DTO.ProjectRequest;
import NikKha03.TaskService.model.Project;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {

    ResponseEntity<?> createProject(ProjectRequest request);

    ResponseEntity<?> changeProject(Long projectId, ProjectRequest request);

    void deleteProject(Long projectId);

    ResponseEntity<List<Project>> getMyProjects(String username);

    ResponseEntity<List<Project>> getOtherProjects(String username);

    ResponseEntity<Project> getProject(Long id, String username);

    ResponseEntity<?> inviteInProject(String username, Long projectId);

    ResponseEntity<?> kickedOut(String username, Long projectId);

}
