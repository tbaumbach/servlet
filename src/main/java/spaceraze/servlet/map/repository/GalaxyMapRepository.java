package spaceraze.servlet.map.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spaceraze.map.GalaxyMap;
import spaceraze.map.MapStatus;

import java.util.List;

public interface GalaxyMapRepository extends JpaRepository<GalaxyMap, Long> {

    List<GalaxyMap> findAllByStatus(MapStatus status);

    List<GalaxyMap> findAllByAuthor(String author);

    GalaxyMap findGalaxyMapByUuidAndStatus(String key, MapStatus status);

    GalaxyMap findGalaxyMapByUuidAndVersionId(String name, long version);

    GalaxyMap findGalaxyMapByName(String name);
}
