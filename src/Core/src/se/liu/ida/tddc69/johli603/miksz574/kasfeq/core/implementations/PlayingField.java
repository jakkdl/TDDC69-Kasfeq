package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import java.util.EnumMap;
import java.util.Map;
import org.newdawn.slick.tiled.TiledMap;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources.ResourceManager;

public class PlayingField {
    public enum Options {
        MAPNAME,
        GRAVITY,
        FRICTION,
        AIMSPEED,
        PLAYERMOVEFORCE,
        PLAYERMASS,
        PLAYERWIDTH,
        PLAYERHEIGHT,
        PLAYERJUMPFORCE,
        BULLETMASS,
        BULLETWIDTH,
        BULLETHEIGHT
    }
    private Map<Options,Object> optionsDict;
    private TiledMap map;

    public PlayingField() throws NoSuchFieldException {
        optionsDict = new EnumMap<Options, Object>(Options.class);
        try {
            map = ResourceManager.INSTANCE.loadResource(TiledMap.class, "untitled.tmx");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        setOptions();
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

    public MapBlock.States getPixel(Point point) {
        int layerID = map.getLayerIndex("Tile Layer 1");
        int tileWidth = map.getTileWidth();
        int tileHeight = map.getTileHeight();

        int tileX = (int)Math.floor((double)point.getX()/(double)tileWidth);
        int tileY = (int)Math.floor((double)point.getY()/(double)tileHeight);
        int id = map.getTileId(tileX, tileY, layerID);

        if(id == 2) {
            return MapBlock.States.EMPTY;
        }
        else if(id == 3) {
            return MapBlock.States.DESTRUCTABLE;
        }
        else {
            return MapBlock.States.SOLID;
        }
    }

    public int getOptionInt(Options option) throws NoSuchFieldException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof Integer) {
                return (Integer) optionsDict.get(option);
            }
            else {
                return (Integer) getDefault(option);
            }
        }
        throw new NoSuchFieldException("No such option.");
    }

    public double getOptionDouble(Options option) throws NoSuchFieldException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof Double) {
                return (Double) optionsDict.get(option);
            }
            else {
                return (Double) getDefault(option);
            }
        }
        else {
            throw new NoSuchFieldException("No such option.");
        }
    }

    public String getOptionString(Options option) throws NoSuchFieldException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof String) {
                return (String) optionsDict.get(option);
            }
            else {
                return (String) getDefault(option);
            }
        }
        throw new NoSuchFieldException("No such option.");
    }

    public Boolean getOptionBoolean(Options option) throws NoSuchFieldException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof String) {
                return (Boolean) optionsDict.get(option);
            }
            else {
                return (Boolean) getDefault(option);
            }
        }
        throw new NoSuchFieldException("No such option.");
    }

    public void setOption(Options option, Object value) {
        optionsDict.put(option, value); //overwrites old values with same key
    }

    public void setOptions() throws NoSuchFieldException {
        for (Options option : Options.values()) {
            String mapProperty = map.getMapProperty(option.toString(), null);
            Object def = getDefault(option);
            if (mapProperty == null) {
                setOption(option, getDefault(option));
            }
            else {
                try {
                    if (getDefault(option) instanceof Integer) {
                        setOption(option, Integer.parseInt(mapProperty));
                    }
                    else if (getDefault(option) instanceof Double) {
                        setOption(option, Double.parseDouble(mapProperty));
                    }
                    else if (getDefault(option) instanceof Boolean) {
                        setOption(option, Boolean.parseBoolean(mapProperty));
                    }
                    else if (getDefault(option) instanceof String) {
                        setOption(option, mapProperty);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error when loading " + option.toString() + " incorrect type");
                    setOption(option, getDefault(option));
                }
            }
           setOption(option, map.getMapProperty(option.toString(), getDefault(option).toString()));
        }
    }

    public double getAimspeed() throws NoSuchFieldException {
        return getOptionDouble(Options.AIMSPEED);
    }

    public double getGravity() throws NoSuchFieldException {
        return getOptionDouble(Options.GRAVITY);
    }

    public double getFriction() throws NoSuchFieldException {
        return getOptionDouble(Options.FRICTION);
    }

    public double getPlayerMass() throws NoSuchFieldException {
        return getOptionDouble(Options.PLAYERMASS);
    }
    public double getPlayerWidth() throws NoSuchFieldException {
        return getOptionDouble(Options.PLAYERWIDTH);
    }
    public double getPlayerHeight() throws NoSuchFieldException {
        return getOptionDouble(Options.PLAYERHEIGHT);
    }
    public double getPlayerJumpForce() throws NoSuchFieldException {
        return getOptionDouble(Options.PLAYERJUMPFORCE);
    }
    public double getPlayerMoveForce() throws NoSuchFieldException {
        return getOptionDouble(Options.PLAYERMOVEFORCE);
    }
    public double getBulletMass() throws NoSuchFieldException {
        return getOptionDouble(Options.BULLETMASS);
    }
    public double getBulletHeight() throws NoSuchFieldException {
        return getOptionDouble(Options.BULLETHEIGHT);
    }
    public double getBulletWidth() throws NoSuchFieldException {
        return getOptionDouble(Options.BULLETWIDTH);
    }


    private Object getDefault(Options option) throws NoSuchFieldException {
        switch (option) {
            case GRAVITY:
                return 0.05;
            case MAPNAME:
                return "defaultMapName";
            case AIMSPEED:
                return Math.PI / 120;
            case PLAYERMOVEFORCE:
                return 0.1;
            case PLAYERJUMPFORCE:
                return -2.0;
            case FRICTION:
                return 0.03;
            case PLAYERMASS:
                return 2.0;
            case PLAYERWIDTH:
                return 10.0;
            case PLAYERHEIGHT:
                return 20.0;
            case BULLETMASS:
                return 0.1;
            case BULLETHEIGHT:
                return 2.0;
            case BULLETWIDTH:
                return 2.0;
            default:
                throw new NoSuchFieldException("No default value");
        }
    }
}
