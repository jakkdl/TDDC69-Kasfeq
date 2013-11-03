package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.*;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.DrawableGameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.logic.AbstractGameLogic;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.logic.DeathmatchLogic;

import java.util.*;

/**
 * \class World
 * \brief Describes the world of the game
 */
public class World implements DrawableGameComponent {
    private final GameObjectManager gameObjectManager;
    private final InputManager inputManager;
    private final PhysicsEngine physicsEngine;
    private final PlayingField playingField;
    private final Map<Integer, Player> players;
    private AbstractGameLogic gameLogic = null;
    private final static String DEFAULT_MAP_NAME = "default.tmx";

    public World() {
        players = new HashMap<Integer, Player>();
        gameObjectManager = new GameObjectManager(this);
        inputManager = new InputManager(this);

        playingField = new PlayingField(DEFAULT_MAP_NAME);
        physicsEngine = new PhysicsEngine(playingField);
    }

    /**
     * \brief Return a player given a playerID.
     *
     * @param playerID ID of the player to return.
     * @return The player whose ID is playerID.
     */
    public Player getPlayer(int playerID) {
        return players.get(playerID);
    }

    /**
     * \brief Spawn/create a GameObject into the world.
     *
     * @param obj The GameObject to spawn.
     */
    public void spawn(GameObject obj) {
        gameObjectManager.spawnObject(obj);
    }

    /**
     * \brief Despawn/delete a GameObject from the world.
     *
     * @param obj The GameObject to despawn.
     */
    public void despawn(GameObject obj) {
        gameObjectManager.despawnObject(obj);

        if(obj instanceof Player) {
            players.remove(Player.class.cast(obj).getPlayerId());
        }
    }

    /**
     * \brief Spawn a new player.
     *
     * @param playerColor Color of the new player.
     */
    public void spawnNewPlayer(Color playerColor) {
        Player player = new Player(this, players.size()+1);
        player.setPosition(playingField.getAvailablePosition(player));
        player.setPlayerColor(playerColor);
        players.put(players.size() + 1, player);
        spawn(player);
    }

    /**
     * \brief Notifies gameLogic that player died and ends the game if <=1 player is left.
     *
     * @param player The Player that died.
     */
    public void playerDied(Player player) {
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

    /**
     *
     * @return the gameLogic used.
     */
    public AbstractGameLogic getGameLogic() {
        return gameLogic;
    }

    /**
     *
     * @return The physicsengine used.
     */
    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }

    /**
     *
     * @return The playingField used.
     */
    public PlayingField getPlayingField() {
        return playingField;
    }

    /**
     * \brief Restart the game.
     *
     * @param gameContainer The GameContainer to use.
     */
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

        gameLogic = new DeathmatchLogic(this);
        gameLogic.init(gameContainer);
    }

    @Override
    public void update(GameContainer gameContainer, int i) {
        gameObjectManager.update(gameContainer, i);
        inputManager.update(gameContainer, i);
        playingField.update(gameContainer, i);

        gameLogic.update(gameContainer, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.drawString("Kasfeq", 0, 0);

        playingField.render(gameContainer, graphics);
        gameObjectManager.render(gameContainer, graphics);

        gameLogic.render(gameContainer, graphics);
    }

    @Override
    public void dispose() {
        gameObjectManager.dispose();
        inputManager.dispose();
        playingField.dispose();
        gameLogic.dispose();

        Iterator<Map.Entry<Integer, Player>> playerIterator = players.entrySet().iterator();
        while(playerIterator.hasNext()) {
            Map.Entry<Integer, Player> playerEntry = playerIterator.next();
            playerEntry.getValue().dispose();

            playerIterator.remove();
        }
    }
}
