package NikKha03.TaskService.service.impl;

import NikKha03.TaskService.DTO.ProjectRequest;
import NikKha03.TaskService.mappers.ProjectMapper;
import NikKha03.TaskService.model.*;
import NikKha03.TaskService.repository.*;
import NikKha03.TaskService.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final ProjectMapper mapper;

    private final ProjectOwnerRepository projectOwnerRepository;
    private final UserInProjectRepository userInProjectRepository;
    private final TabRepository tabRepository;
    private final RolesInProjectRepository rolesInProjectRepository;

    @Override
    public ResponseEntity<?> createProject(ProjectRequest request) {
        if (request.getName() == null)
            return ResponseEntity.badRequest().body("Empty project name");

        // Создаем проект
        Project project = new Project();
        project.setName(request.getName());
        if (!request.getProjectType().equals(ProjectType.COMPANY)) {
            project.setProjectOwner(projectOwnerRepository.findByUsername(request.getPrincipalUser()));
        } else {
            project.setProjectOwner(projectOwnerRepository.findById(request.getProjectOwner()).orElse(null));
        }
        project.setProjectType(request.getProjectType());
        repository.save(project);

        // Добавляем создателя к участникам проекта
        UserInProject userInProject = new UserInProject();
        userInProject.setProject(project);
        userInProject.setUsername(request.getPrincipalUser());
        List<RolesInProject> roles = new ArrayList<>();
        // Добавляю роли: CREATOR, ADMIN, MEMBER
        roles.add(rolesInProjectRepository.findById(1L).orElse(null));
        roles.add(rolesInProjectRepository.findById(2L).orElse(null));
        roles.add(rolesInProjectRepository.findById(3L).orElse(null));
        userInProject.setRoles(roles);
        userInProjectRepository.save(userInProject);

        List<UserInProject> team = new ArrayList<>();
        team.add(userInProject);
        project.setTeam(team);

        // Создаем начальную вкладку проекта
        Tab tab = new Tab();
        tab.setProject(project);
        tab.setName("Начальная доска");
        tabRepository.save(tab);

        return ResponseEntity.ok(project);
    }

    @Override
    public ResponseEntity<?> changeProject(Long projectId, ProjectRequest request) {
        Project project = repository.findById(projectId).orElse(null);
        if (project == null)
            return ResponseEntity.badRequest().body("Invalid projectId");
        if (request.getName().length() == 0)
            return ResponseEntity.badRequest().body("New project name is empty");

        project.setName(request.getName());

        return ResponseEntity.ok(repository.save(project));
    }

    @Override
    public ResponseEntity<?> inviteInProject(String username, Long projectId) {
        Project project = repository.findById(projectId).orElse(null);

        UserInProject invitedUser = new UserInProject();
        invitedUser.setProject(project);
        invitedUser.setUsername(username);
        List<RolesInProject> roles = new ArrayList<>();
        // Добавляю роль MEMBER
        roles.add(rolesInProjectRepository.findById(3L).orElse(null));
        invitedUser.setRoles(roles);

        project.setProjectType(ProjectType.SEVERAL_USERS);
        List<UserInProject> team = project.getTeam();
        team.add(invitedUser);
        project.setTeam(team);


        return ResponseEntity.ok(repository.save(project));
    }

    @Override
    @Transactional
    public ResponseEntity<?> kickedOut(String username, Long projectId) {
        mapper.deleteUserRolesFromProject(projectId, username);
        mapper.deleteUserFromProject(projectId, username);

        return ResponseEntity.ok().body("User kicked out");
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId) {
        repository.deleteById(projectId);
    }

    @Override
    public ResponseEntity<Project> getProject(Long id, String username) {
        Project project = repository.findById(id).orElse(null);
        boolean isMember = false;

        if (project != null) {
            List<UserInProject> team = project.getTeam();
            for (UserInProject userInProject : team) {
                if (userInProject.getUsername().equals(username))
                    isMember = true;
            }
        } else {
            return ResponseEntity.badRequest().body(null);
        }

        if (isMember) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Override
    public ResponseEntity<List<Project>> getMyProjects(String username) {
        return ResponseEntity.ok(repository.getMyProjects(username));
    }

    @Override
    public ResponseEntity<List<Project>> getOtherProjects(String username) {
        return ResponseEntity.ok(repository.getOtherProjects(username));
    }

}
