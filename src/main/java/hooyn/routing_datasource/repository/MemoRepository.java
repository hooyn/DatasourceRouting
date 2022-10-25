package hooyn.routing_datasource.repository;

import hooyn.routing_datasource.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
