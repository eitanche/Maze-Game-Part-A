package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import IO.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    public static final String tempDirectoryPath = System.getProperty("java.io.tmpdir");
    private static volatile Object o = new Object();
    @Override

    /**
     * solve maze
     */
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            Maze maze;
            maze =(Maze) fromClient.readObject();
            Solution sol = findSolution(maze);
            //if the maze was solved - find the maze's solution in the files created by the server
            if (sol == null){
                SearchableMaze searchMaze = new SearchableMaze(maze);
                ISearchingAlgorithm searcher = SearchingAlogrithmFactory.getSearchingAlgorithm();
                sol=searcher.solve(searchMaze);
                addSolution(maze,sol);
            }
            toClient.writeObject(sol);
            toClient.flush();
            toClient.close();
            fromClient.close();
        }
        catch(Exception e) {

        }
    }

    /**
     * this function checks if we already solved the arg maze
     * @param maze to check if solved
     * @return Solution if the maze solved already and null if not
     */
    private Solution findSolution(Maze maze) {
        try {
            long hashCode = maze.hashCode();
            String fileName = ""+hashCode;
            int fileNameCounter = 0;
            String name = tempDirectoryPath+"\\maze"+fileName+"-"+fileNameCounter+".txt";
            File hash = new File(name);

            while (hash.exists()) {
                synchronized (o) {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(name));
                    ByteArrayInputStream byteIn = new ByteArrayInputStream((byte[]) in.readObject());
                    MyDecompressorInputStream decompress = new MyDecompressorInputStream(byteIn);
                    byte[] decompressedMaze = new byte[1500000];
                    decompress.read(decompressedMaze);
                    if (maze.equals(new Maze(decompressedMaze))) {
                        Solution sol = (Solution) in.readObject();
                        in.close();
                        decompress.close();
                        return sol;
                    }
                    in.close();
                    decompress.close();
                }
                fileNameCounter++;
                name = tempDirectoryPath+"\\maze"+fileName+"-"+fileNameCounter+".txt";
                hash = new File(name);
            }

        } catch (Exception e) {

        }
        return null;
    }

    /**
     * add solution to temp directory
     * @param maze maze to add
     * @param sol solution to add
     */
    private void addSolution(Maze maze, Solution sol) {
        try {
            ByteArrayOutputStream byteStream;
            ObjectOutputStream out;
            long hashCode = maze.hashCode();
            String fileName = ""+hashCode;
            int fileNameCounter = 0;
            //create new file name that not exists to prevent collision names
            String name = tempDirectoryPath+"\\maze"+fileName+"-"+fileNameCounter+".txt";
            synchronized (o) {
                File hash = new File(name);
                //if file already exists, search after file name that isn't used
                while (hash.exists()) {
                    fileNameCounter++;
                    name = tempDirectoryPath + "\\maze" + fileName + "-" + fileNameCounter + ".txt";
                    hash = new File(name);
                }
                //create file and use it as output stream
                out = new ObjectOutputStream(new FileOutputStream(name));
                byteStream = new ByteArrayOutputStream();
                MyCompressorOutputStream scos = new MyCompressorOutputStream(byteStream);
                scos.write(maze.toByteArray());
                scos.flush();
                scos.close();
                //write maze object and send only him
                out.writeObject(byteStream.toByteArray());
                out.flush();
                //write solution object
                out.writeObject(sol);
            }
            out.flush();
            out.close();
            byteStream.close();

        }
        catch (Exception e) {

        }
    }
}