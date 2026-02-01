package spaceraze.servlet.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spaceraze.game.Galaxy;

public interface GameRepository extends JpaRepository<Galaxy, Long> {
}
