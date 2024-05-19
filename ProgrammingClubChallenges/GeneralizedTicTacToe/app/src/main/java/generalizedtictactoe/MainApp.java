package generalizedtictactoe;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("N by N Tic Tac Toe");
        NbyNTicTacToe game = new NbyNTicTacToe(300);


        TextField textField = new TextField();
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int boardSize = Integer.parseInt(newValue);
                game.updateBoardSize(boardSize);
                System.out.println("board size is: " + boardSize);
            } catch (NumberFormatException e) {
                System.out.println("Value is not an integer");
                textField.setText("3"); //set default to a 3 by 3 board
            }
        });

        Button b = new Button("click to start");
        b.setOnMouseClicked((e) -> {
            b.setText("clicked!");
        });

        GridPane pane = new GridPane();
        pane.add(new Label("Welcome! What are the dimensions of your game?"), 0, 0);
        pane.add(textField, 0, 1);
        pane.add(b, 0 , 2);

        GridPane gameBoard = new GridPane();
        Scene gameScene = new Scene(gameBoard, 640, 480);
        //event handler for switch button to show new scene
        b.setOnAction(event -> {
            game.configureGame(gameBoard, game.getBoardSize());
            stage.setScene(gameScene);
        });

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    System.out.println("Periodic check if game is over: " + game.isGameOver());
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene introScene = new Scene(pane, 640, 480);
        stage.setScene(introScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
