package Server;

import java.io.*;
import java.util.Properties;

import IO.MyCompressorOutputStream;
import IO.SimpleCompressorOutputStream;
import algorithms.mazeGenerators.*;

/**
 * represent server that generate mazes
 */
public class ServerStrategyGenerateMaze implements IServerStrategy{

    /**
     * generate and send maze to client
     * @param inFromClient input stream of, gets input stats for maze generation
     * @param outToClient output stream, where write to
     */
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            OutputStream scos;
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            int [] rowCol = (int []) fromClient.readObject();
            IMazeGenerator mazeGen;
            Maze maze;
            //choosing generator
            mazeGen = MazeGenFactory.MazeGen(Configurations.getConfigInstance().loadProp().getProperty("mazeGeneratingAlgorithm"));
            maze = mazeGen.generate(rowCol[0],rowCol[1]);
            //create stream to send to My compressor
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            //My compressor write to byteStream
            scos = new MyCompressorOutputStream(byteStream);
            scos.write(maze.toByteArray());
            scos.flush();
            //send object to client
            toClient.writeObject(byteStream.toByteArray());
            toClient.flush();
            fromClient.close();
            toClient.close();
            scos.close();

        } catch (IOException | ClassNotFoundException e) {

        }


    }
}
