package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.GameObjectManager;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;

public class World implements GameComponent {
    private final GameObjectManager gameObjectManager;

    public World() {
        gameObjectManager = new GameObjectManager();
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        gameObjectManager.update(gameContainer,i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        gameObjectManager.render(gameContainer, graphics);
    }

    @Override
    public void dispose() throws Exception {
        gameObjectManager.dispose();
    }
}
