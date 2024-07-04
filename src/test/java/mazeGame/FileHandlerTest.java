package mazeGame;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class FileHandlerTest {
    ObservableList<HighScore> list;
    FileHandler fileHandler;

    @BeforeEach
    void setUp() {
        fileHandler = new FileHandler();
        list = FXCollections.observableArrayList(
            new HighScore("peder1", 10, 10000),
            new HighScore("peder2", 50, 10000),
            new HighScore("peder3", 100, 10000)
        );
    }
    @Test
    void testReadAndWriteFromFile() {
        fileHandler.downloadHighScores(list);
        ObservableList<HighScore> filedata = fileHandler.uploadHighScores();
        for (int i = 0; i < filedata.size(); i++) {
            assertEquals(filedata.get(i).getTime(), list.get(i).getTime());
            assertEquals(filedata.get(i).getDifficulty(), list.get(i).getDifficulty());
            assertEquals(filedata.get(i).getName(), list.get(i).getName());
            assertEquals(filedata.get(i).getAchievement(), list.get(i).getAchievement());
        }
    }
}
