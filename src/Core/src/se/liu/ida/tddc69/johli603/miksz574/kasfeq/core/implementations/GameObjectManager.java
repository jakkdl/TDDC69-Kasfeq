package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.GameObject;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameObjectManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class GameObjectManager {
    private List<GameObject> gameObjects;

    protected GameObjectManager() {
        gameObjects = new ArrayList<GameObject>();
    }

    /**
     * \brief Initializes the component
     */
    public void initialize() {
    }

    /**
     * \brief Updates the component
     */
    public void update() {
    }

    /**
     * \brief Destroys the component
     */
    public void destroy() {
        Iterator<GameObject> iterator = gameObjects.iterator();
        while(iterator.hasNext()) {
            GameObject obj = iterator.next();
            obj.dispose();

            iterator.remove();
        }
    }
}
