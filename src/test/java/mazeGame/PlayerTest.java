package mazeGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;
    private int x, y, width, height, radius;

    @BeforeEach
    void setUp() {
        x = 5;
        y = 5;
        width = 10;
        height = 10;
        radius = 3;
        player = new Player(x, y, width, height, radius);
    }

    @Test
    public void testConstructor() {
        assertEquals(x, player.pos()[0]);
        assertEquals(y, player.pos()[1]);
        assertEquals(radius, player.getView().getRadius());
    }

    @Test
    public void testMove() {
        player.move(1, 1);
        assertEquals(x + 1, player.pos()[0]);
        assertEquals(y + 1, player.pos()[1]);
    }

    @Test
    public void testMoveOutOfBounds() {
        player.move(10, 10);
        assertEquals(x, player.pos()[0]);
        assertEquals(y, player.pos()[1]);
    }

    @Test
    public void testSetPos() {
        player.setPos(3, 3);
        assertEquals(3, player.pos()[0]);
        assertEquals(3, player.pos()[1]);
    }

    @Test
    public void testSetPosOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> player.setPos(20, 20));
    }

    @Test
    public void testSetConstraints() {
        player.setConstraints(20, 20);
        player.setPos(15, 15);
        assertEquals(15, player.pos()[0]);
        assertEquals(15, player.pos()[1]);
    }

    @Test
    public void testSetConstraintsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> player.setConstraints(-1, -1));
    }

    @Test
    public void testSetView() {
        player.setView(5);
        assertEquals(5, player.getView().getRadius());
    }

    @Test
    public void testSetViewInvalid() {
        assertThrows(IllegalArgumentException.class, () -> player.setView(-1));
    }
}