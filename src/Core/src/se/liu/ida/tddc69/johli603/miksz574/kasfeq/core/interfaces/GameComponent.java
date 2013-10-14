package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces;

import org.newdawn.slick.*;

/**
 * \interface GameComponent
 * \brief Represents a game component
 */
public interface GameComponent {
    /**
     * \brief The init function is called when the game component is initialized
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @throws SlickException Thrown by functions related to the Slick2D initialization
     * @throws NoSuchFieldException Thrown by the PlayingField class
     */
    public void init(GameContainer gameContainer) throws  SlickException, NoSuchFieldException;

    /**
     * \brief The update function is called before every frame is rendered
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i The time in ms since the last update call
     * @throws SlickException Thrown by functions related to the Slick2D update
     * @throws NoSuchFieldException Thrown by the PlayingField class
     */
    public void update(GameContainer gameContainer, int i) throws SlickException, NoSuchFieldException;

    /**
     * \brief the render function is called every frame to draw the game component
     * @param gamecontainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics the org.newdawn.slick.graphics instance used for drawing
     * @throws slickexception thrown by functions related to the slick2d rendering
     */
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException;

    /**
     * \brief The dispose function is called before a component is destoryed
     * @throws Exception Thrown is something fails
     */
    public void dispose() throws Exception;
}
