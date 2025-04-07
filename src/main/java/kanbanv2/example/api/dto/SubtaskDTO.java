package kanbanv2.example.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SubtaskDTO {
    private long id;
    private String title;
    private long taskId;
    private boolean isCompleted;
}
