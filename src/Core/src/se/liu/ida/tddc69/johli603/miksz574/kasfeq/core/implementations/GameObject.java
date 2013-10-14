package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

/**
 * \class GameObject
 * \brief The base class that all objects in the game world inherit
 */
public abstract class GameObject implements GameComponent {
    private final World world;
    private Vector2f position;
    private Vector2f velocity;
    private Vector2f continuousForce;
    private Vector2f instantaneousForce;
    private double facing;
    private double mass;
    private double width;
    private double height;

    /**
     * \brief GameObject constructor
     * @param world The world that the gameobject is to be in
     * @param mass  The mass of the object
     * @param width The width of the object
     * @param height The height of the object
     */
    protected GameObject(World world, double mass, double width, double height) {
        this.world = world;
        position = new Vector2f();
        velocity = new Vector2f();
        continuousForce = new Vector2f();
        instantaneousForce = new Vector2f();
        this.mass = mass;
        this.width = width;
        this.height = height;
    }

    /**
     * @return Returns the World instance that the object is in
     */
    public World getWorld() {
        return world;
    }

    /**
     * @return Returns the position vector of the object in the world, (0,0)=top left
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     * \brief Sets the position vector of the GameObject instance
     * @param position The new position vector of the game object, (0,0) = top left
     */
    public void setPosition(Vector2f position) {
        this.position = position;
    }

    /**
     * @return Returns the current velocity vector for the game object
     */
    public Vector2f getVelocity() {
        return velocity;
    }

    /**
     * \brief Sets the current velocity vector for the game object
     * @param velocity The new velocity vector
     */
    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    /**
     * @return Returns the continious force applied on the game object
     */
    public Vector2f getContForce() {
        return continuousForce;
    }

    /**
     * \brief Sets the continious force applied on the game object
     * @param force The new continious force vector
     */
    public void setContForce(Vector2f force) {
        this.continuousForce = force;
    }

    /**
     * \brief Adds a continious force to the game object
     * @param force The continious force vector to be added
     */
    public void addContForce(Vector2f force) {
        continuousForce.add(force);
    }

    /**
     * @return Returns the instant force applied on the game object
     */
    public Vector2f getInstantForce() {
        return instantaneousForce;
    }

    /**
     * \brief Sets the instant force applied on the game object
     * @param force The new instant force vector
     */
    public void setInstantForce(Vector2f force) {
        this.instantaneousForce = force;
    }

    /**
     * \brief Adds a instant force on the game object
     * @param force The instant force vector to be added
     */
    public void addInstantForce(Vector2f force) {
        instantaneousForce.add(force);
    }

    /**
     * @return Returns the facing angle of the game object in radians
     */
    public double getFacing() {
        return facing;
    }

    /**
     * \brief Sets the facing angle of the game object
     * @param facing The new facing angle in radians
     */
    public void setFacing(double facing) {
        this.facing = facing;
    }

    /**
     * @return Returns the mass of the object
     */
    public double getMass() {
        return mass;
    }

    /**
     * \brief Sets the mass of the game object
     * @param mass The new mass of the object
     */
    public void setMass(double mass) {
        this.mass = mass;
    }

    /**
     * @return Returns the width of the game object
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return Returns the height of the game object
     */
    public double getHeight() {
        return height;
    }

    /**
     * \brief Abstract function that is called when a game object collides with the terrain
     */
    public abstract void collision();

    /**
     * \brief Abstract function that is called when a game object collides with another game object
     * @param obj The game object that we collided with
     */
    public abstract void collision(GameObject obj);

    /**
     * \brief Despawns this game object from the world.
     */
    public void despawn() {
        world.despawn(this);
    }

    /**
     * \brief The update function is called before every frame is rendered
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i             The time in ms since the last update call
     * @throws org.newdawn.slick.SlickException
     *                              Thrown by functions related to the Slick2D update
     * @throws NoSuchFieldException Thrown by the PlayingField class
     */
    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException, NoSuchFieldException {
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
