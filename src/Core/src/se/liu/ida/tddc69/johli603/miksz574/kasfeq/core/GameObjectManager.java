package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core;

import java.util.ArrayList;
import java.util.List;

/** \enum GameObjectManager
 * \brief Handles all of the game objects in the game
 */
public enum GameObjectManager implements Module{
    INSTANCE;

    private List<GameObject> objectList;

    private GameObjectManager() {
    }

    /**
     * \brief Can this module be disabled
     */
    @Override
    public Boolean canBeDisabled() {
        return false;
    }

    /**
     * \brief Returns the human friendly name of the module
     */
    @Override
    public String getModuleName() {
        return "Game Object Manager";
    }

    /**
     * \brief Returns a description for the module
     */
    @Override
    public String getModuleDescription() {
        return "Handles all the game objects in the game";
    }

    /**
     * \brief Called when the module is initialized into the system
     */
    public void initialize() {
        objectList = new ArrayList<GameObject>();
    }

    /**
     * \brief Called by the ModuleManager every update cycle
     */
    public void update() {
    }

    /**
     * \brief Called when the module is about to be disposed
     */
    public void dispose() {
        for(GameObject object : objectList) {
            object.dispose();
        }

        objectList.clear();
        objectList = null;
    }
}
