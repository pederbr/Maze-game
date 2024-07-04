package mazeGame;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileHandler {
    private static final String packageName = FileHandler.class.getPackage().getName();

    public ObservableList<HighScore> uploadHighScores() {
        try {
            //laster inn filen
            File csvInputFile = new File("src\\main\\resources\\" + packageName + "\\highscores.csv");
            ObservableList<HighScore> highScoreList = FXCollections.observableArrayList();
            //leser filen
            java.nio.file.Files.lines(csvInputFile.toPath())
                .map(line -> line.split(","))
                .forEach(line -> highScoreList.add(new HighScore(line[0], Integer.parseInt(line[1]), Long.parseLong(line[2]))));
            return highScoreList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void downloadHighScores(ObservableList<HighScore> highScoreList) {
        //konverterer listen til en streng
        String filestring = highScoreList.stream()
            .map(HighScore::toCSV)
            .collect(Collectors.joining("\n"));
        
        //laster ned filen
        File csvOutputFile = new File("src\\main\\resources\\" + packageName + "\\highscores.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.write(filestring);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        FileHandler handler = new FileHandler();
        ObservableList<HighScore> list=FXCollections.observableArrayList(
            new HighScore("peder1", 10, 10000),
            new HighScore("peder2", 50, 10000),
            new HighScore("peder3", 100, 10000)
        );
            handler.downloadHighScores(list);
            ObservableList<HighScore> list2=handler.uploadHighScores();
            System.out.println(list2);
    }

}

