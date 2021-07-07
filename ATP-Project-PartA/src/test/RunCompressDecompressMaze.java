package test;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import IO.SimpleCompressorOutputStream;
import IO.SimpleDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import java.io.*;
import java.util.Arrays;

public class RunCompressDecompressMaze {
    public static void main(String[] args) {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(1000, 1000); //Generate new maze
        try {
// save maze to a file
            OutputStream out = new MyCompressorOutputStream(new
                    FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte savedMazeBytes[] = new byte[0];
        try {
//read maze from file
            InputStream in = new MyDecompressorInputStream(new
                    FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Maze loadedMaze = new Maze(savedMazeBytes);
        boolean areMazesEquals =
                Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s",areMazesEquals));

//maze should be equal to loadedMaze
        /*
        byte[] arr = maze.toByteArray();
        Maze newMaze = new Maze(arr);
        if (maze.getCols()!=newMaze.getCols())
            System.out.println("Not equal col number");
        if (maze.getRows()!=newMaze.getRows())
            System.out.println("Not equal row number");

        if (!maze.getStartPosition().equals(newMaze.getStartPosition()))
            System.out.println("Not equal startPos");
        if (!maze.getGoalPosition().equals(newMaze.getGoalPosition()))
            System.out.println("Not equal goalPos");
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j=0;j<maze.getCols();j++) {
                if (maze.getPositionValue(i,j)!=newMaze.getPositionValue(i,j))
                    System.out.println("Poisition at "+i+","+j+" not equal");
            }
        }*/
    }
}