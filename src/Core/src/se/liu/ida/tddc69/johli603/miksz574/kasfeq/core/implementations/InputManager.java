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

        provider.bindCommand(new KeyControl(Input.KEY_UP), new PlayerCommand(world, 1, PlayerCommand.InputType.JUMP));
        provider.bindCommand(new KeyControl(Input.KEY_LEFT), new PlayerCommand(world, 1, PlayerCommand.InputType.LEFT));
        provider.bindCommand(new KeyControl(Input.KEY_RIGHT), new PlayerCommand(world, 1, PlayerCommand.InputType.RIGHT));
        provider.bindCommand(new KeyControl(Input.KEY_SPACE), new PlayerCommand(world, 1, PlayerCommand.InputType.SHOOT));
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