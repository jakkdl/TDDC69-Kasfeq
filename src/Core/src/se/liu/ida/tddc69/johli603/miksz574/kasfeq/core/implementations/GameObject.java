package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;

public abstract class GameObject implements GameComponent {
    private Vector2f position; //use a point?
    private Vector2f velocity;
    private Vector2f force;
    private float mass;

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public Vector2f getForce() {
        return force;
    }

    public void setForce(Vector2f force) {
        this.force = force;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    protected GameObject(float mass) {
        position = new Vector2f();
        velocity = new Vector2f();
        force = new Vector2f();
        this.mass = mass;
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        // i = time since last update in ms
        // F=m*a
        velocity.add(force.scale(1.0f/mass));
        position.add(velocity);
    }

    @Override
    public void dispose() throws Exception {
    }
}
