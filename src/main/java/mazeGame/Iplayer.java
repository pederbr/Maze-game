package mazeGame;

import javafx.scene.shape.Circle;

public interface Iplayer {
    public int[] pos();
    public void move(int x, int y);
    public void setPos(int x, int y);
    public void setConstraints(int width, int height);
    public Circle getView();
    }
