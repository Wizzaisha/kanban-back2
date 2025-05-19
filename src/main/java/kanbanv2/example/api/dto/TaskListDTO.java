package kanbanv2.example.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaskListDTO {
    private long id;
    private String title;
    private String description;
    private long columnStatusId;
}
