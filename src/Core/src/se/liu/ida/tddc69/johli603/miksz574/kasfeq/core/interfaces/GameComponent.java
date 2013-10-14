package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * \interface GameComponent
 * \brief Represents a game component
 */
public interface GameComponent {
    /**
     * \brief The init function is called when the game component is initialized
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @throws Exception Thrown is something fails
     */
    public void init(GameContainer gameContainer) throws  Exception;

    /**
     * \brief The update function is called before every frame is rendered
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i The time in ms since the last update call
     * @throws Exception Thrown is something fails
     */
    public void update(GameContainer gameContainer, int i) throws Exception;

    /**
     * \brief the render function is called every frame to draw the game component
     * @param gamecontainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics the org.newdawn.slick.graphics instance used for drawing
     * @throws exception thrown is something fails
     */
    public void render(GameContainer gameContainer, Graphics graphics) throws Exception;

    /**
     * \brief The dispose function is called before a component is destoryed
     * @throws Exception Thrown is something fails
     */
    public void dispose() throws Exception;
}
