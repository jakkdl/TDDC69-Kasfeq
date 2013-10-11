package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

public class Player extends GameObject {
    private Color playerColor;
    private float health=10;
    private float aimAngle = 0;
    private float aimAngleSpeed = 0;

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

    public Player(World world) {
        super(world, 2, 10, 20);
    }

    public void collision() {
    }

    public void collision(GameObject obj) {
        health -= 1;
        if (health == 0) {
            despawn();
        }
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        super.update(gameContainer, i);
        aimAngle += aimAngleSpeed;
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(playerColor);
        Vector2f position = getPosition();
        graphics.fillRect(position.getX(), position.getY(),super.getWidth(),super.getHeight());
        float aimRadius = getWidth();
        graphics.setColor(Color.red);
        graphics.drawLine(position.getX()+getWidth()/2,position.getY()+getHeight()/2,(float)(position.getX()+getWidth()/2+aimRadius*Math.cos(aimAngle)),(float)(position.getY()+getHeight()/2+aimRadius*Math.sin(aimAngle)));
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
            addInstantForce(new Vector2f(0, -2f));
        }
        else {
        }
    }

    public void shoot(boolean isKeyPressed) {
        if(isKeyPressed) {
            Projectile bullet = new Projectile(getWorld());
            bullet.setPosition(bulletPosition());
            bullet.addInstantForce(new Vector2f(Math.toDegrees(aimAngle)).scale(1));
            getWorld().spawn(bullet);
        }
        else {
        }
    }

    private Vector2f bulletPosition() {
        float x = getPosition().getX() + getWidth() / 2;
        float y = getPosition().getY() + getHeight() / 2;
        return new Vector2f(x, y).add(new Vector2f(Math.toDegrees(aimAngle)).scale(15));
    }

    public void aimLeft(boolean isKeyPressed) {
        if(isKeyPressed) {
            aimAngleSpeed += Math.PI/120;
        }
        else {
            aimAngleSpeed -= Math.PI/120;
        }
    }

    public void aimRight(boolean isKeyPressed) {
        if(isKeyPressed) {
            aimAngleSpeed -= Math.PI/120;
        }
        else {
            aimAngleSpeed += Math.PI/120;
        }
    }

    @Override
    public void dispose() throws Exception {
        super.dispose();
    }
}
