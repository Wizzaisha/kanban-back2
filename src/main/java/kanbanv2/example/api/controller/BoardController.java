package kanbanv2.example.api.controller;

import kanbanv2.example.api.dto.BoardDTO;
import kanbanv2.example.api.entity.Board;
import kanbanv2.example.api.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;


    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        return ResponseEntity.ok(boardService.getAllBoards()) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable Long id) {
        Optional<BoardDTO> boardDTO = boardService.getBoardById(id);
        return boardDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BoardDTO> saveBoard(@RequestBody Board board) {
        return ResponseEntity.ok(boardService.saveBoard(board));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Long id, @RequestBody BoardDTO boardDTO) {
        System.out.println(boardDTO.toString());
        Optional<BoardDTO> existingBoard = boardService.getBoardById(id);

        if (existingBoard.isPresent()) {
            return ResponseEntity.ok(boardService.updateBoard(boardDTO, id));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        Optional<BoardDTO> existingBoard = boardService.getBoardById(id);
        if (existingBoard.isPresent()) {
            boardService.deleteBoard(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
