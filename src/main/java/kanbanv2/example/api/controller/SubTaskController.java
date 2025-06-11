package kanbanv2.example.api.controller;

import kanbanv2.example.api.dto.SubtaskDTO;
import kanbanv2.example.api.dto.TaskDTO;
import kanbanv2.example.api.service.SubtaskService;
import kanbanv2.example.api.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/subtasks")
public class SubTaskController {

    private final SubtaskService subtaskService;

    @Autowired
    public SubTaskController(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<SubtaskDTO> existingSubTaskDTO = subtaskService.getById(id);
        if (existingSubTaskDTO.isPresent()) {
            subtaskService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
