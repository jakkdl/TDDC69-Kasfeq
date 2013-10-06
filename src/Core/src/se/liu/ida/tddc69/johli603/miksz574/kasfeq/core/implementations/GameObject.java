package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;

public abstract class GameObject implements GameComponent {
    private Vector2f position; //use a point?
    private Vector2f velocity;
    private Vector2f continuousForce;
    private Vector2f instantaneousForce;
    private float mass;

    private float width;
    private float height;

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




    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    protected GameObject(float mass, float width, float height) {
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
