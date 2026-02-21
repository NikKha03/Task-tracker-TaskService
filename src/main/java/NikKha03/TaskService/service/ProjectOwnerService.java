package NikKha03.TaskService.service;

import NikKha03.TaskService.model.User;

public interface ProjectOwnerService {

    void saveUser(String keycloakId);

    User findByKeycloakId(String keycloakId);

}
