package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameObjectManager implements GameComponent{
    private final List<GameObject> gameObjects;

    public GameObjectManager() {
        gameObjects = new ArrayList<GameObject>();
    }

    public void spawnObject(GameObject obj) {
        gameObjects.add(obj);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        for(GameObject obj : gameObjects) {
            obj.update(gameContainer, i);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        for(GameObject obj : gameObjects) {
            obj.render(gameContainer, graphics);
        }
    }

    @Override
    public void dispose() throws Exception {
        Iterator<GameObject> iterator = gameObjects.iterator();
        while(iterator.hasNext()) {
            GameObject obj = iterator.next();
            obj.dispose();

            iterator.remove();
        }
    }
}
