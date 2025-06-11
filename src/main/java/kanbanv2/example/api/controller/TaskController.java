package kanbanv2.example.api.controller;

import kanbanv2.example.api.dto.TaskDTO;
import kanbanv2.example.api.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAll() {
        return ResponseEntity.ok(taskService.getAll()) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable Long id) {
        Optional<TaskDTO> taskDTO = taskService.getById(id);
        return taskDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TaskDTO> save(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.save(taskDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Optional<TaskDTO> existingTaskDTO = taskService.getById(id);
        if (existingTaskDTO.isPresent()) {
            return ResponseEntity.ok(taskService.update(taskDTO, id));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<TaskDTO> existingTaskDTO = taskService.getById(id);
        if (existingTaskDTO.isPresent()) {
            taskService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
