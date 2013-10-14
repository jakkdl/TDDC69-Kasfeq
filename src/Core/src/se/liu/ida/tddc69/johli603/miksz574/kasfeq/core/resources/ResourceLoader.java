package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources;

/**
 * \interface ResourceLoader
 * @param <LoadedType> The type representing the resource to be loaded
 */
public interface ResourceLoader<LoadedType> {
    /**
     * \brief Loads a resource from the resource directory
     * @param filename The filename of the resource to be loaded
     * @return Returns a object representing the loaded resource
     * @throws Exception Thrown if something failed
     */
    public LoadedType loadResource(String filename) throws Exception;
}
