package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import java.util.EnumMap;
import java.util.Map;
import org.newdawn.slick.tiled.TiledMap;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources.ResourceManager;

public class PlayingField {
    public enum Options {
        GRAVITY,
        PIXELSPERBLOCK,
        MAPNAME,
        MAPTYPE,
        AIMSPEED,
        MOVEFORCE
    }
    private Map<Options,Object> optionsDict;
    private TiledMap map;

    public PlayingField() {
        optionsDict = new EnumMap<Options, Object>(Options.class);
        try {
            map = ResourceManager.INSTANCE.loadResource(TiledMap.class, "untitled.tmx");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public TiledMap getMap() {
        return map;
    }

    public void render(int x, int y) {
        map.render(x, y);
    }

    public int getLayerIndex(String name) {
        return map.getLayerIndex(name);
    }

    public int getTileWidth() {
        return map.getTileWidth();
    }

    public int getTileHeight() {
        return map.getTileHeight();
    }

    public int getTileId(int tileX, int tileY, int layerID) {
        return map.getTileId(tileX, tileY, layerID);
    }

    public int getWidth() {
        return map.getWidth();
    }

    public int getHeight() {
        return map.getHeight();
    }

    public int getOptionInt(Options option) throws NoSuchFieldException,ClassCastException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof Integer) {
                return (Integer) optionsDict.get(option);
            }
            throw new ClassCastException("Expected int.");
        }
        throw new NoSuchFieldException("No such option.");
    }

    public float getOptionFloat(Options option) throws NoSuchFieldException,ClassCastException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof Float) {
                return (Float) optionsDict.get(option);
            }
            throw new ClassCastException("Expected float.");
        }
        throw new NoSuchFieldException("No such option.");
    }

    public String getOptionString(Options option) throws NoSuchFieldException,ClassCastException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof String) {
                return (String) optionsDict.get(option);
            }
            throw new ClassCastException("Expected String.");
        }
        throw new NoSuchFieldException("No such option.");
    }

    public Boolean getOptionBoolean(Options option) throws NoSuchFieldException,ClassCastException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof String) {
                return (Boolean) optionsDict.get(option);
            }
            throw new ClassCastException("Expected Boolean.");
        }
        throw new NoSuchFieldException("No such option.");
    }

    public void setOption(Options option, Object value) {
        optionsDict.put(option, value); //overwrites old values with same key
    }

    public static Object getDefault(Options option) {
        switch (option) {
            case GRAVITY:
                return 20;
            case PIXELSPERBLOCK:
                return 32;
            case MAPNAME:
                return "defaultMapName";
            case MAPTYPE:
                return "text";
            case AIMSPEED:
                return Math.PI/120;
            case MOVEFORCE:
                return 0.1f;
        }
        return null; //throw exception
    }
}
