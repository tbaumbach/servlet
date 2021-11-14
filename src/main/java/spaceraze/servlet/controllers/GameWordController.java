package spaceraze.servlet.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        //thelastgreatwar
        GameWorld gameWorld = GameWorldHandler.getGameWorld("thelastgreatwar");

        //gameWorldRepository.save(gameWorld);
        gameWorldRepository.saveAndFlush(gameWorld);

        GameWorld gameWorld2 = gameWorldRepository.findAll().get(0);

        //spaceshipTypeRepository.save(gameWorld.getShipTypes().get(0));

        //List<SpaceshipType> all = spaceshipTypeRepository.findAll();

        return gameWorld2;
    }
}
