package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources;

import org.newdawn.slick.tiled.TiledMap;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources.ResourceLoader;

import java.io.File;
import java.io.FileInputStream;

public class TiledMapLoader implements ResourceLoader<TiledMap>{
    @Override
    public TiledMap loadResource(String filename) throws Exception {
        return new TiledMap(new FileInputStream(new File(filename)),"resources");
    }
}

