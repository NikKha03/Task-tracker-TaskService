package NikKha03.TaskService.mappers;


import NikKha03.TaskService.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Select("SELECT * FROM tasks WHERE tab=#{tabId} AND task_status=#{status}")
    List<Task> getTasksByTabAndStatus(@Param("tabId") Long tabId, @Param("status") String status);

    @Select("SELECT * FROM tasks WHERE implementer=#{implementer} AND task_status=#{status}")
    List<Task> getTasksByImplementerAndStatus(@Param("implementer") String implementer, @Param("status") String status);

    @Select("SELECT t.* FROM tasks t INNER JOIN tabs tab ON t.tab=tab.tab_id WHERE tab.project_id=#{projectId} AND t.task_status=#{status}")
    List<Task> getTasksByProjectIdAndStatus(@Param("projectId") Long projectId, @Param("status") String status);

    @Update("UPDATE tasks SET is_pause=#{value} WHERE task_id=#{taskId}")
    void updateIsPause(@Param("taskId") Long taskId, @Param("value") Boolean value);

}
