package kanbanv2.example.api.mapper;

import kanbanv2.example.api.dto.*;
import kanbanv2.example.api.entity.Board;
import kanbanv2.example.api.entity.ColumnStatus;
import kanbanv2.example.api.entity.Subtask;
import kanbanv2.example.api.entity.Task;

import java.util.stream.Collectors;

public class BoardMapper {

    public static BoardDTO  toDTO(Board board) {
        BoardDTO dto = new BoardDTO();

        dto.setId(board.getId());
        dto.setName(board.getName());
//        dto.setColumnStatus(board.getColumns_status().stream().map(BoardMapper::mapColumnDTO).collect(Collectors.toList()));

        return dto;
    }


    public static ColumnStatusDTO mapColumnDTO(ColumnStatus columnStatus) {
        ColumnStatusDTO dto = new ColumnStatusDTO();

        dto.setId(columnStatus.getId());
        dto.setName(columnStatus.getName());
        dto.setBoardId(columnStatus.getBoard().getId());
        dto.setTasks(columnStatus.getTasks().stream().map(BoardMapper::mapTaskDTO).collect(Collectors.toList()));

        return dto;

    }

    public static ColumnStatusListDTO mapColumnListDTO(ColumnStatus columnStatus) {
        ColumnStatusListDTO dto = new ColumnStatusListDTO();

        dto.setId(columnStatus.getId());
        dto.setName(columnStatus.getName());
        dto.setBoardId(columnStatus.getBoard().getId());
        dto.setTasks(columnStatus.getTasks().stream().map(BoardMapper::mapTaskListDTO).collect(Collectors.toList()));

        return dto;

    }

    public static TaskListDTO mapTaskListDTO(Task task) {
        TaskListDTO dto = new TaskListDTO();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setColumnStatusId(task.getColumnStatus().getId());
        dto.setDescription(task.getDescription());

        return dto;
    }

    public static TaskDTO mapTaskDTO(Task task) {
        TaskDTO dto = new TaskDTO();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setColumnStatusId(task.getColumnStatus().getId());
        dto.setDescription(task.getDescription());
        dto.setSubtasks(task.getSubtasks().stream().map(BoardMapper::mapSubtasksDTO).collect(Collectors.toList()));

        return dto;
    }

    public static SubtaskDTO mapSubtasksDTO(Subtask subtask){
        SubtaskDTO dto = new SubtaskDTO();

        dto.setId(subtask.getId());
        dto.setTitle(subtask.getTitle());
        dto.setTaskId(subtask.getTask().getId());
        dto.setCompleted(subtask.isCompleted());

        return dto;
    }

    public static Board toEntity(BoardDTO boardDTO) {
        Board board = new Board();

        board.setId(board.getId());
        board.setName(boardDTO.getName());
//        board.setColumns_status(boardDTO.getColumnStatus()
//                .stream()
//                .map(BoardMapper::mapColumnToEntity)
//                .collect(Collectors.toList()));

        return board;
    }

    public static ColumnStatus mapColumnToEntity(ColumnStatusDTO dto) {
        ColumnStatus column = new ColumnStatus();
        column.setId(dto.getId());
        column.setName(dto.getName());
        column.setTasks(dto.getTasks()
                .stream()
                .map(BoardMapper::mapTaskToEntity)
                .collect(Collectors.toList()));


        return column;
    }

    public static Task mapTaskToEntity(TaskDTO dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());


        task.setSubtasks(dto.getSubtasks()
                .stream()
                .map(BoardMapper::mapSubtaskToEntity)
                .collect(Collectors.toList()));


        return task;
    }

    public static Subtask mapSubtaskToEntity(SubtaskDTO dto) {
        Subtask subtask = new Subtask();
        subtask.setId(dto.getId());
        subtask.setTitle(dto.getTitle());
        subtask.setCompleted(dto.isCompleted());
        return subtask;
    }

}
