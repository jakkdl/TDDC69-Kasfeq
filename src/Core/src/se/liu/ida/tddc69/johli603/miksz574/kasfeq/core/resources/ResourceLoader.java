package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources;

import org.newdawn.slick.SlickException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * \interface ResourceLoader
 * @param <T></T> The type representing the resource to be loaded
 */
public interface ResourceLoader<T> {
    /**
     * \brief Loads a resource from the resource directory
     * @param filename The filename of the resource to be loaded
     * @return Returns a object representing the loaded resource
     */
    public T loadResource(String filename) throws FileNotFoundException, IOException, SlickException;
}
