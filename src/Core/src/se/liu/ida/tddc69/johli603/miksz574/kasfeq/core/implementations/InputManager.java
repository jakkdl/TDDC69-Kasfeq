package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.*;
import org.newdawn.slick.command.*;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

public class InputManager implements GameComponent, InputProviderListener {
    private World world;
    public InputManager(World world) {
        this.world = world;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        Input input = gameContainer.getInput();
        InputProvider provider = new InputProvider(input);
        provider.addListener(this);

        provider.bindCommand(new KeyControl(Input.KEY_W), new PlayerCommand(world, 1, PlayerCommand.InputType.JUMP));
        provider.bindCommand(new KeyControl(Input.KEY_A), new PlayerCommand(world, 1, PlayerCommand.InputType.LEFT));
        provider.bindCommand(new KeyControl(Input.KEY_D), new PlayerCommand(world, 1, PlayerCommand.InputType.RIGHT));
        provider.bindCommand(new KeyControl(Input.KEY_S), new PlayerCommand(world, 1, PlayerCommand.InputType.SHOOT));
        provider.bindCommand(new KeyControl(Input.KEY_Q), new PlayerCommand(world, 1, PlayerCommand.InputType.AIM_RIGHT));
        provider.bindCommand(new KeyControl(Input.KEY_E), new PlayerCommand(world, 1, PlayerCommand.InputType.AIM_LEFT));

        provider.bindCommand(new KeyControl(Input.KEY_I), new PlayerCommand(world, 2, PlayerCommand.InputType.JUMP));
        provider.bindCommand(new KeyControl(Input.KEY_J), new PlayerCommand(world, 2, PlayerCommand.InputType.LEFT));
        provider.bindCommand(new KeyControl(Input.KEY_L), new PlayerCommand(world, 2, PlayerCommand.InputType.RIGHT));
        provider.bindCommand(new KeyControl(Input.KEY_K), new PlayerCommand(world, 2, PlayerCommand.InputType.SHOOT));
        provider.bindCommand(new KeyControl(Input.KEY_U), new PlayerCommand(world, 2, PlayerCommand.InputType.AIM_RIGHT));
        provider.bindCommand(new KeyControl(Input.KEY_O), new PlayerCommand(world, 2, PlayerCommand.InputType.AIM_LEFT));
    }

    @Override
    public void controlPressed(Command command) {
        PlayerCommand playerCommand = (PlayerCommand)command;
        playerCommand.pressKey();
    }

    @Override
    public void controlReleased(Command command) {
        PlayerCommand playerCommand = (PlayerCommand)command;
        playerCommand.releaseKey();
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
    }

    @Override
    public void dispose() throws Exception {
    }
}
