package kanbanv2.example.api.service;

import kanbanv2.example.api.dao.BoardRepository;
import kanbanv2.example.api.dao.ColumnStatusRepository;
import kanbanv2.example.api.dto.ColumnStatusDTO;
import kanbanv2.example.api.entity.Board;
import kanbanv2.example.api.entity.ColumnStatus;
import kanbanv2.example.api.entity.Task;
import kanbanv2.example.api.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColumnStatusService {

    private final ColumnStatusRepository columnStatusRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public ColumnStatusService(ColumnStatusRepository columnStatusRepository, BoardRepository boardRepository) {
        this.columnStatusRepository = columnStatusRepository;
        this.boardRepository = boardRepository;
    }


    public List<ColumnStatusDTO> getAllColumnStatuses() {
        List<ColumnStatus> columnStatuses = columnStatusRepository.findAll();
        return columnStatuses.stream().map(BoardMapper::mapColumnDTO).collect(Collectors.toList());
    }

    public List<ColumnStatusDTO> getColumnListByBoardId(long id) {
        List<ColumnStatus> columnStatuses = columnStatusRepository.findByBoardId(id);
        return columnStatuses.stream().map(BoardMapper::mapColumnDTO).collect(Collectors.toList());
    }

    public Optional<ColumnStatusDTO> getColumnById(long id) {
        return columnStatusRepository.findById(id).map(BoardMapper::mapColumnDTO);
    }

    @Transactional
    public ColumnStatusDTO saveColumn(ColumnStatusDTO columnStatusDTO) {

        Board board = boardRepository.findById(columnStatusDTO.getBoardId()).orElseThrow(() -> new RuntimeException("Board not found"));
        ColumnStatus columnStatus = BoardMapper.mapColumnToEntity(columnStatusDTO);

        columnStatus.setBoard(board);

        if (columnStatus.getTasks() != null) {
            columnStatus.getTasks().forEach(task -> task.setColumnStatus(columnStatus));
        }

        ColumnStatus columnSaved = columnStatusRepository.save(columnStatus);
        return BoardMapper.mapColumnDTO(columnSaved);
    }


    @Transactional
    public ColumnStatusDTO updateColumn(ColumnStatusDTO columnStatusDTO, long id) {

        Board board = boardRepository.findById(columnStatusDTO.getBoardId()).orElseThrow(() -> new RuntimeException("Board not found"));

        ColumnStatus existingColumnOpt = columnStatusRepository.findById(id).orElseThrow(() -> new RuntimeException("Board not found"));

        existingColumnOpt.setBoard(board);
        existingColumnOpt.setName(columnStatusDTO.getName());

        existingColumnOpt.getTasks().clear();

        if (columnStatusDTO.getTasks() != null) {
            columnStatusDTO.getTasks().forEach(taskDTO -> {
                Task task = BoardMapper.mapTaskToEntity(taskDTO);
                task.setColumnStatus(existingColumnOpt);
                existingColumnOpt.getTasks().add(task);
            });
        }

        ColumnStatus updatedColumn = columnStatusRepository.save(existingColumnOpt);
        return BoardMapper.mapColumnDTO(updatedColumn);
    }

    @Transactional
    public void deleteBoard(long id) {
        columnStatusRepository.deleteById(id);
    }



}
