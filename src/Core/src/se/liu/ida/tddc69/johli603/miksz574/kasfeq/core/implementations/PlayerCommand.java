package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.command.Command;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

/**
 * \class PlayerCommand
 * \brief This class is used when binding a keyboard key to a certain action for a player
 */
public class PlayerCommand implements Command {
    private final World world;
    private final int playerID;
    private final InputType inputType;

    /**
     * \brief PlayerCommand constructor
     *
     * @param world     The World instance where the player is
     * @param playerID  The PlayerID for which this command should be bound to
     * @param inputType The type of action that this command represents
     */
    public PlayerCommand(World world, int playerID, InputType inputType) {
        this.world = world;
        this.playerID = playerID;
        this.inputType = inputType;
    }

    /**
     * \brief Inherited from org.newdawn.slick.command.Command
     */
    public void pressKey() {
        Player player = world.getPlayer(playerID);

        if (player == null)
            return;

        switch (inputType) {
            case LEFT:
                player.moveLeft(true);
                break;
            case RIGHT:
                player.moveRight(true);
                break;
            case JUMP:
                player.jump(true);
                break;
            case SHOOT:
                player.shoot(true);
                break;
            case AIM_LEFT:
                player.aimLeft(true);
                break;
            case AIM_RIGHT:
                player.aimRight(true);
                break;
        }
    }

    /**
     * \brief Inherited from org.newdawn.slick.command.Command
     */
    public void releaseKey() {
        Player player = world.getPlayer(playerID);

        if (player == null)
            return;

        switch (inputType) {
            case LEFT:
                player.moveLeft(false);
                break;
            case RIGHT:
                player.moveRight(false);
                break;
            case JUMP:
                player.jump(false);
                break;
            case SHOOT:
                player.shoot(false);
                break;
            case AIM_LEFT:
                player.aimLeft(false);
                break;
            case AIM_RIGHT:
                player.aimRight(false);
                break;
        }
    }

    /**
     * \enum InputType
     * \brief Represents the different actions for a player
     */
    public enum InputType {
        /**
         * Input describing the left input
         */
        LEFT,

        /**
         * Input describing the jump input
         */
        JUMP,

        /**
         * Input describing the right input
         */
        RIGHT,

        /**
         * Input describing the shoot input
         */
        SHOOT,

        /**
         * Input describing the left aiming
         */
        AIM_LEFT,

        /**
         * Input describing the right aiming
         */
        AIM_RIGHT
    }
}
