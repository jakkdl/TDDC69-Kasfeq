package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * \class ResourceManager
 * \brief The ResourceManager class is responsible for managing all the resources for the game
 */
public final class ResourceManager {
    private static final Map<Class<?>, ResourceLoader<?>> resourceLoaders;
    private static final Map<String, Object> cachedResources;

    static {
        resourceLoaders = new HashMap<Class<?>, ResourceLoader<?>>();
        resourceLoaders.put(TiledMap.class, new TiledMapLoader());
        cachedResources = new HashMap<String, Object>();
    }

    private ResourceManager() {
    }

    /**
     * \brief Loads a resource of the specified type from a specified file
     *
     * @param filename The filename of the resource to load
     * @param <T> The type of the resource to load
     * @return The loaded object returned by a ResourceLoader
     */
    // Inspector mistake, it is used in try-catch blocks
    @SuppressWarnings("UnusedDeclaration")
    public static <T> T loadResource(Class<T> resourceClass, String filename) throws FileNotFoundException, IOException, SlickException {
        T resource = resourceClass.cast(cachedResources.get(filename));

        if (resource == null) {
            resource = reloadResource(resourceClass, filename);
        }

        return resource;
    }

    /**
     * \brief Reloads a resource replacing the one in cache
     *
     * @param filename The filename of the resource to load
     * @param <T> The type of the resource to load
     * @return The loaded object returned by a ResourceLoader
     */
    public static <T> T reloadResource(Class<T> resourceClass, String filename) throws FileNotFoundException, IOException, SlickException {
        T resource = resourceClass.cast(resourceLoaders.get(resourceClass).loadResource("resources/" + filename));
        cachedResources.put(filename, resource);

        return resource;
    }
}
