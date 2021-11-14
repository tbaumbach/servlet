package spaceraze.servlet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spaceraze.world.GameWorld;

public interface GameWorldRepository extends JpaRepository<GameWorld, Long> {
}
