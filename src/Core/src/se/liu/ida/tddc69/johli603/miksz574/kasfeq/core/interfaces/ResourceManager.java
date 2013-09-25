package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces;

/** \interface ResourceManager
 * \brief Interface used for implementing a resource manager
 */
public interface ResourceManager extends ModuleComponent {
    /**
     * \brief Loads a resource of the specified type from a specified file
     * @param filename The filename of the resource to load
     * @param <LoadedType> The type of the resource to load
     * @return The loaded object returned by a ResourceLoader
     */
    public <LoadedType> LoadedType loadResource(Class resourceClass, String filename);
}
