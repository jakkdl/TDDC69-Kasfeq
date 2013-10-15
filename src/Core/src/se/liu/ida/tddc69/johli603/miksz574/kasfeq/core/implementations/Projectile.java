package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

/**
 * \class Projectile
 * \brief Base class for all projectiles
 */
public class Projectile extends GameObject {
    /**
     * \brief Projectile constructor
     *
     * @param world The world that the projectile will be in in
     */
    public Projectile(World world) {
        super(world, world.getPlayingField().getBulletMass(), world.getPlayingField().getBulletWidth(), world.getPlayingField().getBulletHeight());
    }

    /**
     * \brief Abstract function that is called when a game object collides with the terrain
     *
     * @param x x coordinate for collision
     * @param y y coordinate for collision
     */
    @Override
    public void collision(double x, double y) {
        //TODO: Broken collision handling makes destroying terrain very buggy
        /*for (int dx = -1; dx < getWidth()+1; dx ++) {
            for (int dy = -1; dy < getHeight()+1; dy++) {
                getWorld().getPlayingField().destroyPixel(x+dx, y+dy);
            }
        }*/
        despawn();
    }

    /**
     * \brief Abstract function that is called when a game object collides with another game object
     *
     * @param obj The game object that we collided with
     */
    @Override
    public void collision(GameObject obj) {
        despawn();
    }

    /**
     * \brief The init function is called when the game component is initialized
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     */
    @Override
    public void init(GameContainer gameContainer) {
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
     * @param gamecontainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics      the org.newdawn.slick.graphics instance used for drawing
     */
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        Vector2d position = getPosition();
        graphics.setColor(Color.white);
        graphics.fillRect((float)position.getX(), (float)position.getY(), 5, 5);
    }

    /**
     * \brief The dispose function is called before a component is destoryed
     *
     */
    @Override
    public void dispose() {
    }
}
