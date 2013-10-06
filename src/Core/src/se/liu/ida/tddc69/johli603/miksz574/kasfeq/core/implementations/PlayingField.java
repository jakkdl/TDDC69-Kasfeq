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
        optionsDict = new EnumMap<ReadXmlFile.Options, Object>(ReadXmlFile.Options.class);
    }

    public void setBlock(Point point, MapBlock mapBlock) {
        mapBlocks[point.getX()][point.getY()] = mapBlock;
    }

    public MapBlock getBlock(Point point) {
        return mapBlocks[point.getX()][point.getY()];
    }

    public MapBlock getPixel(Point point) {
        int pixelSize = (Integer)optionsDict.get(ReadXmlFile.Options.PIXELSPERBLOCK);
        return mapBlocks[(int)Math.floor(point.getX()/pixelSize)][(int)Math.floor(point.getY()/pixelSize)];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getPixelHeight() {
        return height*(Integer)optionsDict.get(ReadXmlFile.Options.PIXELSPERBLOCK);
    }

    public int getPixelWidth() {
        return width*(Integer)optionsDict.get(ReadXmlFile.Options.PIXELSPERBLOCK);
    }

    public int getOptionInt(ReadXmlFile.Options option) throws NoSuchFieldException,ClassCastException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof Integer) {
                return (Integer) optionsDict.get(option);
            }
            throw new ClassCastException("Expected int.");
        }
        throw new NoSuchFieldException("No such option.");
    }

    public float getOptionFloat(ReadXmlFile.Options option) throws NoSuchFieldException,ClassCastException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof Float) {
                return (Float) optionsDict.get(option);
            }
            throw new ClassCastException("Expected float.");
        }
        throw new NoSuchFieldException("No such option.");
    }

    public String getOptionString(ReadXmlFile.Options option) throws NoSuchFieldException,ClassCastException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof String) {
                return (String) optionsDict.get(option);
            }
            throw new ClassCastException("Expected String.");
        }
        throw new NoSuchFieldException("No such option.");
    }

    public Boolean getOptionBoolean(ReadXmlFile.Options option) throws NoSuchFieldException,ClassCastException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof String) {
                return (Boolean) optionsDict.get(option);
            }
            throw new ClassCastException("Expected Boolean.");
        }
        throw new NoSuchFieldException("No such option.");
    }

    public void setOption(ReadXmlFile.Options option, Object value) {
        optionsDict.put(option, value); //overwrites old values with same key
    }

}
