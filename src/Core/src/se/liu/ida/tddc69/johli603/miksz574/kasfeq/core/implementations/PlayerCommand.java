package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.command.Command;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

public class PlayerCommand implements Command {
    public enum InputType {
        LEFT, JUMP, RIGHT;
    }

    private World world;
    private int playerID;
    private InputType inputType;

    public void pressKey() {
        Player player = world.getPlayer(playerID);

        switch (inputType) {
            case LEFT:
                player.setVelX(-2);
                break;
            case JUMP:
                player.setVelY(-2);
                break;
            case RIGHT:
                player.setVelX(2);
                break;
        }
    }

    public void releaseKey() {
        Player player = world.getPlayer(playerID);

        switch (inputType) {
            case LEFT:
            case RIGHT:
                player.setVelX(0);
                break;
            case JUMP:
                player.setVelY(2);
                break;
        }
    }

    public PlayerCommand(World world, int playerID, InputType inputType) {
        this.world = world;
        this.playerID = playerID;
        this.inputType = inputType;
    }
}
