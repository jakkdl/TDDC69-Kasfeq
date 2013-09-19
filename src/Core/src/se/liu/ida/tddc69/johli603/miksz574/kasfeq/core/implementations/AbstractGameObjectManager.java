package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.GameObject;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameObjectManager;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGameObjectManager implements GameObjectManager {
    private List<GameObject> gameObjects;

    protected AbstractGameObjectManager() {
        gameObjects = new ArrayList<GameObject>();
    }

    /**
     * \brief Initializes the component
     */
    @Override
    public void initialize() {
    }

    /**
     * \brief Updates the component
     */
    @Override
    public void update() {
    }

    /**
     * \brief Destroys the component
     */
    @Override
    public void destroy() {
        for(GameObject object : gameObjects) {
            object.dispose();
        }

        gameObjects.clear();
    }
}
