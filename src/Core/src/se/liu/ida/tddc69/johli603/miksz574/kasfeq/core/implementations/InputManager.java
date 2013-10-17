package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

/**
 * \class InputManager
 * \brief Handles the input for the actual game
 */
public class InputManager implements GameComponent {
    private World world;
    private InputProvider inputProvider;
    private InputProviderListener inputProviderListener;

    /**
     * The InputManager constructor
     *
     * @param world The world that the InputManager should manage input for
     */
    public InputManager(World world) {
        this.world = world;
        inputProvider = null;
        inputProviderListener = null;
    }

    /**
     * \brief The init function is called when the game component is initialized
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     */
    @Override
    public void init(GameContainer gameContainer) {
        Input input = gameContainer.getInput();
        inputProvider = new InputProvider(input);
        inputProviderListener = new InputProviderListener() {
            @Override
            public void controlPressed(Command command) {
                PlayerCommand playerCommand = (PlayerCommand) command;
                playerCommand.pressKey();
            }

            @Override
            public void controlReleased(Command command) {
                PlayerCommand playerCommand = (PlayerCommand) command;
                playerCommand.releaseKey();
            }
        };

        inputProvider.addListener(inputProviderListener);

        inputProvider.bindCommand(new KeyControl(Input.KEY_H), new PlayerCommand(world, 1, PlayerCommand.InputType.JUMP));
        inputProvider.bindCommand(new KeyControl(Input.KEY_A), new PlayerCommand(world, 1, PlayerCommand.InputType.LEFT));
        inputProvider.bindCommand(new KeyControl(Input.KEY_D), new PlayerCommand(world, 1, PlayerCommand.InputType.RIGHT));
        inputProvider.bindCommand(new KeyControl(Input.KEY_G), new PlayerCommand(world, 1, PlayerCommand.InputType.SHOOT));
        inputProvider.bindCommand(new KeyControl(Input.KEY_W), new PlayerCommand(world, 1, PlayerCommand.InputType.AIM_RIGHT));
        inputProvider.bindCommand(new KeyControl(Input.KEY_S), new PlayerCommand(world, 1, PlayerCommand.InputType.AIM_LEFT));

        inputProvider.bindCommand(new KeyControl(Input.KEY_LEFT), new PlayerCommand(world, 2, PlayerCommand.InputType.LEFT));
        inputProvider.bindCommand(new KeyControl(Input.KEY_RIGHT), new PlayerCommand(world, 2, PlayerCommand.InputType.RIGHT));
        inputProvider.bindCommand(new KeyControl(Input.KEY_UP), new PlayerCommand(world, 2, PlayerCommand.InputType.AIM_RIGHT));
        inputProvider.bindCommand(new KeyControl(Input.KEY_DOWN), new PlayerCommand(world, 2, PlayerCommand.InputType.AIM_LEFT));
        inputProvider.bindCommand(new KeyControl(Input.KEY_INSERT), new PlayerCommand(world, 2, PlayerCommand.InputType.SHOOT));
        inputProvider.bindCommand(new KeyControl(Input.KEY_DELETE), new PlayerCommand(world, 2, PlayerCommand.InputType.JUMP));
    }

    /**
     * \brief The update function is called before every frame is rendered
     *
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i             The time in ms since the last update call
     */
    @Override
    public void update(GameContainer gameContainer, int i) {
    }

    /**
     * \brief The dispose function is called before a component is destoryed
     *
     */
    @Override
    public void dispose() {
        inputProvider.removeListener(inputProviderListener);
    }
}
