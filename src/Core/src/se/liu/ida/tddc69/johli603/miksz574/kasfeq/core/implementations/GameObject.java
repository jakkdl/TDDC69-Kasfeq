package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

public abstract class GameObject implements GameComponent {
    private final World world;
    private Vector2f position;
    private Vector2f velocity;
    private Vector2f continuousForce;
    private Vector2f instantaneousForce;
    private double facing;

    private float mass;
    private float width;
    private float height;

    public World getWorld() {
        return world;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Vector2f getInstantForce() {
        return instantaneousForce;
    }

    public void setInstantForce(Vector2f force) {
        this.instantaneousForce = force;
    }

    public void addInstantForce(Vector2f force) {
        instantaneousForce.add(force);
    }

    public Vector2f getContForce() {
        return continuousForce;
    }
    
    public void setContForce(Vector2f force) {
        this.continuousForce = force;
    }

    public void addContForce(Vector2f force) {
        continuousForce.add(force);
    }

    public abstract void collision();

    public void despawn() {
        world.despawn(this);
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public double getFacing() {
        return facing;
    }

    public void setFacing(double facing) {
        this.facing = facing;
    }

    protected GameObject(World world, float mass, float width, float height) {
        this.world = world;
        position = new Vector2f();
        velocity = new Vector2f();
        continuousForce = new Vector2f();
        instantaneousForce = new Vector2f();
        this.mass = mass;
        this.width = width;
        this.height = height;
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
    }

    @Override
    public void dispose() throws Exception {
    }
}
