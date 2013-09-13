package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core;

/** \enum Game
 *  \brief Main class of the game
 *
 *  Contains the main entry point and handles initialization of everything
 */
public enum Game {
    /** \brief The singleton instance of the Game class */
    INSTANCE;

    /** \brief Main entry point for the game */
    public static void main(String[] args) {
        Game.INSTANCE.start();
        Game.INSTANCE.spin();
    }

    private Boolean gameRunning = false;

    private Game() {
    }

    /** \brief Initializes everything and starts the game */
    void start() {
        // Initialize the Module Manager
        ModuleManager.INSTANCE.loadModules();

        gameRunning = true;
    }

    /** \brief Spins until the game is ready to quit */
    void spin() {
        while(gameRunning) {
            ModuleManager.INSTANCE.update();
        }

        stop();
    }

    /** \brief Unloads everything and stops the game */
    void stop() {
        // Unload all the modules
        ModuleManager.INSTANCE.unloadModules();

        gameRunning = false;
    }
}
