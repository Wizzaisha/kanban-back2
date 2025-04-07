package kanbanv2.example.api.service;

import kanbanv2.example.api.dao.ColumnStatusRepository;
import kanbanv2.example.api.dao.TaskRepository;
import kanbanv2.example.api.dto.TaskDTO;
import kanbanv2.example.api.entity.ColumnStatus;
import kanbanv2.example.api.entity.Subtask;
import kanbanv2.example.api.entity.Task;
import kanbanv2.example.api.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ColumnStatusRepository columnStatusRepository;

    @Autowired
    public TaskService(ColumnStatusRepository columnStatusRepository, TaskRepository taskRepository) {
        this.columnStatusRepository = columnStatusRepository;
        this.taskRepository = taskRepository;
    }


    public List<TaskDTO> getAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(BoardMapper::mapTaskDTO).collect(Collectors.toList());
    }

    public Optional<TaskDTO> getById(long id) {
        return taskRepository.findById(id).map(BoardMapper::mapTaskDTO);
    }

    @Transactional
    public TaskDTO save(TaskDTO taskDTO) {

        ColumnStatus columnStatus = columnStatusRepository.findById(taskDTO.getColumnStatusId()).orElseThrow(() -> new RuntimeException("Board not found"));
        Task task = BoardMapper.mapTaskToEntity(taskDTO);

        task.setColumnStatus(columnStatus);

        if (task.getSubtasks() != null) {
            task.getSubtasks().forEach(subtask -> subtask.setTask(task));
        }

        Task taskSaved = taskRepository.save(task);
        return BoardMapper.mapTaskDTO(taskSaved);
    }


    @Transactional
    public TaskDTO update(TaskDTO taskDTO, long id) {

        ColumnStatus columnStatus = columnStatusRepository.findById(taskDTO.getColumnStatusId()).orElseThrow(() -> new RuntimeException("Board not found"));

        Task existingTaskOpt = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Board not found"));

        existingTaskOpt.setColumnStatus(columnStatus);
        existingTaskOpt.setTitle(taskDTO.getTitle());
        existingTaskOpt.setDescription(taskDTO.getDescription());

        existingTaskOpt.getSubtasks().clear();

        if (taskDTO.getSubtasks() != null) {
            taskDTO.getSubtasks().forEach(subtaskDTO -> {
                Subtask subtask = BoardMapper.mapSubtaskToEntity(subtaskDTO);
                subtask.setTask(existingTaskOpt);
                existingTaskOpt.getSubtasks().add(subtask);
            });
        }

        Task updatedTask = taskRepository.save(existingTaskOpt);
        return BoardMapper.mapTaskDTO(updatedTask);
    }

    @Transactional
    public void delete(long id) {
        taskRepository.deleteById(id);
    }

}
