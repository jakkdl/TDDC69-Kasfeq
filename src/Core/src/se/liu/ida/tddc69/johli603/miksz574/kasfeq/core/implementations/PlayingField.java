package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.DrawableGameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources.ResourceManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * \class PlayingField
 * \brief Describes the map of the game
 */
public class PlayingField implements DrawableGameComponent {
    /**
     * \enum Options
     * \brief Enum map options
     */
    public enum Options {
        /** \brief The name of the map */
        MAPNAME("defaultMapName"),
        /** \brief The name of the outhor of the map */
        AUTHOR("defaultAuthor"),
        /** \brief The gravity of the map */
        GRAVITY(0.05),
        /** \brief The friction of the map */
        FRICTION(0.03),
        /** \brief The aimspeed for the map */
        AIMSPEED(Math.PI / 120),
        /** \brief The player move force for the map */
        PLAYERMOVEFORCE(0.1),
        /** \brief The player mass for the map */
        PLAYERMASS(2.0),
        /** \brief The player width for the map */
        PLAYERWIDTH(10),
        /** \brief The player height for the map */
        PLAYERHEIGHT(20),
        /** \brief The player jump force for the map */
        PLAYERJUMPFORCE(-12.0),
        /** \brief The player lives for the map */
        PLAYERLIVES(3),
        /** \brief The player health for the map */
        PLAYERHEALTH(10.0),
        /** \brief The player damage for the map */
        PLAYERDAMAGE(0.5),
        /** \brief The bullet mass for the map */
        BULLETMASS(0.1),
        /** \brief The bullet width for the map */
        BULLETWIDTH(5),
        /** \brief The bullet height for the map */
        BULLETHEIGHT(5),
        /** \brief The bullet damage for the map */
        BULLETDAMAGE(1.0);

        private final Object defaultValue;

        // In order to be able to change values we need to keep this field non-final
        @SuppressWarnings("NonFinalFieldInEnum")
        private Object changedValue = null;

        Options(Object defaultValue) {
            this.defaultValue = defaultValue;
        }

        public <T> T getValue(Class<T> valueType) {
            return valueType.cast(changedValue == null ? defaultValue : changedValue);
        }

        public <T> void setValue(T value) {
            changedValue = value;
        }
    }

    /**
     * \enum MapTile
     * \brief Enum describing the types of tiles
     */
    public enum MapTile {
        /** \brief Describes a empty tile */
        EMPTY, //AIR
        /** \brief Describes a solid tile */
        SOLID,
        /** \brief Describes a destructable tile */
        DESTRUCTABLE
    }

    private TiledMap map = null;
    private String filename = null;

    public PlayingField(String filename) {
        this.filename = filename;
    }

    public int getTileWidth() {
        return map.getTileWidth();
    }

    public int getTileHeight() {
        return map.getTileHeight();
    }

    public void setPixel(double x, double y, int type) {
        int layerID = map.getLayerIndex("Tile Layer 1");
        int tileX = (int) Math.floor(x);
        int tileY = (int) Math.floor(y);
        map.setTileId(tileX/map.getTileWidth(), tileY/map.getTileHeight(), layerID, type);

    }

    public MapTile getPixel(double x, double y) {
        return getTile(x/map.getTileWidth(), y/map.getTileHeight());
    }

    public MapTile getPixel(Vector2d point) {
        return getTile(point.getX()/map.getTileWidth(), point.getY()/map.getTileHeight());
    }

    public MapTile getTile(double x, double y) {
        int layerID = map.getLayerIndex("Tile Layer 1");
        int tileX = (int) Math.floor(x);
        int tileY = (int) Math.floor(y);

        if (tileX < 0 || tileX >= map.getWidth() || tileY < 0 || tileY >= map.getHeight()) {
            return MapTile.SOLID;
        }

        switch( map.getTileId(tileX, tileY, layerID)) {
            case 1:
                return MapTile.SOLID;
            case 2:
                return MapTile.EMPTY;
            case 3:
                return MapTile.DESTRUCTABLE;
            default:
                System.out.println("Unknown tile ID");
                return null;
        }
    }

    public void destroyPixel(double x, double y) {
        if (getPixel(x, y) == MapTile.DESTRUCTABLE) {
            setPixel(x, y, 2);
        }
    }


