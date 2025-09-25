package spaceraze.servlet.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spaceraze.map.GalaxyMap;
import spaceraze.map.MapStatus;
import spaceraze.servlet.map.repository.GalaxyMapRepository;

import java.util.List;
import java.util.UUID;

@Service
public class GalaxyMapService {
    /*
    private final GalaxyMapRepository mapRepository;

    public GalaxyMapService(GalaxyMapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }
    */

    public List<GalaxyMap> getAllPublishedMaps() {
        return null; //mapRepository.findAllByStatus(MapStatus.PUBLISHED);
    }

    public List<GalaxyMap> getAllMapsCreatedByUser(String author) {
        return null; //mapRepository.findAllByAuthor(author);
    }

    public GalaxyMap getMapByKey(String key) {
        return null;
     //   return mapRepository.findGalaxyMapByUuidAndStatus(key, MapStatus.PUBLISHED);
    }

    public GalaxyMap getMapByKeyAndVersion(String key, int version) {
        return null; // mapRepository.findGalaxyMapByUuidAndVersionId(key, version);
    }

    public GalaxyMap saveMap(GalaxyMap map) {
        /*
        if(map.getUuid() == null){
            map.setUuid(UUID.randomUUID().toString());
            map.setVersionId(1);
            if (mapRepository.findGalaxyMapByName(map.getName()) != null) {
                throw new RuntimeException("A map with the name " + map.getUuid() + " already exists.");
            }
            if (map.getStatus() == null) {
                map.setStatus(MapStatus.DRAFT);
            } else if (MapStatus.PUBLISHED.equals(map.getStatus())) {
                // TODO add validation on minimum of planets and connections to all planets.
                map.setStatus(MapStatus.PUBLISHED);
            }
        }else{
            GalaxyMap existingMap = getMapByKey(map.getUuid());
            if(existingMap == null){
                throw new RuntimeException("No map exists with key: " + map.getUuid());
            } else if(existingMap.getStatus() == MapStatus.PUBLISHED && existingMap.getVersionId() == map.getVersionId()){
                throw new RuntimeException("This map is already published with the version number: " + map.getVersionId());
            } else if (MapStatus.DRAFT == map.getStatus()){
                //Update
                map.setId(existingMap.getId());
            } else if(MapStatus.PUBLISHED == map.getStatus()){
                if(existingMap.getStatus() == MapStatus.PUBLISHED && existingMap.getVersionId() == map.getVersionId()){
                    throw new RuntimeException("This map is already published with the version number: " + map.getVersionId());
                }
                // Save the published map with a new version and change status on existing.
                map.setUuid(UUID.randomUUID().toString());

                if(existingMap.getStatus() == MapStatus.PUBLISHED) {
                    existingMap.setStatus(MapStatus.REPLACED);
                    GalaxyMap newDraft = new GalaxyMap(map.getAuthor() + "." + map.getName());
                    newDraft.setStatus(MapStatus.DRAFT);
                    newDraft.setConnections(map.getConnections());
                    newDraft.setPlanets(map.getPlanets());
                    newDraft.setCreatedDate(map.getCreatedDate());
                    newDraft.setChangedDate(map.getChangedDate());
                    newDraft.setDescription(map.getDescription());
                    newDraft.setFileName(map.getFileName());
                    newDraft.setVersionId(map.getVersionId() + 1);
                    mapRepository.saveAndFlush(newDraft);
                } else {
                    existingMap.setVersionId(existingMap.getVersionId() + 1);
                }
                mapRepository.saveAndFlush(existingMap);
            }
        }
        return mapRepository.saveAndFlush(map);
        */
        return null;
    }
}
