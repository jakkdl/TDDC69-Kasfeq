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

import java.util.ArrayList;
import java.util.List;

public class World implements GameComponent {
    private final GameObjectManager gameObjectManager;
    private final InputManager inputManager;
    //private MapComponent mapComponent;
    private PhysicsEngine physicsEngine;
    private List<Player> players;
    private TiledMap map;

    public World() {
        players = new ArrayList<Player>();
        gameObjectManager = new GameObjectManager(this);
        inputManager = new InputManager(this);
        //mapComponent = null;

        /*try {
            mapComponent = new MapComponent((PlayingField)ResourceManager.INSTANCE.loadResource(PlayingField.class, "textMap.xml"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }*/

        try {
            map = ResourceManager.INSTANCE.loadResource(TiledMap.class, "untitled.tmx");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        physicsEngine = new PhysicsEngine(map);
    }

    public Player getPlayer(int playerID) {
        return players.get(playerID-1);
    }

    public void spawn(GameObject obj) {
        gameObjectManager.spawnObject(obj);
    }

    public void despawn(GameObject obj) {
        gameObjectManager.despawnObject(obj);
    }

    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameObjectManager.init(gameContainer);
//        mapComponent.init(gameContainer);
        inputManager.init(gameContainer);

        Player player1 = new Player(this);
        player1.setPosition(new Vector2f(20,100));
        player1.setPlayerColor(Color.orange);
        gameObjectManager.spawnObject(player1);
        players.add(player1);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        gameObjectManager.update(gameContainer,i);
        //mapComponent.update(gameContainer, i);
        inputManager.update(gameContainer, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.drawString("Kasfeq",0,0);
        //mapComponent.render(gameContainer, graphics);
        map.render(0,0);
        gameObjectManager.render(gameContainer, graphics);
    }

    @Override
    public void dispose() throws Exception {
        gameObjectManager.dispose();
    }
}
