package NikKha03.TaskService.service.impl;

import NikKha03.TaskService.model.User;
import NikKha03.TaskService.repository.ProjectOwnerRepository;
import NikKha03.TaskService.service.ProjectOwnerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProjectOwnerServiceImpl implements ProjectOwnerService {

    private final ProjectOwnerRepository repository;

    public ProjectOwnerServiceImpl(ProjectOwnerRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void saveUser(String keycloakId) {
        repository.saveUser(keycloakId);
    }

    @Override
    public User findByKeycloakId(String keycloakId) {
        return repository.findByKeycloakId(keycloakId);
    }
}
