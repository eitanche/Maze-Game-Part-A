package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;
import algorithms.maze3D.Position3D;
import algorithms.maze3D.SearchableMaze3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    @Test
    void solve3D() {
        BestFirstSearch bfs = new BestFirstSearch();
        assertThrows(NullPointerException.class,
                () -> bfs.solve(null)
        );
        Maze3D maze = null;
        for (int i = 0; i < 10; i++) {
            BestFirstSearch bf = new BestFirstSearch();
            MyMaze3DGenerator mg = new MyMaze3DGenerator();
            long time  = System.currentTimeMillis();
            maze = mg.generate(100,100, 100);
            time = System.currentTimeMillis() - time;
            assertTrue(time < 60000);
            SearchableMaze3D m3 = new SearchableMaze3D(maze);
            time  = System.currentTimeMillis();
            bf.solve(m3);
            time = System.currentTimeMillis() - time;
            assertTrue(time < 60000);
        }


    }

    @Test
    void MazeWithNoSol(){
        BestFirstSearch bfs = new BestFirstSearch();
        MyMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(100,100);
        int [][] map = maze.getMap();
        Position start = maze.getStartPosition();
        Position goal = maze.getGoalPosition();
        int r = (start.getRowIndex()+goal.getRowIndex())/2;
        int c = (start.getColumnIndex()+goal.getColumnIndex())/2;

        for (int i = 0; i <100 ; i++) {
            if (!start.equals(new Position(i,c)) && !goal.equals(new Position(i,c)))
                map[i][c]=1;
            if (!start.equals(new Position(r,i)) && !goal.equals(new Position(r,i)))
                map[r][i]=1;
        }
        SearchableMaze m = new SearchableMaze(maze);
        assertThrows(NullPointerException.class,
                () -> bfs.solve(m));
    }

    @Test
    void MazeWithNoSol3D(){
        BestFirstSearch bfs = new BestFirstSearch();
        MyMaze3DGenerator mg = new MyMaze3DGenerator();
        Maze3D maze = mg.generate(50,50,50);
        int [][][] map = maze.getMap();
        Position3D start = maze.getStartPosition();
        Position3D goal = maze.getGoalPosition();
        int d = (start.getDepthIndex()+goal.getDepthIndex())/2;
        int r = (start.getRowIndex()+goal.getRowIndex())/2;
        int c = (start.getColumnIndex()+goal.getColumnIndex())/2;
        for (int i=0;i<start.getRowIndex();i++)
            for(int j = 0; j< start.getColumnIndex();j++)
                if (!start.equals(new Position3D(i,r,j) )&& !goal.equals(new Position3D(i,r,j)))
                    map[d][i][j]=1;
        for (int i=0;i<start.getDepthIndex();i++)
            for (int j = 0; j < start.getColumnIndex(); j++) {
                if (!start.equals(new Position3D(i,r,j) )&& !goal.equals(new Position3D(i,r,j)))
                    map[i][r][j]=1;
            }
        for (int i=0;i<start.getDepthIndex();i++)
            for (int j = 0; j < start.getRowIndex(); j++) {
                if (!start.equals(new Position3D(i,j,c) )&& !goal.equals(new Position3D(i,j,c)))
                    map[i][j][c]=1;
            }
        SearchableMaze3D m3 = new SearchableMaze3D(maze);
        assertThrows(NullPointerException.class,
                () -> bfs.solve(m3));
    }

    @Test
    void solve() {
        for (int i = 0; i < 10; i++) {
            BestFirstSearch bf = new BestFirstSearch();
            MyMazeGenerator mg = new MyMazeGenerator();
            long time  = System.currentTimeMillis();
            Maze maze = mg.generate(1000,1000);
            time = System.currentTimeMillis() - time;
            assertTrue(time < 60000);
            SearchableMaze m = new SearchableMaze(maze);
            time  = System.currentTimeMillis();
            bf.solve(m);
            time = System.currentTimeMillis() - time;
            assertTrue(time < 60000);
        }

    }


    @Test
    void isEmpty_Dequeue_Enqueue() {
        BestFirstSearch bf = new BestFirstSearch();
        assertTrue(bf.isEmpty());
        MyMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(100,100);
        SearchableMaze s = new SearchableMaze(maze);
        bf.solve(s);
        while (!bf.isEmpty())
            bf.dequeue();
        assertTrue(bf.isEmpty());
        MazeState ms=new MazeState(new Position(200,200),0);
        bf.enqueue(ms);
        assertFalse(bf.isEmpty());
    }

    @Test
    void getName() {
        BestFirstSearch bf = new BestFirstSearch();
        assertEquals("Best First Search",bf.getName());
    }
}