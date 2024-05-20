package generalizedtictactoe;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class NbyNTicTacToe {
    private int gridSize;
    private ArrayList<ArrayList<PieceType>> gridState;
    private PieceType curPiece;
    private boolean isGameOver;

    enum PieceType {
        WHITE,
        BLACK,
        NONE
    };

    public NbyNTicTacToe(int gridSize) {
        this.gridSize = gridSize;
        this.gridState = new ArrayList<>();
        for (int i = 0; i < this.gridSize; i++) {
            ArrayList<PieceType> row = new ArrayList<>();
            for (int j = 0; j < this.gridSize; j++) {
                row.add(PieceType.NONE); //add default value
            }
            this.gridState.add(row); //add row to matrix
        }
        this.curPiece = PieceType.WHITE;
        this.isGameOver = false;
    }

    public void updateBoardSize(int size) {
        this.gridSize = size;
        for (int i = 0; i < this.gridSize; i++) {
            ArrayList<PieceType> row = new ArrayList<>();
            for (int j = 0; j < this.gridSize; j++) {
                row.add(PieceType.NONE); //add default value
            }
        }
    }

    public int getBoardSize() {
        return this.gridSize;
    }

    //render playing grid screen
    public void configureGame(GridPane gridPane, int size) {
        //add buttons to the GridPane
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Button button = new Button();
                button.setOnMouseClicked(event -> {
                    if (button.getStyle() != "-fx-background-color: red;" && button.getStyle() != "-fx-background-color: blue;") {
                        Image image;
                        if (this.curPiece == PieceType.WHITE) {
                            image = new Image("file:/Users/irislitiu/Downloads/donny.jpg");
                            button.setStyle("-fx-background-color: red;");
                            update(button);
                            this.curPiece = PieceType.BLACK;
                        } else {
                            image = new Image("file:/Users/irislitiu/Downloads/biddy.jpg");
                            button.setStyle("-fx-background-color: blue;");
                            update(button);
                            this.curPiece = PieceType.WHITE;
                        }

                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(80);
                        imageView.setFitHeight(80);
                        button.setGraphic(imageView);

                        System.out.println("current grid state is: ");
                        for (int i = 0; i < this.gridSize; i++) {
                            for (int j = 0; j < this.gridSize; j++) {
                                System.out.print(this.gridState.get(i).get(j) + " ");
                            }
                            System.out.println();
                        }
                    }
                });
                button.setPrefSize(100, 100);
                button.setStyle("-fx-font-size: 24;");
                gridPane.add(button, col, row);
            }
        }
    }

    public void update(Button button) {
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);
        PieceType type = this.curPiece == PieceType.WHITE ? PieceType.WHITE : PieceType.BLACK;
        this.gridState.get(row).set(col, type);
        this.isGameOver = isGameOver(new Tuple<Integer,Integer>(row, col));
        System.out.println("the game is over:" + this.isGameOver);
    }

    public boolean isGameOver() {
        return this.isGameOver;
    }

    public void resetGame() {
        this.isGameOver = false;
    }

    //check if current piece played creates a win
    public boolean isGameOver(Tuple<Integer, Integer> newPieceLoc) {
        //check if piece is on a diagonal
        int LRDiagCounter = 0;
        if (newPieceLoc.getFirst() == newPieceLoc.getSecond()) {
            PieceType firstPiece = this.gridState.get(0).get(0);
            for (int i = 0; i < this.gridSize; i++) {
                if (this.gridState.get(i).get(i) == firstPiece) {
                    LRDiagCounter++;
                }
            }
        }
        int RLDiagCounter = 0;
        if (newPieceLoc.getFirst() + newPieceLoc.getSecond() == this.gridSize-1) {
            PieceType firstPiece = this.gridState.get(0).get(this.gridSize);
            for (int i = 0; i < this.gridSize; i++) {
                if (this.gridState.get(i).get(this.gridSize-i) == firstPiece) {
                    RLDiagCounter++;
                }
            }
        }
        //check all pieces on same row or all on same column
        int rowCounter = 0, colCounter = 0;
        for (int i = 0; i < this.gridSize; i++) {
            if (this.gridState.get(newPieceLoc.getFirst()).get(i) == this.curPiece) {
                rowCounter++;
            }
            if (this.gridState.get(i).get(newPieceLoc.getSecond()) == this.curPiece) {
                colCounter++;
            }
        }

        if (LRDiagCounter == this.gridSize || RLDiagCounter == this.gridSize || rowCounter == this.gridSize || colCounter == this.gridSize) {
            return true;
        }

        return false;
    }
}
