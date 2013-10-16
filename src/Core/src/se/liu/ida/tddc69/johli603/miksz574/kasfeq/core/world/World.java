package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.*;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.logic.AbstractGameLogic;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.logic.DeathmatchLogic;

import java.util.*;

/**
 * \class World
 * \brief Describes the world of the game
 */
public class World implements GameComponent {
    private final GameObjectManager gameObjectManager;
    private final InputManager inputManager;
    private final PhysicsEngine physicsEngine;
    private final PlayingField playingField;
    private final Map<Integer, Player> players;
    private final List<AbstractGameLogic> gameLogicHandlers;

    public World() {
        players = new HashMap<Integer, Player>();
        gameObjectManager = new GameObjectManager(this);
        inputManager = new InputManager(this);

        playingField = new PlayingField("untitled.tmx");
        physicsEngine = new PhysicsEngine(playingField);
        gameLogicHandlers = new ArrayList<AbstractGameLogic>();
    }

    public Player getPlayer(int playerID) {
        return players.get(playerID);
    }

    public void spawn(GameObject obj) {
        gameObjectManager.spawnObject(obj);
    }

    public void despawn(GameObject obj) {
        gameObjectManager.despawnObject(obj);

        if(obj instanceof Player) {
            players.remove(Player.class.cast(obj).getPlayerId());
        }
    }

    public void spawnNewPlayer(Color playerColor) {
        Player player = new Player(this, players.size()+1);
        player.setPosition(playingField.getAvailablePosition(player));
        player.setPlayerColor(playerColor);
        players.put(players.size() + 1, player);
        spawn(player);
    }

    public void playerDied(Player player) {
        for(AbstractGameLogic gameLogic : gameLogicHandlers) {
            gameLogic.onPlayerDeath(player);

            if(players.size() == 1) {
                for(Player winningPlayer : players.values()) {
                    gameLogic.onPlayerWon(winningPlayer);
                }
            }
            else if(players.size() < 1) {
                gameLogic.onPlayerDraw();
            }
        }
    }

    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }

    public PlayingField getPlayingField() {
        return playingField;
    }

    public void restartGame(GameContainer gameContainer) {
        dispose();
        init(gameContainer);
    }

    @Override
    public void init(GameContainer gameContainer) {
        gameObjectManager.init(gameContainer);
        inputManager.init(gameContainer);
        playingField.init(gameContainer);

        spawnNewPlayer(Color.orange);
        spawnNewPlayer(Color.magenta);

        gameLogicHandlers.add(new DeathmatchLogic(this));

        for(AbstractGameLogic gameLogic : gameLogicHandlers) {
            gameLogic.init(gameContainer);
        }
    }

    @Override
    public void update(GameContainer gameContainer, int i) {
        gameObjectManager.update(gameContainer, i);
        inputManager.update(gameContainer, i);
        playingField.update(gameContainer, i);

        for(AbstractGameLogic gameLogic : gameLogicHandlers) {
            gameLogic.update(gameContainer, i);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.drawString("Kasfeq", 0, 0);

        playingField.render(gameContainer, graphics);
        gameObjectManager.render(gameContainer, graphics);

        for(AbstractGameLogic gameLogic : gameLogicHandlers) {
            gameLogic.render(gameContainer, graphics);
        }
    }

    @Override
    public void dispose() {
        gameObjectManager.dispose();
        inputManager.dispose();
        playingField.dispose();

        Iterator<AbstractGameLogic> iterator = gameLogicHandlers.iterator();
        while (iterator.hasNext()) {
            AbstractGameLogic logic = iterator.next();
            logic.dispose();

            iterator.remove();
        }

        Iterator<Map.Entry<Integer, Player>> playerIterator = players.entrySet().iterator();
        while(playerIterator.hasNext()) {
            Map.Entry<Integer, Player> playerEntry = playerIterator.next();
            playerEntry.getValue().dispose();

            playerIterator.remove();
        }
    }
}
