package kanbanv2.example.api.controller;

import kanbanv2.example.api.dto.ColumnStatusDTO;
import kanbanv2.example.api.dto.ColumnStatusListDTO;
import kanbanv2.example.api.service.ColumnStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/columns")
@CrossOrigin(origins = "*")
public class ColumnStatusController {

    private final ColumnStatusService columnStatusService;

    @Autowired
    public ColumnStatusController(ColumnStatusService columnStatusService) {
        this.columnStatusService = columnStatusService;
    }


    @GetMapping
    public ResponseEntity<List<ColumnStatusListDTO>> getAll() {
        return ResponseEntity.ok(columnStatusService.getAllColumnStatuses()) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ColumnStatusListDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(columnStatusService.getColumnListByBoardId(id)) ;
    }

    @PostMapping
    public ResponseEntity<ColumnStatusDTO> save(@RequestBody ColumnStatusDTO columnStatusDTO) {
        return ResponseEntity.ok(columnStatusService.saveColumn(columnStatusDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColumnStatusDTO> update(@PathVariable Long id, @RequestBody ColumnStatusDTO columnStatusDTO) {
        Optional<ColumnStatusDTO> existingColumn = columnStatusService.getColumnById(id);
        if (existingColumn.isPresent()) {
            return ResponseEntity.ok(columnStatusService.updateColumn(columnStatusDTO, id));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<ColumnStatusDTO> existingColumn = columnStatusService.getColumnById(id);
        if (existingColumn.isPresent()) {
            columnStatusService.deleteBoard(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
