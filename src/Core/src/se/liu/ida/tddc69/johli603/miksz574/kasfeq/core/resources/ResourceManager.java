package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources;

import org.newdawn.slick.tiled.TiledMap;

import java.util.HashMap;
import java.util.Map;

/**
 * \enum ResourceManager
 * \brief The ResourceManager class is responsible for managing all the resources for the game
 */
public enum ResourceManager {
    /**
     * \brief The Singleton instance of the ResourceManager
     */
    INSTANCE;
    private final Map<Class, ResourceLoader> resourceLoaders;
    private final Map<String, Object> cachedResources;

    ResourceManager() {
        resourceLoaders = new HashMap<Class, ResourceLoader>();
        resourceLoaders.put(TiledMap.class, new TiledMapLoader());
        cachedResources = new HashMap<String, Object>();
    }

    /**
     * \brief Loads a resource of the specified type from a specified file
     *
     * @param filename     The filename of the resource to load
     * @param <LoadedType> The type of the resource to load
     * @return The loaded object returned by a ResourceLoader
     */
    public <LoadedType> LoadedType loadResource(Class resourceClass, String filename) throws Exception {
        Object resource = cachedResources.get(filename);

        if (resource == null) {
            resource = resourceLoaders.get(resourceClass).loadResource("resources/" + filename);
            cachedResources.put(filename, resource);
        }

        return (LoadedType) resource;
    }
}
