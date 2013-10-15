package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.logic;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.Player;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

public class DeathmatchLogic extends AbstractGameLogic {
    private int winningPlayerID = -1;
    private int waitTime = 0;

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
     * @throws Exception Thrown if something fails
     */
    @Override
    public void init(GameContainer gameContainer) throws Exception {
    }

    /**
     * \brief The update function is called before every frame is rendered
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i             The time in ms since the last update call
     * @throws Exception Thrown if something fails
     */
    @Override
    public void update(GameContainer gameContainer, int i) throws Exception {
        if(winningPlayerID >= 0) {
            waitTime += i;

            if(waitTime > 5000) {
                gameContainer.exit();
            }
        }
    }

    /**
     * \brief the render function is called every frame to draw the game component
     *
     * @param gamecontainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics      the org.newdawn.slick.graphics instance used for drawing
     * @throws exception thrown if something fails
     */
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws Exception {
        if(winningPlayerID > 0) {
            graphics.clear();
            graphics.drawString(String.format("Player %s won", winningPlayerID), gameContainer.getWidth()/2, gameContainer.getHeight()/2);
        }
        else if(winningPlayerID == 0) {
            graphics.clear();
            graphics.drawString("Draw", gameContainer.getWidth()/2, gameContainer.getHeight()/2);
        }
    }

    /**
     * \brief The dispose function is called before a component is destoryed
     *
     * @throws Exception Thrown if something fails
     */
    @Override
    public void dispose() throws Exception {
    }
}
