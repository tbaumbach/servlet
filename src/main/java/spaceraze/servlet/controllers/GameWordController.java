package spaceraze.servlet.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spaceraze.servlet.repositories.GameWorldRepository;
import spaceraze.servlet.repositories.SpaceshipTypeRepository;
import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.world.GameWorld;
import spaceraze.world.SpaceshipType;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/gamewords")
public class GameWordController {

    private GameWorldRepository gameWorldRepository;
    private SpaceshipTypeRepository spaceshipTypeRepository;

    @GetMapping("/{name}/{version}")
    //@RequestMapping("/{name}/{version}")
    public GameWorld gameWorld(@PathVariable String name, @PathVariable int version){

        //TODO only run this the fista time to create the Gameworld in the database.
        gameWorldRepository.deleteAll();
        //thelastgreatwar
        GameWorld gameWorld = GameWorldHandler.getGameWorld("thelastgreatwar");
        gameWorldRepository.saveAndFlush(gameWorld);

        //starts the read(get)
        //TODO add logik to user in variable name and version
        GameWorld gameWorld2 = gameWorldRepository.findAll().get(gameWorldRepository.findAll().size() -1);

        return gameWorld2;
    }
/*
    @PutMapping()
    public GameWorld gameWorld(){}

 */
}
