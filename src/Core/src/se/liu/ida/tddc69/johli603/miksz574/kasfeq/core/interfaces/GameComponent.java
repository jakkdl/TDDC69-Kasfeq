package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces;

import org.newdawn.slick.GameContainer;

/**
 * \interface GameComponent
 * \brief Represents a game component
 */
public interface GameComponent {
    /**
     * \brief The init function is called when the game component is initialized
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     */
    public void init(GameContainer gameContainer);

    /**
     * \brief The update function is called before every frame is rendered
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i The time in ms since the last update call
     */
    public void update(GameContainer gameContainer, int i);

    /**
     * \brief The dispose function is called before a component is destoryed
     */
    public void dispose();
}
