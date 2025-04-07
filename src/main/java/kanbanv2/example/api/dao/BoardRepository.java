package kanbanv2.example.api.dao;

import kanbanv2.example.api.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
