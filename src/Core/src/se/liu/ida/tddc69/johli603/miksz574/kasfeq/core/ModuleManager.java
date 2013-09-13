package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/** \enum ModuleManager
 *  \brief ModuleManager manages all the modules in the game
 *
 *  A module is a class that inherits from the Module interface
 */
enum ModuleManager {
    /** \brief The singleton instance of the ModuleManager class */
    INSTANCE;

    /** \brief Stores all the enabled modules */
    private final List<Module> loadedModules;
    /** \brief The ServiceLoader class is used to instantiate all the modules */
    private final ServiceLoader<Module> serviceLoader;

    private ModuleManager() {
        // We add the 'plugins' directory to the classpath
        try {
            ClasspathUtils.addDirToClasspath(new File("plugins"));
        }
        catch(IOException e) {
            // We ignore the error, since this directory is optional
        }

        loadedModules = new ArrayList<Module>();
        serviceLoader = ServiceLoader.load(Module.class);
    }

    /** \brief Loads all the internal modules(Not from external jar files) */
    private void loadInternalModules() {
        Module module;

        module = new GameObjectManager();
        loadedModules.add(module);
        module.initialize();

        module = new RenderingEngine();
        loadedModules.add(module);
        module.initialize();
    }

    /** \brief Loads all the enabled modules */
    void loadModules() {
        serviceLoader.reload();

        loadInternalModules();

        for(Module module : serviceLoader) {
            // TODO: Check if module is enabled
            module.initialize();
            loadedModules.add(module);
        }
    }

    /** \brief Update functions for all the enabled modules */
    void update() {
        for(Module module : loadedModules) {
            module.update();
        }
    }

    /** \brief Unloads all the loaded modules */
    void unloadModules() {
        for(Module module : loadedModules) {
            module.dispose();
        }

        loadedModules.clear();
    }
}
