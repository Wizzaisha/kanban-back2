package kanbanv2.example.api.service;

import kanbanv2.example.api.dao.BoardRepository;
import kanbanv2.example.api.dto.BoardDTO;
import kanbanv2.example.api.entity.Board;
import kanbanv2.example.api.entity.ColumnStatus;
import kanbanv2.example.api.entity.Subtask;
import kanbanv2.example.api.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {


    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    public List<BoardDTO> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(BoardMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<BoardDTO> getBoardById(long id) {
        return boardRepository.findById(id).map(BoardMapper::toDTO);
    }

    @Transactional
    public BoardDTO saveBoard(Board board) {
        if (board.getColumnStatus() != null) {
            board.getColumnStatus().forEach(column -> column.setBoard(board));
        }

        Board boardSaved = boardRepository.save(board);
        return BoardMapper.toDTO(boardSaved);
    }


    @Transactional
    public BoardDTO updateBoard(BoardDTO boardDTO, long id) {

        System.out.println(boardDTO.toString());
        Board existingBoardOpt = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Board not found"));;

        existingBoardOpt.setName(boardDTO.getName());

        existingBoardOpt.getColumnStatus().clear();

        if (boardDTO.getColumnStatus() != null) {
            boardDTO.getColumnStatus().forEach(columnDTO -> {
                ColumnStatus columnStatus = BoardMapper.mapColumnToEntity(columnDTO);
                columnStatus.setBoard(existingBoardOpt);
                existingBoardOpt.getColumnStatus().add(columnStatus);
            });
        }

        System.out.println(existingBoardOpt.toString());

        Board updatedBoard = boardRepository.save(existingBoardOpt);
        return BoardMapper.toDTO(updatedBoard);
    }

    @Transactional
    public void deleteBoard(long id) {
        boardRepository.deleteById(id);
    }

}
