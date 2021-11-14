package spaceraze.servlet.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spaceraze.servlet.repositories.MapRepository;
import spaceraze.world.Map;
import spaceraze.world.MapStatus;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class MapService {
    private final MapRepository mapRepository;

    public List<Map> getAllPublishedMaps() {
        return mapRepository.findAllByStatus(MapStatus.PUBLISHED);
    }

    public List<Map> getAllMapsCreatedByUser(String author) {
        return mapRepository.findAllByAuthor(author);
    }

    public Map getMapByKey(String key) {
        return mapRepository.findMapByKey(key);
    }

    public Map getMapByNameAndVersion(String name, int version) {
        return mapRepository.findMapByNameAndVersionId(name, version);
    }

    public Map saveMap(Map map) {
        if(map.getKey() == null){
            map.setKey(UUID.randomUUID().toString());
        }else{
            Map existingMap = getMapByKey(map.getKey());
            if(existingMap == null){
                throw new RuntimeException("No map exists with key: " + map.getKey());
            }else if(MapStatus.DRAFT == map.getStatus()){
                //Update
                map.setId(existingMap.getId());
            }else if(MapStatus.PUBLISHED == map.getStatus()){
                if(existingMap.getStatus() == MapStatus.PUBLISHED && existingMap.getVersionId() == map.getVersionId()){
                    throw new RuntimeException("This map is already published with the version number: " + map.getVersionId());
                }
                // Save the published map with a new key and change status on existing.
                map.setKey(UUID.randomUUID().toString());
                mapRepository.saveAndFlush(map);

                existingMap.setStatus(MapStatus.REPLACED);
                mapRepository.saveAndFlush(existingMap);
            }
        }
        return mapRepository.saveAndFlush(map);
    }
}
