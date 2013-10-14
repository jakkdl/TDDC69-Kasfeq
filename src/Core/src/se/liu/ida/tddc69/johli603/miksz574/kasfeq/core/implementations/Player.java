package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
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
    public Player(World world, int playerId) throws PlayingField.NoSuchOptionException {
        super(world, world.getPlayingField().getPlayerMass(), world.getPlayingField().getPlayerWidth(), world.getPlayingField().getPlayerHeight());
	this.playerId = playerId;
        playerColor = Color.transparent;
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
     */
    @Override
    public void collision() {
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
     * @throws Exception Thrown if something fails
     */
    @Override
    public void init(GameContainer gameContainer) throws Exception {
    }

    /**
     * \brief The update function is called before every frame is rendered
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i             The time in ms since the last update call
     * @throws Exception Thrown if something fails
     */
    @Override
    public void update(GameContainer gameContainer, int i) throws Exception {
        aimAngle += aimAngleSpeed;
	if (health <= 0) {
	    if (lives > 0) {
	        setPosition(getWorld().getPhysicsEngine().getAvailablePosition(this));
	        health=10;
		lives--;
	    }
	    else {
		despawn();
	    }
	}
    }

    /**
     * \brief the render function is called every frame to draw the game component
     *
     * @param gamecontainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics      the org.newdawn.slick.graphics instance used for drawing
     * @throws Exception Thrown if something fails
     */
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws Exception {
        graphics.setColor(playerColor);
        Vector2f position = getPosition();
        graphics.fillRect(position.getX(), position.getY(), (float) getWidth(), (float) getHeight());
        double aimRadius = getWidth();
        graphics.setColor(Color.white);
        graphics.drawLine((float) (position.getX() + getWidth() / 2), (float) (position.getY() + getHeight() / 2), (float) (position.getX() + getWidth() / 2 + aimRadius * Math.cos(aimAngle)), (float) (position.getY() + getHeight() / 2 + aimRadius * Math.sin(aimAngle)));
    }

    /**
     * \brief The dispose function is called before a component is destoryed
     *
     * @throws Exception Thrown if something fails
     */
    @Override
    public void dispose() throws Exception {
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void aimLeft(boolean isKeyPressed) throws PlayingField.NoSuchOptionException {
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
    public void aimRight(boolean isKeyPressed) throws PlayingField.NoSuchOptionException {
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
    public void moveLeft(boolean isKeyPressed) throws PlayingField.NoSuchOptionException {
        setFacing(Math.PI);
        if (isKeyPressed) {
            addContForce(new Vector2f(-(float) getWorld().getPlayingField().getPlayerMoveForce(), 0));
        } else {
            addContForce(new Vector2f((float) getWorld().getPlayingField().getPlayerMoveForce(), 0));
        }
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void moveRight(boolean isKeyPressed) throws PlayingField.NoSuchOptionException {
        setFacing(0);
        if (isKeyPressed) {
            addContForce(new Vector2f((float) getWorld().getPlayingField().getPlayerMoveForce(), 0));
        } else {
            addContForce(new Vector2f(-(float) getWorld().getPlayingField().getPlayerMoveForce(), 0));
        }
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void jump(boolean isKeyPressed) throws PlayingField.NoSuchOptionException {
        if (isKeyPressed) {
            addInstantForce(new Vector2f(0, (float) getWorld().getPlayingField().getPlayerJumpForce()));
        }
    }

    /**
     * Calculates the spawn position for a new bullet
     *
     * @return The position vector for the spawn position
     */
    private Vector2f bulletPosition() {
        double x = getPosition().getX() + getWidth() / 2;
        double y = getPosition().getY() + getHeight() / 2;
        return new Vector2f((float) x, (float) y).add(new Vector2f(Math.toDegrees(aimAngle)).scale(15)); //TODO: replace with function that calculates where bullet can spawn
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void shoot(boolean isKeyPressed) throws PlayingField.NoSuchOptionException {
        if (isKeyPressed) {
            Projectile bullet = new Projectile(getWorld());
            bullet.setPosition(bulletPosition());
            bullet.addInstantForce(new Vector2f(Math.toDegrees(aimAngle)).scale(1));
            getWorld().spawn(bullet);
            //shotgun();
        }
    }

    /**
     * Function used by the InputManager class
     *
     * @param isKeyPressed Is the key pressed
     */
    public void shotgun() throws PlayingField.NoSuchOptionException {
        Projectile[] bullets = new Projectile[6];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = new Projectile(getWorld());
            bullets[i].setPosition(bulletPosition());
            bullets[i].addInstantForce(new Vector2f(Math.toDegrees(aimAngle + Math.random())).scale(1));
            getWorld().spawn(bullets[i]);
        }
    }
}
