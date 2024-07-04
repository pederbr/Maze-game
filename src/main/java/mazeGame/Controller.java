package mazeGame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * The Controller class is responsible for controlling the game logic and user interactions in the maze game.
 */
public class Controller{
    private static final FileHandler FILE_HANDLER = new FileHandler();
    private static ObservableList<HighScore> highScoreList;
    private static Timeline timeline;
    private static long startTime, elapsedTime;
    private static Grid maze;
    private static Rectangle rect;
    private Iplayer player;
    @FXML
    private AnchorPane scene;

    @FXML
    private Slider slider;

    @FXML
    private GridPane gridPane;

    @FXML 
    private Text timer, winTimeDisplay, winDifficultyDisplay, errorText;

    @FXML 
    private Pane winPane;

    @FXML 
    private TextField nameField;

    @FXML
    private TableView<HighScore> HighScoreView;

    @FXML
    private TableColumn<HighScore, String> timeColumn, nameColumn;

    @FXML 
    private TableColumn<HighScore, Integer> difficultyColumn;

    @FXML
    private Button addRecordButton;


    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is called automatically by JavaFX.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @FXML
    public void initialize() {
        
        winPane.setVisible(false);
        scene.setOnKeyPressed(e -> this.updatePos(e)); 
        scene.requestFocus();

        //definerer hva som skal vises i highscore-tabellen
        nameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));        
        difficultyColumn.setCellValueFactory(p -> new SimpleObjectProperty<Integer>(p.getValue().getDifficulty()));        
        timeColumn.setCellValueFactory(p -> new SimpleStringProperty(HelpMethods.formatTime(p.getValue().getTime())));        

        //laster opp gamle rekorder fra fil
        highScoreList = FILE_HANDLER.uploadHighScores();

        //kobler liste og tabell sammen
        HighScoreView.setItems(highScoreList);

        //sorterer highscore-listen etter tid
        HighScoreView.setSortPolicy(t -> {
            FXCollections.sort(highScoreList, (a, b) -> Long.compare(b.getAchievement(), a.getAchievement()));
            return true;
        });

    } 
    
    /**
     * Starts the timer.
     * If the timer is already running, it will be stopped first to avoid multiple timer threads.
     */
    public void startTimer() {
        if(timeline != null) {timeline.stop();}
        timer.setText("00:00:00");
        startTime = System.currentTimeMillis();
        timeline = new Timeline(new KeyFrame(Duration.millis(10),e ->{
            long now = System.currentTimeMillis();
            elapsedTime = now - startTime;
            timer.setText(HelpMethods.formatTime(elapsedTime));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    /**
     * Generates a new maze based on the value of the slider.
     *
     * @param e The action event that triggered the maze generation.
     */
    @FXML
    public void generateMaze(ActionEvent e) {
        if(winPane.isVisible()) {return;} //gjør så knappen ikke virker når winpane er synlig
        maze = new Grid((int) Math.ceil(slider.getValue()));
        drawMaze(HelpMethods.generateMaze(maze));
        startTimer();
    }

    /**
     * Draws the maze on the grid pane.
     *
     * @param mazeGrid The maze grid to be drawn.
     */
    @FXML
    private void drawMaze(Grid mazeGrid) {
        gridPane.getChildren().clear();
        
        int rectWidth = (int) gridPane.getWidth() / mazeGrid.getWidth();
        int rectHeight = (int) gridPane.getHeight() / mazeGrid.getHeight();

        for (int i = 1; i < mazeGrid.getHeight()+1; i++) {
            for (int j = 1; j < mazeGrid.getWidth()+1; j++) {
                rect = new Rectangle(rectWidth, rectHeight);
                rect.setStroke(null);
                rect.setFill(mazeGrid.get(j, i) ? javafx.scene.paint.Color.BLACK : javafx.scene.paint.Color.WHITE);
                gridPane.add(rect, j, i);
            }
        }
        
        rect = new Rectangle(rectWidth, rectHeight);
        rect.setFill(javafx.scene.paint.Color.GREEN);
        gridPane.add(rect, maze.getEnd()[0], maze.getEnd()[1]);
        
        player = new Player(maze.getStart()[0], maze.getStart()[1], maze.getWidth(), maze.getHeight(), Math.min(rectWidth, rectHeight) / 2);
        drawPlayer();
    }

    /**
     * Draws the player on the grid pane.
     */
    @FXML
    private void drawPlayer() {
        if(maze==null) {
            gridPane.getChildren().remove(player.getView());
            return;
        }
        if(gridPane.getChildren().contains(player.getView())) {
            gridPane.getChildren().remove(player.getView());
        }
        gridPane.add(player.getView(), player.pos()[0], player.pos()[1]);
        if(player.pos()[0] == maze.getEnd()[0] && player.pos()[1] == maze.getEnd()[1]) {
            youWon();
        }
    }

    /**
     * Updates the player's position based on the user's key input.
     *
     * @param event The key event that triggered the position update.
     */
    @FXML
    public void updatePos(KeyEvent event) {
        if(winPane.isVisible()) {return;} //gjør så knappen ikke virker når winpane er synlig
        if(maze == null) { return;} // gjør så spiller ikke kan flytte seg når det ikke er en maze
        int dx = 0, dy = 0;
        Direction direction = null;
        switch (event.getCode()) {
            case W:
                direction = Direction.UP;
                dy = -1;
                break;
            case S:
                direction = Direction.DOWN;
                dy = 1;
                break;
            case A:
                direction = Direction.LEFT;
                dx = -1;
                break;
            case D:
                direction = Direction.RIGHT;
                dx = 1;
                break;
            default:
                break;
        }
        if (direction != null && HelpMethods.validMove(direction, player, maze)) {
            player.move(dx, dy);
            drawPlayer();
        }
    }

    /**
     * Handles the game logic when the player reaches the end of the maze.
     */
    private void youWon() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
        nameField.setVisible(true);
        addRecordButton.setVisible(true);
        winPane.setVisible(true);        
        winTimeDisplay.setText(HelpMethods.formatTime(elapsedTime));
        winDifficultyDisplay.setText(Integer.toString(maze.getDifficulty()));
    }

    /**
     * Closes the win pane and performs necessary actions.
     * If the win pane is not visible, the method returns without performing any actions.
     * Clears the grid pane, sets the maze to null, and hides the win pane.
     *
     * @param e the ActionEvent that triggered the method
     */
    public void closeWinPane(ActionEvent e) {
        if(!winPane.isVisible()) {return;} //gjør så knappen ikke virker når winpane er usynlig
        gridPane.getChildren().clear();
        maze = null;
        winPane.setVisible(false);
    }

    /**
     * Adds a new high score to the high score list.
     * 
     * @param e the ActionEvent that triggered the method
     */
    public void addHighScore(ActionEvent e) {
        String name = nameField.getText();
        if(name == null || name.length() > 10) {
            errorText.setText("navn må være mellom 1 og 10 bokstaver");
            return;
        }
        highScoreList.add(new HighScore(name, maze.getDifficulty(), elapsedTime));
        FXCollections.sort(highScoreList, (a, b) -> Long.compare(b.getAchievement(), a.getAchievement()));
        if(highScoreList.size() > 10) {
            highScoreList.removeLast();
        }
        FILE_HANDLER.downloadHighScores(highScoreList);
        HighScoreView.refresh();

        //gjemmer knappen så man ikke kan legge inn highscore mer enn 1 gang
        nameField.setVisible(false);
        addRecordButton.setVisible(false);
    }

    /**
     * Closes the window and performs necessary actions.
     * Saves the high score list to a file and exits the program.
     *
     * @param e the WindowEvent that triggered the method
     */
    
    public static void closeWindow(WindowEvent e) {
        FILE_HANDLER.downloadHighScores(highScoreList);
        System.exit(0);
    }
}