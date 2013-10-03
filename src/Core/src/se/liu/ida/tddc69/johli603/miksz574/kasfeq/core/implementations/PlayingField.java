package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import java.util.EnumMap;
import java.util.Map;

public class PlayingField {
    MapBlock[][] mapBlocks; //X, Y ; 0,0 in top left
    private Map<ReadXmlFile.Options,Object> optionsDict;
    int width;
    int height;

    PlayingField(int width, int height) {
        this.width = width;
        this.height = height;
        mapBlocks = new MapBlock[width][height];
    }

    public void setBlock(Point point, MapBlock mapBlock) {
        mapBlocks[point.getX()][point.getY()] = mapBlock;
        optionsDict = new EnumMap<ReadXmlFile.Options, Object>(ReadXmlFile.Options.class);
    }

    public MapBlock getBlock(Point point) {
        return mapBlocks[point.getX()][point.getY()];
    }

    public MapBlock getPixel(Point point) {
        int pixelSize = (Integer)optionsDict.get(ReadXmlFile.Options.PIXELSPERBLOCK);
        return mapBlocks[point.getX()][point.getY()];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Object getOption(ReadXmlFile.Options option) {
        if (optionsDict.containsKey(option)) {
            return optionsDict.get(option);
        }
        else {
            return null; //throw
        }
    }

    public void setOption(ReadXmlFile.Options option, Object value) {
        if (!optionsDict.containsKey(option)) {
            optionsDict.put(option, value);
        }
        //else throw
    }

}
