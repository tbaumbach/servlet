package spaceraze.servlet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spaceraze.world.Map;
import spaceraze.world.MapStatus;

import java.util.List;

public interface MapRepository extends JpaRepository<Map, Long> {

    List<Map> findAllByStatus(MapStatus status);

    List<Map> findAllByAuthor(String author);

    Map findMapByKey(String key);

    Map findMapByNameAndVersionId(String name, long version);
}
