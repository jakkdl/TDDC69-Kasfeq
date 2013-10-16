package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.logic;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.Player;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

public class DeathmatchLogic extends AbstractGameLogic {
    private int winningPlayerID = -1;
    private int waitTime = 0;
    private static final int SCORE_SCREEN_TIME = 5000;

    public DeathmatchLogic(World world) {
        super(world);
    }

    /**
     * \brief Abstract function that is called when a player dies
     *
     * @param player The Player object that represents the player that died
     */
    @Override
    public void onPlayerDeath(Player player) {
    }

    /**
     * \brief Abstract function that is called when a player wins
     *
     * @param player The Player object that represents the player that won
     */
    @Override
    public void onPlayerWon(Player player) {
        winningPlayerID = player.getPlayerId();
    }

    /**
     * \brief Abstract function that is called when the game draws
     */
    @Override
    public void onPlayerDraw() {
        winningPlayerID = 0;
    }

    /**
     * \brief The init function is called when the game component is initialized
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     */
    @Override
    public void init(GameContainer gameContainer) {
    }

    /**
     * \brief The update function is called before every frame is rendered
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i             The time in ms since the last update call
     */
    @Override
    public void update(GameContainer gameContainer, int i) {
        if(winningPlayerID >= 0) {
            waitTime += i;

            if(waitTime > SCORE_SCREEN_TIME) {
                getWorld().restartGame(gameContainer);
            }
        }
    }

    /**
     * \brief the render function is called every frame to draw the game component
     *
     * @param gameContainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics      the org.newdawn.slick.graphics instance used for drawing
     */
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        if(winningPlayerID > 0) {
            graphics.clear();
            graphics.setColor(Color.green);
            graphics.drawString(String.format("Player %s won", winningPlayerID), gameContainer.getWidth()/(float)2, gameContainer.getHeight()/(float)2);
        }
        else if(winningPlayerID == 0) {
            graphics.clear();
            graphics.setColor(Color.white);
            graphics.drawString("Draw", gameContainer.getWidth()/(float)2, gameContainer.getHeight()/(float)2);
        }
    }

    /**
     * \brief The dispose function is called before a component is destoryed
     *
     */
    @Override
    public void dispose() {
    }
}
