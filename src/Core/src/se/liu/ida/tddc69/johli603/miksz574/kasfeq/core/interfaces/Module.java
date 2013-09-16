package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces;

/** \interface Module
 *  \brief The Module interface is the interface that all the modules of the game have to implement
 */
public interface Module {
    /** \brief Can this module be disabled */
    public Boolean canBeDisabled();
    /** \brief Returns the human friendly name of the module */
    public String getModuleName();
    /** \brief Returns a description for the module */
    public String getModuleDescription();

    /** \brief Called when the module is initialized into the system */
    public void initialize();
    /** \brief Called by the ModuleManager every update cycle */
    public void update();
    /** \brief Called when the module is about to be disposed */
    public void dispose();
}
