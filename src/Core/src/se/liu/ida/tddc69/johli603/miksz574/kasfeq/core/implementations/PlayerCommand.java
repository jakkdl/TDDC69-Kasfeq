package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.command.Command;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

public class PlayerCommand implements Command {
    public enum InputType {
        LEFT, JUMP, RIGHT, SHOOT, AIM_LEFT, AIM_RIGHT
    }

    private World world;
    private int playerID;
    private InputType inputType;

    public void pressKey() throws NoSuchFieldException {
        Player player = world.getPlayer(playerID);

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

    public void releaseKey() throws NoSuchFieldException {
        Player player = world.getPlayer(playerID);

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

    public PlayerCommand(World world, int playerID, InputType inputType) {
        this.world = world;
        this.playerID = playerID;
        this.inputType = inputType;
    }
}
