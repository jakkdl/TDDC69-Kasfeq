package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.ResourceLoaders;

import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.ResourceTypes.Texture;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.ResourceLoader;

import java.io.IOException;

public class TextureLoader implements ResourceLoader<Texture> {
    @Override
    public Texture loadResource(String filename) throws IOException {
        return new Texture(filename);
    }
}
