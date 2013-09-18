package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces;

/** \interface ModuleHandler
 *  \brief The ModuleHandler interface is the interface that all the modules of the game have to implement
 */
public interface ModuleHandler {
    /** \brief Returns the human friendly name of the module */
    public String getModuleName();
    /** \brief Returns a description for the module */
    public String getModuleDescription();

    public ResourceManager getResourceManager();
    public RenderingEngine getRenderingEngine();
    public PhysicsEngine getPhysicsEngine();
    public InputManager getInputManager();
    public GameObjectManager getGameObjectManager();
}
