package spaceraze.servlet.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spaceraze.servlet.services.MapService;
import spaceraze.servlet.services.OldMapService;
import spaceraze.world.Map;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/maps")
public class MapController {

    private final MapService mapService;
    private final OldMapService oldMapService;

    @GetMapping(value = "/")
    //@RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Map> getMaps(){
        List<Map> allPublishedMaps = mapService.getAllPublishedMaps();
        if(allPublishedMaps.isEmpty()){
            oldMapService.createMapsFromOldFile();
            allPublishedMaps = mapService.getAllPublishedMaps();
        }
        return allPublishedMaps;
    }

    /*
    @GetMapping
    @RequestMapping("/{playerId}")
    public Map getPlayerMap(@PathVariable String playerId){

    }
    */

    @GetMapping("/{key}/{version}")
    //@RequestMapping("/{key}/{version}")
    public Map getMapByKey(@PathVariable String key){
        return mapService.getMapByKey(key);

    }

    @GetMapping("/{name}/{version}")
    //@RequestMapping("/{name}/{version}")
    public Map getMapByNameAndVersion(@PathVariable String name, @PathVariable int version){
        return mapService.getMapByNameAndVersion(name, version);

    }

    @PostMapping(value = "/")
    //@RequestMapping(value = "/", method = RequestMethod.POST)
    public Map saveMap(Map map){
        return mapService.saveMap(map);
    }
}
