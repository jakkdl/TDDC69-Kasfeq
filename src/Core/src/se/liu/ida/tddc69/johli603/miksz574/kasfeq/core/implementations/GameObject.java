package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;

public abstract class GameObject implements GameComponent {
    private int posX, posY;
    private int velX, velY;

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        posX += velX;
        posY += velY;
    }

    @Override
    public void dispose() throws Exception {
    }
}
