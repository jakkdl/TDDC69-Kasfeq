package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces;

import org.newdawn.slick.*;

public interface GameComponent {
    public void init(GameContainer gameContainer) throws  SlickException, NoSuchFieldException;
    public void update(GameContainer gameContainer, int i) throws SlickException, NoSuchFieldException;
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException;
    public void dispose() throws Exception;
}
