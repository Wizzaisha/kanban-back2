package kanbanv2.example.api.dao;

import kanbanv2.example.api.entity.ColumnStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ColumnStatusRepository extends JpaRepository<ColumnStatus, Long> {

    List<ColumnStatus> findByBoardId(Long boardId);

}
