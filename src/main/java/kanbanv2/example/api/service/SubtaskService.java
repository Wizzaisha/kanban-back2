package kanbanv2.example.api.service;

import kanbanv2.example.api.dao.SubtaskRepository;
import kanbanv2.example.api.dto.SubtaskDTO;
import kanbanv2.example.api.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;

    @Autowired
    public SubtaskService(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    public Optional<SubtaskDTO> getById(long id) {
        return subtaskRepository.findById(id).map(BoardMapper::mapSubtasksDTO);
    }

    @Transactional
    public void delete(long id) {
        subtaskRepository.deleteById(id);
    }

}
