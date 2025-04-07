package kanbanv2.example.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TaskDTO {
    private long id;
    private String title;
    private String description;
    private long columnStatusId;
    private List<SubtaskDTO> subtasks;
}
