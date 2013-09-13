package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class RenderingEngine implements Module {
    /**
     * \brief Can this module be disabled
     */
    @Override
    public Boolean canBeDisabled() {
        return false;
    }

    /**
     * \brief Returns the human friendly name of the module
     */
    @Override
    public String getModuleName() {
        return "Rendering Engine";
    }

    /**
     * \brief Returns a description for the module
     */
    @Override
    public String getModuleDescription() {
        return "Handles the OpenGL rendering for the game";
    }

    /**
     * \brief Called when the module is initialized into the system
     */
    @Override
    public void initialize() {
        try {
            Display.setDisplayMode(new DisplayMode(800,600));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * \brief Called by the ModuleManager every update cycle
     */
    @Override
    public void update() {
        if(!Display.isCloseRequested()) {
            // Render Here
            Display.update();
        }
    }

    /**
     * \brief Called when the module is about to be disposed
     */
    @Override
    public void dispose() {
        Display.destroy();
    }
}
