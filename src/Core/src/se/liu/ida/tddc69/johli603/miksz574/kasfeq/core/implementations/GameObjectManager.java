package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameObjectManager implements GameComponent{
    private final List<GameObject> gameObjects;
    private final List<GameObject> despawnedGameObjects;
    private final World world;

    public GameObjectManager(World world) {
        gameObjects = new ArrayList<GameObject>();
        despawnedGameObjects = new ArrayList<GameObject>();
        this.world = world;
    }

    public void spawnObject(GameObject obj) {
        gameObjects.add(obj);
    }

    public void despawnObject(GameObject obj) {
        despawnedGameObjects.add(obj);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        world.getPhysicsEngine().dumbCollisions(gameObjects, i);
        for(GameObject obj : gameObjects) {
            world.getPhysicsEngine().update(obj, i);
            obj.update(gameContainer, i);
        }
        for(GameObject obj : despawnedGameObjects) {
            System.out.println("removed");
            gameObjects.remove(obj);
        }
        despawnedGameObjects.clear();
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        for(GameObject obj : gameObjects) {
            obj.render(gameContainer, graphics);
        }
    }

    @Override
    public void dispose() throws Exception {
        Iterator<GameObject> iterator = gameObjects.iterator();
        while(iterator.hasNext()) {
            GameObject obj = iterator.next();
            obj.dispose();

            iterator.remove();
        }
    }
}
