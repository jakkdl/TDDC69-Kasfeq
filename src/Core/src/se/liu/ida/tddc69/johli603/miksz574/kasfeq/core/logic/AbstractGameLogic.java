package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.logic;

import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.Player;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.DrawableGameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

/**
 * \class AbstractGameLogic
 * \brief Base class for game logics
 */
public abstract class AbstractGameLogic implements DrawableGameComponent {
    private final World world;

    /**
     * @return Returns the World object that the game logic applies to
     */
    public World getWorld() {
        return world;
    }

    /**
     * \brief AbstractGameLogic constructor
     * @param world  The world that the game logic applies to
     */
    protected AbstractGameLogic(World world) {
        this.world = world;
    }

    /**
     * \brief Abstract function that is called when a player dies
     * @param player The Player object that represents the player that died
     */
    public abstract void onPlayerDeath(Player player);

    /**
     * \brief Abstract function that is called when a player wins
     * @param player The Player object that represents the player that won
     */
    public abstract void onPlayerWon(Player player);

    /**
     * \brief Abstract function that is called when the game draws
     */
    public abstract void onPlayerDraw();
}
