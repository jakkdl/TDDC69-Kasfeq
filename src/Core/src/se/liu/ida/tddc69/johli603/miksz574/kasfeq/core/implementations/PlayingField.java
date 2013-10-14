package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources.ResourceManager;

public class PlayingField implements GameComponent {
    public enum Options {
        MAPNAME("defaultMapName"),
        GRAVITY(0.05),
        FRICTION(0.03),
        AIMSPEED(Math.PI / 120),
        PLAYERMOVEFORCE(0.1),
        PLAYERMASS(2.0),
        PLAYERWIDTH(10.0),
        PLAYERHEIGHT(20.0),
        PLAYERJUMPFORCE(-2.0),
        PLAYERLIVES(3),
        PLAYERHEALTH(10.0),
        BULLETMASS(0.1),
        BULLETWIDTH(2.0),
        BULLETHEIGHT(2.0);
        private final Object defaultValue;
        // Since java does not like non-final fields in enums and there is no specific supression for this "issue"
        @SuppressWarnings("all")
        private Object changedValue = null;

        Options(Object defaultValue) {
            this.defaultValue = defaultValue;
        }

        @SuppressWarnings("unchecked")
        public <T> T getValue() {
            return (T) (changedValue == null ? defaultValue : changedValue);
        }

        public <T> void setValue(T value) {
            changedValue = value;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }
    }

    private TiledMap map;

    public PlayingField(String filename) {
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

    public void setOptions() {
        for (Options option : Options.values()) {
            String mapProperty = map.getMapProperty(option.toString(), null);
            Object def = option.getValue();

            if (mapProperty != null) {
                try {
                    if (def instanceof Integer) {
                        option.setValue(Integer.parseInt(mapProperty));
                    } else if (def instanceof Double) {
                        option.setValue(Double.parseDouble(mapProperty));
                    } else if (def instanceof Boolean) {
                        option.setValue(Boolean.parseBoolean(mapProperty));
                    } else if (def instanceof String) {
                        option.setValue(mapProperty);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error when loading " + option.toString() + " incorrect type");
                }
            }
        }
    }

    public double getAimspeed() {
        return Options.AIMSPEED.getValue();
    }

    public double getGravity() {
        return Options.GRAVITY.getValue();
    }

    public double getFriction() {
        return Options.FRICTION.getValue();
    }

    public double getPlayerHealth() {
        return Options.PLAYERHEALTH.getValue();
    }

    public int getPlayerLives() {
        return Options.PLAYERLIVES.getValue();
    }

    public double getPlayerMass() {
        return Options.PLAYERMASS.getValue();
    }

    public double getPlayerWidth() {
        return Options.PLAYERWIDTH.getValue();
    }

    public double getPlayerHeight() {
        return Options.PLAYERHEIGHT.getValue();
    }

    public double getPlayerJumpForce() {
        return Options.PLAYERJUMPFORCE.getValue();
    }

    public double getPlayerMoveForce() {
        return Options.PLAYERMOVEFORCE.getValue();
    }

    public double getBulletMass() {
        return Options.BULLETMASS.getValue();
    }

    public double getBulletHeight() {
        return Options.BULLETHEIGHT.getValue();
    }

    public double getBulletWidth() {
        return Options.BULLETWIDTH.getValue();
    }

    /**
     * \brief The init function is called when the game component is initialized
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @throws Exception Thrown if something fails
     */
    @Override
    public void init(GameContainer gameContainer) throws Exception {
    }

    /**
     * \brief The update function is called before every frame is rendered
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i             The time in ms since the last update call
     * @throws Exception Thrown if something fails
     */
    @Override
    public void update(GameContainer gameContainer, int i) throws Exception {
    }

    /**
     * \brief the render function is called every frame to draw the game component
     *
     * @param gamecontainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics      the org.newdawn.slick.graphics instance used for drawing
     * @throws Exception Thrown if something fails
     */
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws Exception {
        map.render(0, 0);
    }

    /**
     * \brief The dispose function is called before a component is destoryed
     *
     * @throws Exception Thrown if something fails
     */
    @Override
    public void dispose() throws Exception {
    }
}
