package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.*;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/** \enum ModuleManager
 *  \brief Handles all the modules in the game
 *
 *  A module is comprised of a class that implements the ModuleHandler interface and individual components that
 *  implement the ModuleComponent interface
 */
enum ModuleManager {
    /** \brief The singleton instance of the ModuleManager class */
    INSTANCE;

    /** \brief Stores all the enabled modules */
    private final List<ModuleHandler> loadedModules;
    /** \brief The ServiceLoader class is used to instantiate all the modules */
    private final ServiceLoader<ModuleHandler> serviceLoader;

    private ResourceManager resourceManager;
    private RenderingEngine renderingEngine;
    private PhysicsEngine physicsEngine;
    private InputManager inputManager;
    private GameObjectManager gameObjectManager;

    /** \brief Returns an instance of the currently loaded ResourceManager */
    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    /** \brief Returns an instance of the currently loaded RenderingEngine */
    public RenderingEngine getRenderingEngine() {
        return renderingEngine;
    }

    /** \brief Returns an instance of the currently loaded PhysicsEngine */
    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }

    /** \brief Returns an instance of the currently loaded InputManager */
    public InputManager getInputManager() {
        return inputManager;
    }

    /** \brief Returns an instance of the currently loaded GameObjectManager */
    public GameObjectManager getGameObjectManager() {
        return gameObjectManager;
    }

    private ModuleManager() {
        // We add the 'plugins' directory to the classpath
        try {
            ClasspathUtils.addDirToClasspath(new File("plugins"));
        }
        catch(IOException e) {
            // We ignore the error, since this directory is optional
        }

        loadedModules = new ArrayList<ModuleHandler>();
        serviceLoader = ServiceLoader.load(ModuleHandler.class);
    }

    /** \brief Loads all the internal modules(Not from external jar files) */
    private void loadInternalModules() {
        resourceManager = new BasicResourceManager();
        renderingEngine = new OpenGLRenderingEngine();
        physicsEngine = new BasicPhysicsEngine();
        inputManager = new BasicInputManager();
        gameObjectManager = new BasicGameObjectManager();
    }

    /** \brief Loads all the enabled modules */
    void loadModules() {
        serviceLoader.reload();

        loadInternalModules();

        for(ModuleHandler moduleHandler : serviceLoader) {
            // TODO: Check if module is enabled
            // TODO: Handle module components
            loadedModules.add(moduleHandler);
        }
    }

    /** \brief Update functions for all the enabled modules */
    void update() {
        for(ModuleHandler moduleHandler : loadedModules) {
        }
    }

    /** \brief Unloads all the loaded modules */
    void unloadModules() {
        for(ModuleHandler moduleHandler : loadedModules) {
        }

        loadedModules.clear();
    }
}
