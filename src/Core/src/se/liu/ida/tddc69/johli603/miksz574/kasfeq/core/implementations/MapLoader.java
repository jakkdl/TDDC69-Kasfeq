package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources.ResourceLoader;

public class MapLoader implements ResourceLoader<PlayingField>{
    @Override
    public PlayingField loadResource(String filename) throws Exception {
        return ReadXmlFile.loadResource(filename);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
