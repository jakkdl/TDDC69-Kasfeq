package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core;

import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private Map<Class,ResourceLoader> resourceLoaders;

    ResourceManager() {
        resourceLoaders = new HashMap<Class, ResourceLoader>();
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
     * \brief Loads a resource of the specified type from a specified file
     *
     * @param filename     The filename of the resource to load
     * @param <LoadedType> The type of the resource to load
     * @return The loaded object returned by a ResourceLoader
     */
    public <LoadedType> LoadedType loadResource(Class resourceClass, String filename) {
        return (LoadedType)resourceLoaders.get(resourceClass).loadResource(filename);
    }

    /**
     * \brief Destroys the component
     */
    public void destroy() {
    }
}
