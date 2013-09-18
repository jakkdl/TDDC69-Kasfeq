package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.lwjgl.LWJGLException;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.RenderingEngine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class OpenGLRenderingEngine implements RenderingEngine {
    public static final int RESOLUTION_WIDTH = 800;
    public static final int RESOLUTION_HEIGHT = 600;
    public static final int MAX_FPS = 60;

    public OpenGLRenderingEngine() {
    }

    /**
     * \brief Initializes the component
     */
    @Override
    public void initialize() {
        try {
            Display.setDisplayMode(new DisplayMode(RESOLUTION_WIDTH, RESOLUTION_HEIGHT));
            Display.create();
        } catch(LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * \brief Updates the component
     */
    @Override
    public void update() {
        Display.update();
        Display.sync(MAX_FPS);
    }

    /**
     * \brief Destroys the component
     */
    @Override
    public void destroy() {
        Display.destroy();
    }
}
