package kanbanv2.example.api.dao;

import kanbanv2.example.api.entity.ColumnStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnStatusRepository extends JpaRepository<ColumnStatus, Long> {
}
