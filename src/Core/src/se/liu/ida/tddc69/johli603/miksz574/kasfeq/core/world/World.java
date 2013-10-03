package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.GameObjectManager;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.Player;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;

public class World implements GameComponent {
    private final GameObjectManager gameObjectManager;

    public World() {
        gameObjectManager = new GameObjectManager();
        Player player1 = new Player();
        player1.setPlayerColor(Color.orange);
        gameObjectManager.spawnObject(player1);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        gameObjectManager.update(gameContainer,i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        gameObjectManager.render(gameContainer, graphics);
        graphics.setColor(Color.white);
        graphics.drawString("Kasfeq",0,0);
    }

    @Override
    public void dispose() throws Exception {
        gameObjectManager.dispose();
    }
}
