package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.*;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources.ResourceManager;

import java.util.HashMap;
import java.util.Map;

public class World implements GameComponent {
    private final GameObjectManager gameObjectManager;
    private final InputManager inputManager;
    private PhysicsEngine physicsEngine;
    private Map<Integer,Player> players;
    private PlayingField playingField;
    //private TiledMap map;

    public World() {
        players = new HashMap<Integer, Player>();
        gameObjectManager = new GameObjectManager(this);
        inputManager = new InputManager(this);
        playingField = new PlayingField();

        physicsEngine = new PhysicsEngine(playingField);
    }

    public Player getPlayer(int playerID) {
        return players.get(playerID);
    }

    public void spawn(GameObject obj) {
        gameObjectManager.spawnObject(obj);
    }

    public void despawn(GameObject obj) {
        gameObjectManager.despawnObject(obj);
    }

    public void spawnNewPlayer(Color playerColor) {
        Player player = new Player(this);
        player.setPosition(new Vector2f(20+20*players.size(),100));
        player.setPlayerColor(playerColor);
        players.put(players.size()+1, player);
        spawn(player);
    }

    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameObjectManager.init(gameContainer);
        inputManager.init(gameContainer);

        spawnNewPlayer(Color.orange);
        spawnNewPlayer(Color.magenta);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        gameObjectManager.update(gameContainer,i);
        inputManager.update(gameContainer, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.drawString("Kasfeq",0,0);
        playingField.render(0,0);
        gameObjectManager.render(gameContainer, graphics);
    }

    @Override
    public void dispose() throws Exception {
        gameObjectManager.dispose();
        inputManager.dispose();
    }
}
