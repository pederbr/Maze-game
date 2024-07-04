package mazeGame;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class HighScoreTest {

    private String name;
    private int difficulty, time, achievement;

    @BeforeEach
    void setUp() {
        name = "John";
        difficulty = 3;
        time = 5000;
        achievement = 3 * 3 * 100 - 5000;
    }

    @Test
    public void testGetters() {
        HighScore highScore = new HighScore(name, difficulty, time);
        assertEquals(name, highScore.getName());
        assertEquals(difficulty, highScore.getDifficulty());
        assertEquals(time, highScore.getTime());
        assertEquals(achievement, highScore.getAchievement());
    }

    @Test
    public void testToCSV() {
        HighScore highScore = new HighScore(name, difficulty, time);
        assertEquals(name + "," + difficulty + "," + time + "," + achievement, highScore.toCSV());
    }
}

