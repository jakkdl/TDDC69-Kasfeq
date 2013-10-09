package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

public class Player extends GameObject {
    private Color playerColor;
    private float health=100;

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    public float getHealth() {
        return health;
    }

    public void modHealth(float healthMod) {
        this.health *= healthMod;
    }

    public Player(World world, float mass, float width, float height) {
        super(world, mass, width, height);
    }

    public void collision() {
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        super.update(gameContainer, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(playerColor);
        Vector2f position = getPosition();
        graphics.fillRect(position.getX(), position.getY(),super.getWidth(),super.getHeight());
    }

    public void moveLeft(boolean isKeyPressed) {
        setFacing(Math.PI);
        if(isKeyPressed) {
            addContForce(new Vector2f(-0.1f, 0));
        }
        else {
            addContForce(new Vector2f(0.1f, 0));
        }
    }

    public void moveRight(boolean isKeyPressed) {
        setFacing(0);
        if(isKeyPressed) {
            addContForce(new Vector2f(0.1f, 0));
        }
        else {
            addContForce(new Vector2f(-0.1f, 0));
        }
    }

    public void jump(boolean isKeyPressed) {
        if(isKeyPressed) {
            addInstantForce(new Vector2f(0, -0.5f));
        }
        else {
        }
    }

    public void shoot(boolean isKeyPressed) {
        if(isKeyPressed) {
            Projectile bullet = new Projectile(getWorld());
            bullet.setPosition(getPosition().copy());
            bullet.addInstantForce(new Vector2f(getFacing()));
            getWorld().spawn(bullet);
        }
        else {
        }
    }

    @Override
    public void dispose() throws Exception {
        super.dispose();
    }
}
