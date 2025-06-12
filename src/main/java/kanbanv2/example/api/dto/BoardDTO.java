package kanbanv2.example.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardDTO {
    private Long id;
    private String name;
    //private List<ColumnStatusDTO> columnStatus;
}
