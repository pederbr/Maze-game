package mazeGame;

import javafx.scene.shape.Circle;

/**
 * The Player class represents a player in the maze game.
 */
public class Player implements Iplayer{
    private int x, y, height, width;
    private Circle view;

    /**
     * Constructs a new Player object.
     */
    public Player(int x, int y, int width, int height, int radius) {
        if (x < 1 || x > width) {
            throw new IllegalArgumentException("X-trøbbel");
        }
        if (y < 1 || y > height) {
            throw new IllegalArgumentException("Y-trøbbel");
        }
        if(radius < 1){
            throw new IllegalArgumentException("Radius må være større enn 0");
        }
        if(width < 1 || height < 1){
            throw new IllegalArgumentException("Bredde og høyde må være større enn 0");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.view = new Circle(radius);
        view.setFill(javafx.scene.paint.Color.RED);
    }

    /**
     * Returns the current position of the player.
     *
     * @return an array containing the x and y coordinates of the player's position
     */
    @Override
    public int[] pos() {
        return new int[]{x, y};
    }

    /**
     * Moves the player by the specified amount in the x and y directions.
     * If the new position is outside the maze boundaries, the move does not happen.
     *
     * @param x the amount to move in the x direction
     * @param y the amount to move in the y direction
     */
    @Override
    public void move(int x, int y) {
        int newX = this.x + x;
        int newY = this.y + y;
        if (newX > 0 && newX < width && newY > 0 && newY < height) {
            this.x = newX;
            this.y = newY;
        }
    }

    /**
     * Sets the position of the player to the specified coordinates.
     * Throws an IllegalArgumentException if the coordinates are outside the maze boundaries.
     *
     * @param x the x coordinate of the new position
     * @param y the y coordinate of the new position
     * @throws IllegalArgumentException if the coordinates are outside the maze boundaries
     */
    @Override
    public void setPos(int x, int y) {
        if (x < 1 || x > width) {
            throw new IllegalArgumentException("X-trøbbel");
        }
        if (y < 1 || y > height) {
            throw new IllegalArgumentException("Y-trøbbel");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the maze boundaries for the player.
     *
     * @param width  the width of the maze
     * @param height the height of the maze
     */
    @Override
    public void setConstraints(int width, int height) {
        if(width < 1 || height < 1){
            throw new IllegalArgumentException("Bredde og høyde må være større enn 0");
        }
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the view of the player.
     *
     * @return the view of the player as a Circle object
     */
    @Override
    public Circle getView() {
        return view;
    }

    /**
     * Sets the radius and fill color of the player's view.
     *
     * @param radius the radius of the player's view
     */
    public void setView(int radius) {
        if(radius < 1){
            throw new IllegalArgumentException("Radius må være større enn 0");
        }
        view.setRadius(radius);
        view.setFill(javafx.scene.paint.Color.RED);
    }
}


