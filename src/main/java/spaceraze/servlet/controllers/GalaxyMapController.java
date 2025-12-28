package spaceraze.servlet.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spaceraze.servlet.services.GalaxyMapService;
import spaceraze.servlet.services.OldMapService;
import spaceraze.map.GalaxyMap;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/maps")
public class GalaxyMapController {

    private final GalaxyMapService mapService;
    private final OldMapService oldMapService;

    @GetMapping(value = "/")
    //@RequestMapping(value = "/", method = RequestMethod.GET)
    public List<GalaxyMap> getGalaxyMaps(){

        List<GalaxyMap> allPublishedMaps = mapService.getAllPublishedMaps();
        if(allPublishedMaps.isEmpty()){
            oldMapService.createMapsFromOldFile();
            allPublishedMaps = mapService.getAllPublishedMaps();
        }
        return allPublishedMaps;
    }

    /*
    @GetMapping
    @RequestMapping("/{playerId}")
    public Map getPlayerGalaxyMap(@PathVariable String playerId){

    }
    */

    @GetMapping("/{key}")
    public GalaxyMap getGalaxyMapByKey(@PathVariable String key){
        return mapService.getMapByKey(key);
    }

    @GetMapping("/{key}/{version}")
    public GalaxyMap getGalaxyMapByKeyAndVersion(@PathVariable String key, @PathVariable int version){
        return mapService.getMapByKeyAndVersion(key, version);
    }

    @PostMapping(value = "/")
    //@RequestMapping(value = "/", method = RequestMethod.POST)
    public GalaxyMap saveGalaxyMap(GalaxyMap map){
        return mapService.saveMap(map);
    }
}
