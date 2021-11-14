package spaceraze.servlet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spaceraze.world.GameWorld;
import spaceraze.world.SpaceshipType;

public interface SpaceshipTypeRepository extends JpaRepository<SpaceshipType, Long> {
}
