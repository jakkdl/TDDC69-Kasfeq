package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * \class Game
 * \brief Main class of the game
 * Contains the main entry point and handles initialization of everything
 */
class Game implements org.newdawn.slick.Game {
    private GameComponent activeComponent;
    private static final int VSYNC_FPS = 120;

    /**
     * \brief Main entry point for the game
     */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new Game());
            container.start();
        } catch (SlickException e) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void Game() {
        activeComponent = null;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameContainer.setTargetFrameRate(VSYNC_FPS);
        gameContainer.setVSync(true);
        gameContainer.setShowFPS(false);
        gameContainer.setFullscreen(true);

        // Enter game initially(Testing purposes)
        try {
            activeComponent = new World();
            activeComponent.init(gameContainer);
        } catch (SlickException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        try {
            activeComponent.update(gameContainer, i);
        } catch (SlickException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        try {
            activeComponent.render(gameContainer, graphics);
        } catch (SlickException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean closeRequested() {
        // Cleanup
        // Return true if the game should close
        return true;
    }

    @Override
    public String getTitle() {
        return "Kasfeq";
    }

}
