package spaceraze.servlet.world.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spaceraze.world.SpaceshipType;

public interface SpaceshipTypeRepository extends JpaRepository<SpaceshipType, Long> {
}
