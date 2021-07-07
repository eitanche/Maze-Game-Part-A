package Server;

import algorithms.mazeGenerators.*;

/**
 * This class is a factory which creates MazeGenerator object.
 * Used for the server according to the config file
 */
public class MazeGenFactory {

    /**
     * Creates a maze generator according to the string it gets
     * @param s A string which represents which maze generator to create
     * @return The wanted maze generator
     */
    public static IMazeGenerator MazeGen(String s) {
        switch (s) {
            case "MyMazeGenerator":
                return new MyMazeGenerator();
            case "EmptyMazeGenerator":
                return new EmptyMazeGenerator();
            default:
                return new SimpleMazeGenerator();


        }
    }
}
