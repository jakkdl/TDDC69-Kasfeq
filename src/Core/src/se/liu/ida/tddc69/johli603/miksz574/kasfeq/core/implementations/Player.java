package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

/**
 * \class Player
 * \brief The class that represents a human player
 */
public class Player extends GameObject {
    private Color playerColor;
    private double health = 10;
    private int lives = 3;
    private double aimAngle = 0;
    private double aimAngleSpeed = 0;
    private int playerId;
    private final static double BULLETOFFSET = 1.3;

    /**
     * \brief Player constructor
     *
     * @param world The world that the player will be in in
     */
    public Player(World world, int playerId) {
        super(world, world.getPlayingField().getPlayerMass(), world.getPlayingField().getPlayerWidth(), world.getPlayingField().getPlayerHeight());
        this.playerId = playerId;
        playerColor = Color.transparent;
        health = world.getPlayingField().getPlayerHealth();
        lives = world.getPlayingField().getPlayerLives();
    }

    /**
     * @return Returns the id of the player
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * @return Returns the color of the player
     */
    public Color getPlayerColor() {
        return playerColor;
    }

    /**
     * \brief Sets the color of the player
     *
     * @param playerColor The new color for the player
     */
    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * @return Returns the health of the player
     */
    public double getHealth() {
        return health;
    }

    /**
     * \brief Multiplies the health by a factor
     *
     * @param healthMod The factor to multiply the health with
     */
    public void modHealth(double healthMod) {
        this.health *= healthMod;
    }

    /**
     * \brief Abstract function that is called when a game object collides with the terrain
     *
     * @param x x coordinate of collision
     * @param y y coordinate of collision
     */
    @Override
    public void collision(double x, double y) {
    }

    /**
     * \brief Abstract function that is called when a game object collides with another game object
     *
     * @param obj The game object that we collided with
     */
    @Override
    public void collision(GameObject obj) {
        health -= 1;
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
        aimAngle += aimAngleSpeed;
        if (health <= 0) {
            if (lives > 0) {
                setPosition(getWorld().getPlayingField().getAvailablePosition(this));
                health=10;
                lives--;
            }
            else {
                getWorld().despawn(this);
                getWorld().playerDied(this);
            }
        }
    }

    /**
     * \brief the render function is called every frame to draw the game component
     *
     * @param gamecontainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics      the org.newdawn.slick.graphics instance used for drawing
     */
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.setColor(playerColor);
        Vector2d position = getPosition();
        graphics.fillRect((float)position.getX(), (float)position.getY(), (float)getWidth(), (float)getHeight());
        double aimRadius = getWidth();
        graphics.setColor(Color.white);
        graphics.drawLine((float) (position.getX() + getWidth() / 2), (float) (position.getY() + getHeight() / 2), (float) (position.getX() + getWidth() / 2 + aimRadius * Math.cos(aimAngle)), (float) (position.getY() + getHeight() / 2 + aimRadius * Math.sin(aimAngle)));
    }

    /**
     * \brief The dispose function is called before a component is destoryed
     *
     */
    @Override
    public void dispose() {
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void aimLeft(boolean isKeyPressed) {
        if (isKeyPressed) {
            aimAngleSpeed += getWorld().getPlayingField().getAimspeed();
        } else {
            aimAngleSpeed -= getWorld().getPlayingField().getAimspeed();
        }
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void aimRight(boolean isKeyPressed) {
        if (isKeyPressed) {
            aimAngleSpeed -= getWorld().getPlayingField().getAimspeed();
        } else {
            aimAngleSpeed += getWorld().getPlayingField().getAimspeed();
        }
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void moveLeft(boolean isKeyPressed) {
        setFacing(Math.PI);
        if(isKeyPressed) {
            addContForce(new Vector2d(-(float) getWorld().getPlayingField().getPlayerMoveForce(), 0));
        }
        else {
            addContForce(new Vector2d((float) getWorld().getPlayingField().getPlayerMoveForce(), 0));
        }
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void moveRight(boolean isKeyPressed) {
        setFacing(0);
        if(isKeyPressed) {
            addContForce(new Vector2d(getWorld().getPlayingField().getPlayerMoveForce(), 0));
        }
        else {
            addContForce(new Vector2d(-getWorld().getPlayingField().getPlayerMoveForce(), 0));
        }
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void jump(boolean isKeyPressed) {
        if(isKeyPressed) {
            if (getWorld().getPhysicsEngine().isOnGround(this)) {
                addInstantForce(new Vector2d(0, (float)getWorld().getPlayingField().getPlayerJumpForce()));
            }
        }
    }

    /**
     * Calculates the spawn position for a new bullet
     *
     * @return The position vector for the spawn position
     */
    private Vector2d bulletPosition() {
        double x = getPosition().getX() + getWidth() / 2;
        double y = getPosition().getY() + getHeight() / 2;
        double distance = Math.sqrt(Math.pow(getWidth()/2,2) + Math.pow(getHeight()/2,2));
        return new Vector2d(x, y).add(new Vector2d((aimAngle)).scale(distance*BULLETOFFSET));
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void shoot(boolean isKeyPressed) {
        if (isKeyPressed) {
            Projectile bullet = new Projectile(getWorld());
            bullet.setPosition(bulletPosition());
            bullet.addInstantForce(new Vector2d(aimAngle).scale(1));
            getWorld().spawn(bullet);
            //shotgun();
        }
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void shotgun() {
        Projectile[] bullets = new Projectile[6];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new Projectile(getWorld());
            bullets[i].setPosition(bulletPosition());
            bullets[i].addInstantForce(new Vector2d((aimAngle + Math.random())).scale(1));
            getWorld().spawn(bullets[i]);
        }
    }
}
