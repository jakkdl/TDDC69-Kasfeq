package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Vector2f;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

public class PlayerCommand implements Command {
    public enum InputType {
        LEFT, JUMP, RIGHT, SHOOT;
    }

    private World world;
    private int playerID;
    private InputType inputType;

    public void pressKey() {
        Player player = world.getPlayer(playerID);

        switch (inputType) {
            case LEFT:
                player.addContForce(new Vector2f(-0.1f, 0));
                break;
            case RIGHT:
                player.addContForce(new Vector2f(0.1f, 0));
                break;
            case JUMP:
                player.addInstantForce(new Vector2f(0, -2f));
                break;
            case SHOOT:
                Projectile bullet = new Projectile(world);
                bullet.setPosition(player.getPosition().copy());
                bullet.addInstantForce(new Vector2f(10f, 0));
                world.spawn(bullet);
                break;
        }
    }

    public void releaseKey() {
        Player player = world.getPlayer(playerID);

        switch (inputType) {
            case LEFT:
                player.addContForce(new Vector2f(0.1f, 0));
                break;
            case RIGHT:
                player.addContForce(new Vector2f(-0.1f, 0));
                break;
            /*case JUMP:
                player.setForce(new Vector2f(0,0.1f));
                break;*/
        }
    }

    public PlayerCommand(World world, int playerID, InputType inputType) {
        this.world = world;
        this.playerID = playerID;
        this.inputType = inputType;
    }
}
