package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources.ResourceManager;

import java.util.EnumMap;
import java.util.Map;

public class PlayingField implements GameComponent {
    public class NoSuchOptionException extends RuntimeException {
        private final String msg;

        public NoSuchOptionException(String msg) {
            this.msg = msg;
        }

        @Override
        public String getMessage() {
            return msg + "\n" + super.getMessage();
        }
    }

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

    private Map<Options, Object> optionsDict;
    private TiledMap map;

    public PlayingField(String filename) throws NoSuchOptionException {
        optionsDict = new EnumMap<Options, Object>(Options.class);
        try {
            map = ResourceManager.INSTANCE.loadResource(TiledMap.class, filename);
        } catch (Exception e) {
            e.printStackTrace();
            map = null;
        }

        setOptions();
    }

    public int getWidth() {
        return map.getWidth() * map.getTileWidth();
    }

    public int getHeight() {
        return map.getHeight() * map.getTileHeight();
    }

    public MapBlock.States getPixel(Point point) {
        int layerID = map.getLayerIndex("Tile Layer 1");
        int tileWidth = map.getTileWidth();
        int tileHeight = map.getTileHeight();

        int tileX = (int) Math.floor((double) point.getX() / tileWidth);
        int tileY = (int) Math.floor((double) point.getY() / tileHeight);
        int id = map.getTileId(tileX, tileY, layerID);

        if (id == 2) {
            return MapBlock.States.EMPTY;
        } else if (id == 3) {
            return MapBlock.States.DESTRUCTABLE;
        } else {
            return MapBlock.States.SOLID;
        }
    }

    private int getOptionInt(Options option) throws NoSuchOptionException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof Integer) {
                return (Integer) optionsDict.get(option);
            } else {
                return (Integer) getDefault(option);
            }
        }
        throw new NoSuchOptionException("No such option.");
    }

    private double getOptionDouble(Options option) throws NoSuchOptionException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof Double) {
                return (Double) optionsDict.get(option);
            } else {
                return (Double) getDefault(option);
            }
        } else {
            throw new NoSuchOptionException("No such option.");
        }
    }

    private String getOptionString(Options option) throws NoSuchOptionException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof String) {
                return (String) optionsDict.get(option);
            } else {
                return (String) getDefault(option);
            }
        }
        throw new NoSuchOptionException("No such option.");
    }

    private Boolean getOptionBoolean(Options option) throws NoSuchOptionException {
        if (optionsDict.containsKey(option)) {
            Object result = optionsDict.get(option);
            if (result instanceof String) {
                return (Boolean) optionsDict.get(option);
            } else {
                return (Boolean) getDefault(option);
            }
        }
        throw new NoSuchOptionException("No such option.");
    }

    public void setOption(Options option, Object value) {
        optionsDict.put(option, value); //overwrites old values with same key
    }

    public void setOptions() throws NoSuchOptionException {
        for (Options option : Options.values()) {
            String mapProperty = map.getMapProperty(option.toString(), null);
            Object def = getDefault(option);

            if (mapProperty == null) {
                setOption(option, def);
            } else {
                try {
                    if (def instanceof Integer) {
                        setOption(option, Integer.parseInt(mapProperty));
                    } else if (def instanceof Double) {
                        setOption(option, Double.parseDouble(mapProperty));
                    } else if (def instanceof Boolean) {
                        setOption(option, Boolean.parseBoolean(mapProperty));
                    } else if (def instanceof String) {
                        setOption(option, mapProperty);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error when loading " + option.toString() + " incorrect type");
                    setOption(option, def);
                }
            }

            setOption(option, map.getMapProperty(option.toString(), def.toString()));
        }
    }

    public double getAimspeed() {
        return getOptionDouble(Options.AIMSPEED);
    }

    public double getGravity() {
        return getOptionDouble(Options.GRAVITY);
    }

    public double getFriction() {
        return getOptionDouble(Options.FRICTION);
    }

    public double getPlayerMass() {
        return getOptionDouble(Options.PLAYERMASS);
    }

    public double getPlayerWidth() {
        return getOptionDouble(Options.PLAYERWIDTH);
    }

    public double getPlayerHeight() {
        return getOptionDouble(Options.PLAYERHEIGHT);
    }

    public double getPlayerJumpForce() {
        return getOptionDouble(Options.PLAYERJUMPFORCE);
    }

    public double getPlayerMoveForce() {
        return getOptionDouble(Options.PLAYERMOVEFORCE);
    }

    public double getBulletMass() {
        return getOptionDouble(Options.BULLETMASS);
    }

    public double getBulletHeight() {
        return getOptionDouble(Options.BULLETHEIGHT);
    }

    public double getBulletWidth() {
        return getOptionDouble(Options.BULLETWIDTH);
    }

    private Object getDefault(Options option) throws NoSuchOptionException {
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
                throw new NoSuchOptionException("No default value");
        }
    }

    /**
     * \brief The init function is called when the game component is initialized
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @throws Exception Thrown is something fails
     */
    @Override
    public void init(GameContainer gameContainer) throws Exception {
    }

    /**
     * \brief The update function is called before every frame is rendered
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i             The time in ms since the last update call
     * @throws Exception Thrown is something fails
     */
    @Override
    public void update(GameContainer gameContainer, int i) throws Exception {
    }

    /**
     * \brief the render function is called every frame to draw the game component
     *
     * @param gamecontainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics      the org.newdawn.slick.graphics instance used for drawing
     * @throws Exception Thrown is something fails
     */
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws Exception {
        map.render(0, 0);
    }

    /**
     * \brief The dispose function is called before a component is destoryed
     *
     * @throws Exception Thrown is something fails
     */
    @Override
    public void dispose() throws Exception {
    }
}
