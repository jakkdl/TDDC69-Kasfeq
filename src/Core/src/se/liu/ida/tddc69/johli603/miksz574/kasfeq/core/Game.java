package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core;

import org.newdawn.slick.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** \class Game
 *  \brief Main class of the game
 *
 *  Contains the main entry point and handles initialization of everything
 */
class Game extends BasicGame {
    /** \brief Main entry point for the game */
    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new Game("Kasfeq"));
            container.start();
        } catch(SlickException e) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameContainer.setShowFPS(false);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
    }

    private Game(String gameName) {
        super(gameName);
    }
}
