package NikKha03.TaskService.mappers;

import NikKha03.TaskService.model.Project;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectMapper {

    @Select("SELECT p.* FROM projects p JOIN users_projects u ON u.project = p.project_id WHERE u.keycloakId=#{keycloakId} AND p.project_owner=#{keycloakId}")
    List<Project> getMyProjects(@Param("keycloakId") String keycloakId);

    @Select("SELECT p.* FROM projects p JOIN users_projects u ON u.project = p.project_id WHERE u.keycloakId=#{keycloakId} AND u.role=#{role}")
    List<Project> getProjectsWithRole(@Param("keycloakId") String keycloakId, @Param("role") String role);

    // Удаление записей из project_member_roles по пользователю и проекту
    @Delete("""
                DELETE FROM project_member_roles
                WHERE user_in_project_id IN (
                    SELECT uip.id
                    FROM users_in_projects uip
                    WHERE uip.project = #{projectId} AND uip.keycloakId = #{keycloakId}
                )
            """)
    void deleteUserRolesFromProject(@Param("projectId") Long projectId, @Param("keycloakId") String keycloakId);

    // Удаление пользователя из users_in_projects
    @Delete("""
                DELETE FROM users_in_projects
                WHERE project = #{projectId} AND keycloakId = #{keycloakId}
            """)
    void deleteUserFromProject(@Param("projectId") Long projectId, @Param("keycloakId") String keycloakId);

}