    public Vector2d getAvailablePosition(Player player) {
        List<int[]> points = new ArrayList<int[]>();
        int tileWidth = getTileWidth();
        int tileHeight = getTileHeight();

        int playerTileWidth = (int)Math.ceil((double)player.getWidth() / tileWidth);
        int playerTileHeight = (int)Math.ceil((double)player.getHeight() / tileHeight);

        for (int x = 0; x < map.getWidth()- playerTileWidth; x++) {
            for (int y=0; y < map.getHeight()- playerTileHeight; y++) {
                boolean collision = false;
                for (int playerX=0; playerX < playerTileWidth; playerX++) {
                    for (int playerY=0; playerY < playerTileHeight; playerY++) {
                        if (getTile(x+playerX, y+playerY) != MapTile.EMPTY) {
                            collision = true;
                        }
                    }
                }
                if (!collision) {
                    points.add(new int[] {x,y});
                }
            }
        }

        int random = (int)(Math.random()*(points.size()-1));

        return new Vector2d(points.get(random)[0]*tileWidth, points.get(random)[1]*tileHeight);
    }

    public void setOptions() {
        for (Options option : Options.values()) {
            String mapProperty = map.getMapProperty(option.toString(), null);
            Object def = option.getValue(Object.class);

            if (mapProperty != null) {
                try {
                    if(def.getClass() == Integer.class) {
                        option.setValue(Integer.parseInt(mapProperty));
                    } else if(def.getClass() == Double.class) {
                        option.setValue(Double.parseDouble(mapProperty));
                    } else if (def.getClass() == Boolean.class) {
                        option.setValue(Boolean.parseBoolean(mapProperty));
                    } else if (def.getClass() == String.class) {
                        option.setValue(mapProperty);
                    }
                } catch (NumberFormatException ignored) {
                    System.out.println("Error when loading " + option.toString() + " incorrect type");
                }
            }
        }
    }

    public double getAimspeed() {
        return Options.AIMSPEED.getValue(Double.class);
    }

    public double getGravity() {
        return Options.GRAVITY.getValue(Double.class);
    }

    public double getFriction() {
        return Options.FRICTION.getValue(Double.class);
    }

    public double getPlayerHealth() {
        return Options.PLAYERHEALTH.getValue(Double.class);
    }

    public int getPlayerLives() {
        return Options.PLAYERLIVES.getValue(Integer.class);
    }

    public double getPlayerMass() {
        return Options.PLAYERMASS.getValue(Double.class);
    }

    public int getPlayerWidth() {
        return Options.PLAYERWIDTH.getValue(Integer.class);
    }

    public int getPlayerHeight() {
        return Options.PLAYERHEIGHT.getValue(Integer.class);
    }

    public double getPlayerJumpForce() {
        return Options.PLAYERJUMPFORCE.getValue(Double.class);
    }

    public double getPlayerMoveForce() {
        return Options.PLAYERMOVEFORCE.getValue(Double.class);
    }

    public double getPlayerDamage() {
        return Options.PLAYERDAMAGE.getValue(Double.class);
    }

    public double getBulletMass() {
        return Options.BULLETMASS.getValue(Double.class);
    }

    public int getBulletHeight() {
        return Options.BULLETHEIGHT.getValue(Integer.class);
    }

    public int getBulletWidth() {
        return Options.BULLETWIDTH.getValue(Integer.class);
    }

    public double getBulletDamage() {
        return Options.BULLETDAMAGE.getValue(Double.class);
    }

    /**
     * \brief The init function is called when the game component is initialized
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     */
    @Override
    public void init(GameContainer gameContainer) {
        try {
            map = ResourceManager.reloadResource(TiledMap.class, filename);
            setOptions();
        } catch (FileNotFoundException ignored) {
            System.out.println("Could not find map file");
        } catch(IOException e) {
            e.printStackTrace();
            map = null;
        } catch(SlickException e) {
            e.printStackTrace();
            map = null;
        }
    }

    /**
     * \brief The update function is called before every frame is rendered
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i             The time in ms since the last update call
     */
    @Override
    public void update(GameContainer gameContainer, int i) {
    }

    /**
     * \brief the render function is called every frame to draw the game component
     *
     * @param gameContainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics      the org.newdawn.slick.graphics instance used for drawing
     */
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        map.render(0, 0);
    }

    /**
     * \brief The dispose function is called before a component is destoryed
     *
     */
    @Override
    public void dispose() {
        map = null;
    }
}
