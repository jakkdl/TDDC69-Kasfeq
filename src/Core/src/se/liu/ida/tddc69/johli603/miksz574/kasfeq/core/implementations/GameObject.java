package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

/**
 * \class GameObject
 * \brief The base class that all objects in the game world inherit
 */
public abstract class GameObject implements GameComponent {
    private final World world;
    private Vector2d position;
    private Vector2d velocity;
    private Vector2d continuousForce;
    private Vector2d instantaneousForce;
    private double facing;
    private double mass;
    private double width;
    private double height;

    /**
     * \brief GameObject constructor
     *
     * @param world  The world that the gameobject will be in
     * @param mass   The mass of the object
     * @param width  The width of the object
     * @param height The height of the object
     */
    protected GameObject(World world, double mass, double width, double height) {
        this.world = world;
        position = new Vector2d();
        velocity = new Vector2d();
        continuousForce = new Vector2d();
        instantaneousForce = new Vector2d();
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
    public Vector2d getPosition() {
        return position;
    }

    /**
     * \brief Sets the position vector of the GameObject instance
     *
     * @param position The new position vector of the game object, (0,0) = top left
     */
    public void setPosition(Vector2d position) {
        this.position = position;
    }

    /**
     * @return Returns the current velocity vector for the game object
     */
    public Vector2d getVelocity() {
        return velocity;
    }

    /**
     * \brief Sets the current velocity vector for the game object
     *
     * @param velocity The new velocity vector
     */
    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }


    /**
     * @return Returns the facing angle of the game object in radians
     */
    public double getFacing() {
        return facing;
    }

    /**
     * @return Returns the instant force applied on the game object
     */
    public Vector2d getInstantForce() {
        return instantaneousForce;
    }

    /**
      * \brief Sets the instant force on the game object
      *
      * @param force The instant force vector to be set
      */
    public void setInstantForce(Vector2d force) {
        this.instantaneousForce = force;
    }

    /**
     * \brief Adds an instant force on the game object
     *
     * @param force The instant force vector to be added
     */
    public void addInstantForce(Vector2d force) {
        instantaneousForce = instantaneousForce.add(force);
    }

    /**
      * @return Returns the continuous force applied on the game object
      */
    public Vector2d getContForce() {
        return continuousForce;
    }
    
    /**
     * \brief Sets the instant force applied on the game object
     *
     * @param force The new instant force vector
     */
    public void setContForce(Vector2d force) {
        this.continuousForce = force;
    }

    /**
     * \brief Adds a continuous force on the game object
     *
     * @param force The continuous force vector to be added
     */
    public void addContForce(Vector2d force) {
        continuousForce = continuousForce.add(force);
    }

    /**
     * \brief Sets the facing angle of the game object
     *
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
     *
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
     *
     * @param obj The game object that we collided with
     */
    public abstract void collision(GameObject obj);

    /**
     * \brief Despawns this game object from the world.
     */
    public void despawn() {
        world.despawn(this);
    }
}
