package generalizedtictactoe;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("N by N Tic Tac Toe");
        NbyNTicTacToe game = new NbyNTicTacToe(300);

        VBox introLayout = new VBox();
        GridPane gameBoard = new GridPane();
        Scene gameScene = new Scene(gameBoard, 640, 480);
        Scene introScene = new Scene(introLayout, 640, 480);
        
        BackgroundImage backgroundIm = new BackgroundImage(
                new Image("file:/Users/irislitiu/Downloads/patrioticvibes.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                null,
                new BackgroundSize(introScene.getWidth(), introScene.getHeight(), false, false, true, false));
        
        Background background = new Background(backgroundIm);
        

        TextField textField = new TextField();
        textField.prefWidthProperty().bind(introScene.widthProperty().divide(2));
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int boardSize = Integer.parseInt(newValue);
                game.updateBoardSize(boardSize);
                System.out.println("board size is: " + boardSize);
                introLayout.setBackground(background);
            } catch (NumberFormatException e) {
                System.out.println("Value is not an integer");
                textField.setText("3"); //set default to a 3 by 3 board
            }
        });

        Button b = new Button("click to start");
        b.setOnMouseClicked((e) -> {
            b.setText("clicked!");
        });

        introLayout.setSpacing(10);
        introLayout.getChildren().addAll(
            new Label("Welcome! What are the dimensions of your game?"),
            textField,
            b
        );

        //event handler for switch button to show new scene
        b.setOnAction(event -> {
            game.configureGame(gameBoard, game.getBoardSize());
            stage.setScene(gameScene);
        });

        GridPane endPane = new GridPane();
        endPane.add(new Label("Game Over!"), 0, 0);
        Button backButton = new Button("Back to Intro");
        backButton.setOnAction(e -> stage.setScene(introScene));
        endPane.add(backButton, 0, 1);
        Scene endScene = new Scene(endPane, 640, 480);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    System.out.println("Periodic check if game is over: " + game.isGameOver());
                    if (game.isGameOver()) {
                        stage.setScene(endScene);
                        game.resetGame();
                    }
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        stage.setScene(introScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
