package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Player extends GameObject {
    private Color playerColor;
    private float health=1;

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

    public Player() {
        super(1);
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
        graphics.fillRect(position.getX(), position.getY(),32,64);
    }

    @Override
    public void dispose() throws Exception {
        super.dispose();
    }
}
