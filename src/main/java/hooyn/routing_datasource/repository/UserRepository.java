package hooyn.routing_datasource.repository;

import hooyn.routing_datasource.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
