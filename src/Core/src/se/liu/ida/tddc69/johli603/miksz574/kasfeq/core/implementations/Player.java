package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.*;
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

    /**
     * \brief Player constructor
     *
     * @param world The world that the player will be in in
     */
    public Player(World world, int playerId) {
        super(world, world.getPlayingField().getPlayerMass(), world.getPlayingField().getPlayerWidth(), world.getPlayingField().getPlayerHeight(), world.getPlayingField().getPlayerDamage());
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
    // For future implementations
    @SuppressWarnings("UnusedDeclaration")
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
     * \brief Deals damage to the player
     *
     * @param damage The ammount of damage the player takes
     */
    public void dealDamage(double damage) {
        health -= damage;
    }

    /**
     * \brief Sacrifices a life giving the player full health
     */
    public void reduceLife() {
        health = getWorld().getPlayingField().getPlayerHealth();
        this.lives--;
    }

    /**
     * @return Returns the ammount of lives the player has left
     */
    public int getLives() {
        return lives;
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
            getWorld().playerDied(this);
        }
    }

    /**
     * \brief the render function is called every frame to draw the game component
     *
     * @param gameContainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics      the org.newdawn.slick.graphics instance used for drawing
     */
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.setColor(playerColor);
        Vector2d position = getPosition();
        int floorX = (int)Math.floor(position.getX());
        int floorY = (int)Math.floor(position.getY());
        graphics.fillRect(floorX, floorY, getWidth(), getHeight());

        //draw weapon
        graphics.setColor(Color.white);
        double aimRadius = length((double)getWidth()/2, (double)getHeight()/2);
        int xMid = floorX + getWidth()  / 2;
        int yMid = floorY + getHeight()  / 2;
        graphics.drawLine(xMid, yMid, (float) (xMid + aimRadius * Math.cos(aimAngle)), (float)(yMid + aimRadius * Math.sin(aimAngle)));

        // Draw healthbar
        graphics.drawString(((Integer)lives).toString(), floorX-10, floorY-10);
        graphics.setColor(Color.green);
        graphics.fillRect(floorX, floorY-6, (float)((health/getWorld().getPlayingField().getPlayerHealth())*getWidth()), 3);
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
            move(Math.PI);
        }
        else {
            move(0);
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
            move(0);
        }
        else {
            move(Math.PI);
        }
    }

    /**
     * * Internal function used my moveLeft and moveRight
     *
     * @param direction direction of movement
     */
    private void move(double direction) {
        double moveForce = getWorld().getPlayingField().getPlayerMoveForce();
        addContForce(new Vector2d(Math.cos(direction)*moveForce, Math.sin(direction)*moveForce));
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void jump(boolean isKeyPressed) {
        if(isKeyPressed) {
            if (getWorld().getPhysicsEngine().checkSolidCollision(this, PhysicsEngine.Direction.DOWN) ||
                    (getWorld().getPhysicsEngine().checkSolidCollision(this, PhysicsEngine.Direction.LEFT) && getContForce().getX() > 0) ||
            (getWorld().getPhysicsEngine().checkSolidCollision(this, PhysicsEngine.Direction.RIGHT) && getContForce().getX() < 0 )){
                addInstantForce(new Vector2d(0, getWorld().getPlayingField().getPlayerJumpForce()));
            }
        }
    }

    /**
     * Calculates the spawn position for a new bullet
     *
     * @return The position vector for the spawn position
     */
    private Vector2d bulletPosition(Projectile bullet) {
        double x = getPosition().getX() + (double)getWidth() / 2;
        double y = getPosition().getY() + (double)getHeight() / 2;
        double distance = length((double)getWidth()/2+bullet.getWidth(), (double)getHeight()/2+bullet.getHeight());
        return new Vector2d(x, y).add(new Vector2d((aimAngle)).scale(distance));
    }

    private double length (double x, double y) {
        return Math.sqrt(x*x+y*y);
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void shoot(boolean isKeyPressed) {
        if (isKeyPressed) {
            Projectile bullet = new Projectile(getWorld());
            bullet.setPosition(bulletPosition(bullet));
            bullet.addInstantForce(new Vector2d(aimAngle).scale(1));
            getWorld().spawn(bullet);
            //shotgun();
        }
    }

    // For future implementations
    @SuppressWarnings("UnusedDeclaration")
    public void shotgun() {
        Projectile[] bullets = new Projectile[6];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new Projectile(getWorld());
            bullets[i].setPosition(bulletPosition(bullets[i]));
            bullets[i].addInstantForce(new Vector2d((aimAngle + Math.random())).scale(1));
            getWorld().spawn(bullets[i]);
        }
    }
}
