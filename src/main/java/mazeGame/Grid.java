package mazeGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a grid for a maze game.
 */
public class Grid {
    private Boolean[][] grid;
    private int width, height, difficulty;
    private int[] start, end;
    private boolean lowerBorderAdded;

    /**
     * Constructs a Grid object with the specified difficulty level.
     *
     * @param difficulty the difficulty level of the grid
     * @throws IllegalArgumentException if the difficulty level is not within the range of 2 to 100 (inclusive)
     */
    public Grid(int difficulty) {
        if (difficulty < 2 || difficulty > 100) {
            throw new IllegalArgumentException("Difficulty level must be between 2 and 100 (inclusive).");
        }
        this.difficulty=difficulty;
        this.width = 2 * difficulty + 1;
        this.height = difficulty + 1;
        this.lowerBorderAdded = false;

        // Set the entire grid to true
        this.grid = new Boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = true;
            }
        }
    }

    /**
     * Adds an outer border to the grid.
     */
    public void addOuterBorder() {
        List<List<Boolean>> tempGrid = new ArrayList<>();
        List<Boolean> topRow = Arrays.asList(grid[0]);
        List<Boolean> bottomRow = Arrays.asList(grid[height - 1]);
        this.width += 2;

        // Add a row at the top if there are any holes in the top row
        if (topRow.stream().anyMatch(Boolean.FALSE::equals)) {
            tempGrid.add(new ArrayList<>(Collections.nCopies(width, true)));
            this.height++;
        }

        // Add true at the beginning and end of each row
        for (Boolean[] row : grid) {
            List<Boolean> newRow = new ArrayList<>();
            newRow.add(true);
            newRow.addAll(Arrays.asList(row));
            newRow.add(true);
            tempGrid.add(newRow);
        }

        // Add a row at the bottom if there are any holes in the bottom row
        if (bottomRow.stream().anyMatch(Boolean.FALSE::equals)) {
            tempGrid.add(new ArrayList<>(Collections.nCopies(width, true)));
            this.height++;
            this.lowerBorderAdded = true;
        }

        // Convert back to the correct form
        Boolean[][] newGrid = new Boolean[height][width];
        for (int i = 0; i < tempGrid.size(); i++) {
            newGrid[i] = tempGrid.get(i).toArray(new Boolean[0]);
        }
        // Update variables
        this.grid = newGrid;
    }

    /**
     * Gets the value at the specified coordinates in the grid.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the value at the specified coordinates, or null if the coordinates are outside the grid
     */
    public Boolean get(int x, int y) {
        // Return null if the coordinates are outside the grid
        if (x < 1 || x > width || y < 1 || y > height) {
            return null;
        }
        return grid[height - y][x - 1];
    }

    /**
     * Sets the value at the specified coordinates in the grid.
     *
     * @param x   the x-coordinate
     * @param y   the y-coordinate
     * @param val the value to set
     * @throws IllegalArgumentException if the coordinates are outside the grid
     */
    public void set(int x, int y, Boolean val) {
        // Throw an exception if the coordinates are outside the grid
        if (x < 1 || x > width || y < 1 || y > height) {
            throw new IllegalArgumentException("Coordinates are outside the grid.");
        }
        grid[height - y][x - 1] = val;
    }

    /**
     * Gets the width of the grid.
     *
     * @return the width of the grid
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the grid.
     *
     * @return the height of the grid
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the starting coordinates of the grid.
     *
     * @return the starting coordinates as an array of length 2, where index 0 represents the x-coordinate and index 1 represents the y-coordinate
     */
    public int[] getStart() {
        return start;
    }

    /**
     * Gets the ending coordinates of the grid.
     *
     * @return the ending coordinates as an array of length 2, where index 0 represents the x-coordinate and index 1 represents the y-coordinate
     */
    public int[] getEnd() {
        return end;
    }

    /**
     * Gets the difficulty level of the grid.
     *
     * @return the difficulty level of the grid
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the starting coordinates of the grid.
     *
     * @param start the starting coordinates as an array of length 2, where index 0 represents the x-coordinate and index 1 represents the y-coordinate
     */
    public void setStart(int[] start) {
        this.start = start;
    }

    /**
     * Sets the ending coordinates of the grid.
     *
     * @param end the ending coordinates as an array of length 2, where index 0 represents the x-coordinate and index 1 represents the y-coordinate
     */
    public void setEnd(int[] end) {
        this.end = end;
    }

    /**
     * Checks if the lower border has been added to the grid.
     *
     * @return true if the lower border has been added, false otherwise
     */
    public boolean lowerBorderAdded() {
        return lowerBorderAdded;
    }

    /**
     * Returns a string representation of the grid.
     *
     * @return a string representation of the grid
     */
    @Override
    public String toString() {
        // Iterate over the grid and build a string representation
        String outString = "";
        for (Boolean[] row : grid) {
            for (Boolean elem : row) {
                if (elem != null) {
                    outString += elem ? "###" : "   ";
                } else {
                    outString += " n ";
                }
            }
            outString += "\n";
        }
        return outString;
    }

    /**
     * Main method to test the Grid class.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Grid grid = new Grid(3);

        for (int i = 1; i <= grid.width; i++) {
            for (int j = 1; j <= grid.height; j++) {
                grid.set(i, j, false);
            }
        }
        grid.addOuterBorder();
        System.out.println(grid);
    }
}

