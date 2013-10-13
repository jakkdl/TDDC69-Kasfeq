package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

public class Player extends GameObject {
    private Color playerColor;
    private double health=10;
    private double aimAngle = 0;
    private double aimAngleSpeed = 0;

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    public double getHealth() {
        return health;
    }

    public void modHealth(double healthMod) {
        this.health *= healthMod;
    }

    public Player(World world) throws NoSuchFieldException {
        super(world, world.getPlayingField().getPlayerMass(), world.getPlayingField().getPlayerWidth(), world.getPlayingField().getPlayerHeight());
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
        graphics.fillRect(position.getX(), position.getY(), (float)getWidth(), (float)getHeight());
        double aimRadius = getWidth();
        graphics.setColor(Color.white);
        graphics.drawLine((float)(position.getX()+getWidth()/2),(float)(position.getY()+getHeight()/2),(float)(position.getX()+getWidth()/2+aimRadius*Math.cos(aimAngle)),(float)(position.getY()+getHeight()/2+aimRadius*Math.sin(aimAngle)));
    }


    public void aimLeft(boolean isKeyPressed) throws NoSuchFieldException {
        if(isKeyPressed) {
            aimAngleSpeed += getWorld().getPlayingField().getAimspeed();
        }
        else {
            aimAngleSpeed -= getWorld().getPlayingField().getAimspeed();
        }
    }

    public void aimRight(boolean isKeyPressed) throws NoSuchFieldException {
        if(isKeyPressed) {
            aimAngleSpeed -= getWorld().getPlayingField().getAimspeed();
        }
        else {
            aimAngleSpeed += getWorld().getPlayingField().getAimspeed();
        }
    }

    public void moveLeft(boolean isKeyPressed) throws NoSuchFieldException {
        setFacing(Math.PI);
        if(isKeyPressed) {
            addContForce(new Vector2f(-(float) getWorld().getPlayingField().getPlayerMoveForce(), 0));
        }
        else {
            addContForce(new Vector2f((float) getWorld().getPlayingField().getPlayerMoveForce(), 0));
        }
    }

    public void moveRight(boolean isKeyPressed) throws NoSuchFieldException {
        setFacing(0);
        if(isKeyPressed) {
            addContForce(new Vector2f((float)getWorld().getPlayingField().getPlayerMoveForce(), 0));
        }
        else {
            addContForce(new Vector2f(-(float)getWorld().getPlayingField().getPlayerMoveForce(), 0));
        }
    }

    public void jump(boolean isKeyPressed) throws NoSuchFieldException {
        if(isKeyPressed) {
            addInstantForce(new Vector2f(0, (float)getWorld().getPlayingField().getPlayerJumpForce()));
        }
        else {
        }
    }

    public void shoot(boolean isKeyPressed) throws NoSuchFieldException {
        if(isKeyPressed) {
            Projectile bullet = new Projectile(getWorld());
            bullet.setPosition(bulletPosition());
            bullet.addInstantForce(new Vector2f(Math.toDegrees(aimAngle)).scale(1));
            getWorld().spawn(bullet);
            //shotgun();
        }
        else {
        }
    }

    public void shotgun() throws NoSuchFieldException {
        Projectile[] bullets = new Projectile[6];
        for(int i = 0; i < bullets.length; i++) {
            bullets[i] = new Projectile(getWorld());
            bullets[i].setPosition(bulletPosition());
            bullets[i].addInstantForce(new Vector2f(Math.toDegrees(aimAngle + Math.random())).scale(1));
            getWorld().spawn(bullets[i]);
        }
    }

    private Vector2f bulletPosition() {
        double x = getPosition().getX() + getWidth() / 2;
        double y = getPosition().getY() + getHeight() / 2;
        return new Vector2f((float)x, (float)y).add(new Vector2f(Math.toDegrees(aimAngle)).scale(15)); //TODO: replace with function that calculates where bullet can spawn
    }

    @Override
    public void dispose() throws Exception {
        super.dispose();
    }
}
