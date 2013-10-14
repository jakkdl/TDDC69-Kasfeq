package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
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

    /**
     * The InputManager constructor
     * @param world The world that the InputManager should manage input for
     */
    public InputManager(World world) {
        this.world = world;
    }

    /**
     * \brief The init function is called when the game component is initialized
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @throws SlickException Thrown by functions related to the Slick2D initialization
     * @throws NoSuchFieldException Thrown by the PlayingField class
     */
    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        Input input = gameContainer.getInput();
        InputProvider provider = new InputProvider(input);
        InputProviderListener listener = new InputProviderListener() {
            @Override
            public void controlPressed(Command command) {
                PlayerCommand playerCommand = (PlayerCommand) command;
                try {
                    playerCommand.pressKey();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void controlReleased(Command command) {
                PlayerCommand playerCommand = (PlayerCommand) command;
                try {
                    playerCommand.releaseKey();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        };

        provider.addListener(listener);

        provider.bindCommand(new KeyControl(Input.KEY_W), new PlayerCommand(world, 1, PlayerCommand.InputType.JUMP));
        provider.bindCommand(new KeyControl(Input.KEY_A), new PlayerCommand(world, 1, PlayerCommand.InputType.LEFT));
        provider.bindCommand(new KeyControl(Input.KEY_D), new PlayerCommand(world, 1, PlayerCommand.InputType.RIGHT));
        provider.bindCommand(new KeyControl(Input.KEY_S), new PlayerCommand(world, 1, PlayerCommand.InputType.SHOOT));
        provider.bindCommand(new KeyControl(Input.KEY_Q), new PlayerCommand(world, 1, PlayerCommand.InputType.AIM_RIGHT));
        provider.bindCommand(new KeyControl(Input.KEY_E), new PlayerCommand(world, 1, PlayerCommand.InputType.AIM_LEFT));

        provider.bindCommand(new KeyControl(Input.KEY_LEFT), new PlayerCommand(world, 2, PlayerCommand.InputType.LEFT));
        provider.bindCommand(new KeyControl(Input.KEY_RIGHT), new PlayerCommand(world, 2, PlayerCommand.InputType.RIGHT));
        provider.bindCommand(new KeyControl(Input.KEY_UP), new PlayerCommand(world, 2, PlayerCommand.InputType.AIM_RIGHT));
        provider.bindCommand(new KeyControl(Input.KEY_DOWN), new PlayerCommand(world, 2, PlayerCommand.InputType.AIM_LEFT));
        provider.bindCommand(new KeyControl(Input.KEY_NUMPAD0), new PlayerCommand(world, 2, PlayerCommand.InputType.SHOOT));
        provider.bindCommand(new KeyControl(Input.KEY_DECIMAL), new PlayerCommand(world, 2, PlayerCommand.InputType.JUMP));
    }

    /**
     * \brief The update function is called before every frame is rendered
     * @param gameContainer The org.newdawn.slick.GameContainer instance of the game
     * @param i The time in ms since the last update call
     * @throws SlickException Thrown by functions related to the Slick2D update
     * @throws NoSuchFieldException Thrown by the PlayingField class
     */
    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
    }

    /**
     * \brief the render function is called every frame to draw the game component
     * @param gamecontainer the org.newdawn.slick.gamecontainer instance of the game
     * @param graphics the org.newdawn.slick.graphics instance used for drawing
     * @throws slickexception thrown by functions related to the slick2d rendering
     */
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
    }

    /**
     * \brief The dispose function is called before a component is destoryed
     * @throws Exception Thrown is something fails
     */
    @Override
    public void dispose() throws Exception {
    }
}
