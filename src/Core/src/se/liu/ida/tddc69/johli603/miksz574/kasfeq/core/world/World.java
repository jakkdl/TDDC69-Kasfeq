package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.GameObjectManager;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.InputManager;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.Player;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.PlayingField;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.resources.ResourceManager;

import java.util.ArrayList;
import java.util.List;

public class World implements GameComponent {
    private final GameObjectManager gameObjectManager;
    private final InputManager inputManager;
    private MapComponent mapComponent;
    private List<Player> players;

    public World() {
        players = new ArrayList<Player>();
        gameObjectManager = new GameObjectManager();
        inputManager = new InputManager(this);
        mapComponent = null;

        try {
            mapComponent = new MapComponent((PlayingField)ResourceManager.INSTANCE.loadResource(PlayingField.class, "textMap.xml"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer(int playerID) {
        return players.get(playerID-1);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameObjectManager.init(gameContainer);
        mapComponent.init(gameContainer);
        inputManager.init(gameContainer);

        Player player1 = new Player();
        player1.setPosX(0);
        player1.setPosY(100);
        player1.setPlayerColor(Color.orange);
        gameObjectManager.spawnObject(player1);
        players.add(player1);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        gameObjectManager.update(gameContainer,i);
        mapComponent.update(gameContainer, i);
        inputManager.update(gameContainer, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        gameObjectManager.render(gameContainer, graphics);
        graphics.setColor(Color.white);
        graphics.drawString("Kasfeq",0,0);
        //mapComponent.render(gameContainer, graphics);
    }

    @Override
    public void dispose() throws Exception {
        gameObjectManager.dispose();
    }
}
