package kanbanv2.example.api.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ColumnStatusListDTO {
    private long id;
    private String name;
    private long boardId;
    private List<TaskListDTO> tasks;
}
