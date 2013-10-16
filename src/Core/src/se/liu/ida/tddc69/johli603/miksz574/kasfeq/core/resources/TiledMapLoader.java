package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * \class TiledMapLoader
 * \brief Resource loader used for loading a "Tiled" map
 */
public class TiledMapLoader implements ResourceLoader<TiledMap> {
    /**
     * \brief Loads a resource from the resource directory
     * @param filename The filename of the resource to be loaded
     * @return Returns a object representing the loaded resource
     * @throws Exception Thrown if something failed
     */
    @Override
    public TiledMap loadResource(String filename) throws FileNotFoundException, IOException, SlickException {
        TiledMap loadedMap;

        FileInputStream fileInputStream = new FileInputStream(new File(filename));
        try {
            loadedMap = new TiledMap(fileInputStream, "resources");
        }
        finally {
            fileInputStream.close();
        }

        return loadedMap;
    }
}

