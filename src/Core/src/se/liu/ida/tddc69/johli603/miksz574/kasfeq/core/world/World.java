package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.*;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;

import java.util.HashMap;
import java.util.Map;

public class World implements GameComponent {
    private final GameObjectManager gameObjectManager;
    private final InputManager inputManager;
    private final PhysicsEngine physicsEngine;
    private final PlayingField playingField;
    private final Map<Integer, Player> players;

    public World() {
        players = new HashMap<Integer, Player>();
        gameObjectManager = new GameObjectManager(this);
        inputManager = new InputManager(this);

        playingField = new PlayingField("untitled.tmx");
        physicsEngine = new PhysicsEngine(playingField);
    }

    public Player getPlayer(int playerID) {
        return players.get(playerID);
    }

    public void spawn(GameObject obj) {
        gameObjectManager.spawnObject(obj);
    }

    public void despawn(GameObject obj) {
        gameObjectManager.despawnObject(obj);
    }

    public void despawn(Player player) {
        gameObjectManager.despawnObject(player);
        players.remove(player.getPlayerId());
    }

    public void spawnNewPlayer(Color playerColor) {
        Player player = new Player(this, players.size()+1);
        player.setPosition(physicsEngine.getAvailablePosition(player));
        player.setPlayerColor(playerColor);
        players.put(players.size() + 1, player);
        spawn(player);
    }

    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }

    public PlayingField getPlayingField() {
        return playingField;
    }

    @Override
    public void init(GameContainer gameContainer) throws Exception, SlickException {
        gameObjectManager.init(gameContainer);
        inputManager.init(gameContainer);
        playingField.init(gameContainer);

        spawnNewPlayer(Color.orange);
        spawnNewPlayer(Color.magenta);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws Exception {
        if (players.size() <= 1) {
            gameContainer.exit();
        }
        gameObjectManager.update(gameContainer, i);
        inputManager.update(gameContainer, i);
        playingField.update(gameContainer, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws Exception {
        graphics.setColor(Color.white);
        graphics.drawString("Kasfeq", 0, 0);

        playingField.render(gameContainer, graphics);
        gameObjectManager.render(gameContainer, graphics);
    }

    @Override
    public void dispose() throws Exception {
        gameObjectManager.dispose();
        inputManager.dispose();
        playingField.dispose();
    }
}
