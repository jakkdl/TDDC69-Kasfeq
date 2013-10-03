package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core;

import org.newdawn.slick.*;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

import java.util.logging.Level;
import java.util.logging.Logger;

/** \class Game
 *  \brief Main class of the game
 *
 *  Contains the main entry point and handles initialization of everything
 */
class Game implements org.newdawn.slick.Game {
    private GameComponent activeComponent;

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameContainer.setTargetFrameRate(60);
        gameContainer.setVSync(true);
        gameContainer.setShowFPS(false);

        // Enter game initially(Testing purposes)
        activeComponent = new World();
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        activeComponent.update(gameContainer, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        activeComponent.render(gameContainer, graphics);
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

    /** \brief Main entry point for the game */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new Game());
            container.start();
        } catch(SlickException e) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
