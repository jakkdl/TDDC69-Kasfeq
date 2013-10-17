package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * \interface DrawableGameComponent
 * \brief Represents a game component that is able to render it's content
 */
public interface DrawableGameComponent extends GameComponent {
    /**
     * \brief the render function is called every frame to draw the game component
     * @param gameContainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics the org.newdawn.slick.graphics instance used for drawing
     */
    public void render(GameContainer gameContainer, Graphics graphics);
}
