package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core;

import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private Map<Class,ResourceLoader> resourceLoaders;
    private Map<String, Object> cachedResources;

    ResourceManager() {
        resourceLoaders = new HashMap<Class, ResourceLoader>();
        cachedResources = new HashMap<String, Object>();
    }

    /**
     * \brief Loads a resource of the specified type from a specified file
     *
     * @param filename     The filename of the resource to load
     * @param <LoadedType> The type of the resource to load
     * @return The loaded object returned by a ResourceLoader
     */
    public <LoadedType> LoadedType loadResource(Class resourceClass, String filename) {
        Object resource = cachedResources.get(filename);

        if(resource == null) {
            resource = resourceLoaders.get(resourceClass).loadResource(filename);
            cachedResources.put(filename, (LoadedType)resource);
        }

        return (LoadedType)resource;
    }
}
