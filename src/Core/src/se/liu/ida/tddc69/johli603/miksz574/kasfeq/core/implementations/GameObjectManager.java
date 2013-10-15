package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * \class GameObjectManager
 * \brief The GameObjectManager class is responsible for managing all instances of the GameObject class
 */
public class GameObjectManager implements GameComponent {
    private final List<GameObject> gameObjects;
    private final List<GameObject> despawnedGameObjects;
    private final World world;

    /**
     * \brief GameObjectManager constructor
     *
     * @param world The world that the game object manager should manage objects for
     */
    public GameObjectManager(World world) {
        gameObjects = new ArrayList<GameObject>();
        despawnedGameObjects = new ArrayList<GameObject>();
        this.world = world;
    }

    /**
     * \brief Adds a GameObject instance to the world
     *
     * @param obj The GameObject instance to add
     */
    public void spawnObject(GameObject obj) {
        gameObjects.add(obj);
    }

    /**
     * \brief Removes a GameObject instance from the world
     *
     * @param obj The GameObject instance to remove
     */
    public void despawnObject(GameObject obj) {
        despawnedGameObjects.add(obj);
    }

    /**
     * \brief The init function is called when the game component is initialized
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     */
    @Override
    public void init(GameContainer gameContainer) {
    }

    /**
     * \brief The update function is called before every frame is rendered
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i             The time in ms since the last update call
     */
    @Override
    public void update(GameContainer gameContainer, int i) {
        world.getPhysicsEngine().dumbCollisions(gameObjects, i);
        for (GameObject obj : gameObjects) {
            world.getPhysicsEngine().updateObject(obj, i);
            obj.update(gameContainer, i);
        }
        for (GameObject obj : despawnedGameObjects) {
            System.out.println("removed");
            gameObjects.remove(obj);
        }
        despawnedGameObjects.clear();
    }

    /**
     * \brief The update function is called before every frame is rendered
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i             The time in ms since the last update call
     */
    @Override
    public void render(GameContainer gameContainer, Graphics graphics){
        for (GameObject obj : gameObjects) {
            obj.render(gameContainer, graphics);
        }
    }

    /**
     * \brief The dispose function is called before a component is destoryed
     *
     */
    @Override
    public void dispose() {
        Iterator<GameObject> iterator = gameObjects.iterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.next();
            obj.dispose();

            iterator.remove();
        }
    }
}
