package mazeGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * This class is responsible for providing static methods for the other classes.
 */

public class HelpMethods {

    //for maze generation
    private static int x, y;
    private static List<int[]> neighbors;
    private static Stack<int[]> backTrackStack;
    private static int[] currentCell;
    private static final Random r = new Random();

    /**
     * Generates a maze on the given Grid object using a recursive backtracking algorithm.
     * The start point of the maze is set on the left side of the grid, and the end point is determined
     * based on the maximum stack size during the maze generation process.
     * @param grid The Grid object to generate the maze on.
     * @return The Grid object with the generated maze.
     */
    
    public static Grid generateMaze(Grid grid){ 
        
        //variabel for å holde på største stack størrelse
        int maxSize = 0;

        //stack for å rekursivt finne naboer
        backTrackStack = new Stack<>();

        //setter generator på venstre siden av mappet
        x = grid.getWidth();
        y = r.nextInt(1, grid.getHeight());
        grid.set(x, y, false);

        //markerer startpunkt
        grid.setStart(new int[]{x, y});
        backTrackStack.push(new int[]{x, y});

        while (!backTrackStack.isEmpty()) {
            //henter nåværende koordinater 
            currentCell = backTrackStack.pop();
            x = currentCell[0];
            y = currentCell[1];

            //oppdaterer og shuffler naboer 
            updateNeighbours(grid);
            Collections.shuffle(neighbors);
            
            for (int[] neighbor : neighbors) {
                int nx = neighbor[0];
                int ny = neighbor[1];

                //hvis naboen ikke har blitt besøkt
                if (grid.get(nx, ny)) {
                    grid.set(nx, ny, false); //markerer cellen som tom
                    grid.set(x + (nx - x) / 2,y + (ny - y) / 2, false); //ødelegger veggen mellom nåværende og nabo
                    backTrackStack.push(new int[]{nx, ny}); //dette er nå nye koordinater
                }

                //finner sluttpunkt
                if (backTrackStack.size() >= maxSize) {
                    maxSize = backTrackStack.size();
                    grid.setEnd(new int[]{x, y});
                }
            }
        }    
        //oppdaterer koordinater mtp at det er blitt lagt til en border
        grid.addOuterBorder();
        grid.setEnd(new int[]{grid.getEnd()[0]+1, grid.getEnd()[1]});
        grid.setStart(new int[]{grid.getStart()[0]+1, grid.getStart()[1]});
        if(grid.lowerBorderAdded()) {
            grid.setEnd(new int[]{grid.getEnd()[0], grid.getEnd()[1]+1});
            grid.setStart(new int[]{grid.getStart()[0], grid.getStart()[1]+1});
        }
        return grid;
    }

    /**
     * Updates the list of neighbors for the current cell in the maze generation process.
     * The neighbors are cells that are adjacent to the current cell in the grid.
     * @param grid The Grid object representing the maze.
     */
    private static void updateNeighbours(Grid grid) {
        neighbors = new ArrayList<>();
        //henter ut naboer i en liste
        if (x > 2) neighbors.add(new int[]{x - 2, y});
        if (y > 2) neighbors.add(new int[]{x, y - 2});
        if (y < grid.getHeight() - 1) neighbors.add(new int[]{x, y + 2});
        if (x < grid.getWidth() - 1) neighbors.add(new int[]{x + 2, y});
    }

    /**
     * Formats the elapsed time into a "MM:SS:MS" string, where MM is minutes, SS is seconds, and MS is milliseconds.
     * The elapsed time is expected to be in milliseconds.
     *
     * @param elapsedTime The elapsed time in milliseconds.
     * @return A string representing the formatted time.
     */
    public static String formatTime(long elapsedTime) {        
        int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
        int seconds = (int) ((elapsedTime / 1000) % 60);
        int miliseconds = (int) (elapsedTime % 1000) / 10;
        return String.format("%02d:%02d:%02d", minutes, seconds, miliseconds);    
    }
    /**
     * Checks if a move in a given direction is valid.
     *
     * @param d The direction to move.
     * @return True if the move is valid, false otherwise.
     */
    public static boolean validMove(Direction d, Iplayer player, Grid maze) {
        switch (d) {
            case UP:
                return (!maze.get(player.pos()[0], player.pos()[1]-1));
            case DOWN:
                return (!maze.get(player.pos()[0], player.pos()[1]+1));
            case LEFT:
                return (!maze.get(player.pos()[0]-1, player.pos()[1])); 
            case RIGHT:
                return (!maze.get(player.pos()[0]+1, player.pos()[1])); 
        }
        return false;
    }
}
