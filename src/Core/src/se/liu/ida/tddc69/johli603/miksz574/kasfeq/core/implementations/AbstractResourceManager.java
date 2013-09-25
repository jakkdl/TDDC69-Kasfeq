package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.ResourceManager;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractResourceManager implements ResourceManager {
    private Map<Class,ResourceLoader> resourceLoaders;

    AbstractResourceManager() {
        resourceLoaders = new HashMap<Class, ResourceLoader>();
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
     * \brief Loads a resource of the specified type from a specified file
     *
     * @param filename     The filename of the resource to load
     * @param <LoadedType> The type of the resource to load
     * @return The loaded object returned by a ResourceLoader
     */
    @Override
    public <LoadedType> LoadedType loadResource(Class resourceClass, String filename) {
        return (LoadedType)resourceLoaders.get(resourceClass).loadResource(filename);
    }

    /**
     * \brief Destroys the component
     */
    @Override
    public void destroy() {
    }
}
