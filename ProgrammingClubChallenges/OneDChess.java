import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import JavaGraphics.Tuple;


public class OneDChess {
    private List<String> config;
    private List<Tuple<String, Integer>> pieceLocs;
    public enum Piece {
        KING("K"),
        KNIGHT("N"),
        ROOK("R"),
        BLANK(".");

        public final String strVal;
        private Piece(String label) {
            this.strVal = label;
        }
    }

    public enum Color {
        WHITE("white"),
        BLACK("black"),
        BLANK("none");

        public final String colorVal;
        private Color(String color) {
            this.colorVal = color;
        }
    }

    public OneDChess() {
        config = new ArrayList<String>(
            Arrays.asList(
                Piece.KING.strVal, Piece.KNIGHT.strVal, Piece.ROOK.strVal, ".", ".", Piece.ROOK.strVal.toLowerCase(), Piece.KNIGHT.strVal.toLowerCase(), Piece.KING.strVal.toLowerCase()));
        pieceLocs = new ArrayList<>();
        for (int i = 0; i < config.size(); i++) {
            if (config.get(i) != ".") {
                pieceLocs.add(new Tuple<String,Integer>(config.get(i), i));
            }
        }
    }

    public static boolean isInRange(int target, int min, int max) {
        return target <= max && target >= min;
    }

    public List<String> getInitialConfig() {
        return config;
    }

    public List<Tuple<String, Integer>> getPieceLocations() {
        return pieceLocs;
    }

    public void setPieceLocation(String piece, Integer location) {
        for (int i = 0; i < pieceLocs.size(); i++) {
            if (piece.equals(pieceLocs.get(i).getFirst())) {
                pieceLocs.set(i, new Tuple<String,Integer>(piece, location));
                break;
            }
        }
    }

    public Color getColor(String curPiece) {
        if (curPiece.equals(Piece.BLANK.strVal)) {
            return Color.BLANK;
        }
        if (curPiece.toUpperCase().equals(curPiece)) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    public Piece getPieceType(String curPiece) {
        if (curPiece.toUpperCase().equals(Piece.KING.strVal)) {
            return Piece.KING;
        } else if (curPiece.toUpperCase().equals(Piece.KNIGHT.strVal)) {
            return Piece.KNIGHT;
        } else if (curPiece.toUpperCase().equals(Piece.ROOK.strVal)) {
            return Piece.ROOK;
        }
        return Piece.BLANK;
    } 

    public boolean isLegalMove(List<String> curConfig, int startPoint, int endPoint, String turnColor) {
        String curPiece = curConfig.get(startPoint);
        Color color = getColor(curPiece);
        //check if turn color is correct, endpoints exist, and not moving to location with piece of the same color already on it
        if (!color.colorVal.equals(turnColor) || 
            !isInRange(startPoint, 0, curConfig.size()) || !isInRange(endPoint, 0, curConfig.size()) ||
            getColor(curConfig.get(endPoint)).colorVal.equals(turnColor)) {
            return false;
        }
        
        if (Piece.KING.strVal.equals(curPiece.toUpperCase())) {
            if (Math.abs(endPoint - startPoint) == 1) { return true; }
        } else if (Piece.KNIGHT.strVal.equals(curPiece.toUpperCase())) {
            if (Math.abs(endPoint - startPoint) == 2) { return true; }
        } else if (Piece.ROOK.strVal.equals(curPiece.toUpperCase())) {
            boolean noHopping = true;
            List<String> subList;
            if (startPoint < endPoint) {
                subList = curConfig.subList(startPoint+1, endPoint);
            } else {
                subList = curConfig.subList(endPoint+1, startPoint);
            }
            for (String entry : subList) {
                if (entry != Piece.BLANK.strVal) {
                    noHopping = false;
                }
            }
            if (noHopping) { return true; }
        } else {
            return false; //starting from blank space
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        OneDChess chess = new OneDChess();
        List<String> curConfig = chess.getInitialConfig();
        int turnCounter = 0;
        boolean isGameFinished = false;
        Color turn;

        System.out.println("Welcome to 1D Chess. The rules are as follows:");
        System.out.println("Uppercase is white, lowercase is black, and turn is shown on the left. \n" +
        "The King (K) can only move 1 space left or right. \n" +
        "The knight (N) must jump 2 spaces, and over any piece in-between them. \n" +
        "The rook (R) can move any number of spaces but cannot move through other pieces. \n" +
        "The dots (.) represent empty spaces. \n" +
        "Provide numbers of user input, the index of the starting cell and the index of the ending cell. \n");

        curConfig.forEach((entry) -> {System.out.printf(" %s ", entry);});
        System.out.println("\n");

        while (!isGameFinished) {
            turn = turnCounter % 2 == 0 ? Color.WHITE : Color.BLACK;
            System.out.printf("start and end position for %s piece: ", turn);
            int startPos = s.nextInt(), endPos = s.nextInt();

            if (chess.isLegalMove(curConfig, startPos, endPos, turn.colorVal)) {
                //alter curConfig
                curConfig.set(endPos, curConfig.get(startPos));
                curConfig.set(startPos, Piece.BLANK.strVal);
                //also update piece locations in tuple
                chess.setPieceLocation(curConfig.get(startPos), endPos);
                if (curConfig.get(endPos) != Piece.BLANK.strVal) {
                    chess.setPieceLocation(curConfig.get(endPos), -1);
                }
                turnCounter++;
            } else {
                System.out.println("Not a legal move, please try again.");
                continue;
            }

            curConfig.forEach((entry) -> {System.out.printf(" %s ", entry);});
            //use tuple values for dynamic access (storing game piece and location (-1 if piece has been eliminated))
            //check for stalemate (no more legal moves, only kings left), or other king is eliminated
            List<Tuple<String, Integer>> remainingPieces = chess.getPieceLocations().stream()
                .filter(piece -> piece.getFirst() != Piece.BLANK.strVal && piece.getSecond() != -1).collect(Collectors.toList());
            for (Tuple<String, Integer> piece : remainingPieces) {
                System.out.println("color: " + chess.getColor(piece.getFirst()) + ", type: " + chess.getPieceType(piece.getFirst()));
            }

            boolean hasWhiteKing = false, hasBlackKing = false, isStalemate = true;
            for (Tuple<String, Integer> piece : remainingPieces) {
                //System.out.println("color: " + chess.getColor(piece.getFirst()) + ", type: " + chess.getPieceType(piece.getFirst()));
                Color curPieceColor = chess.getColor(piece.getFirst());
                if (curPieceColor == Color.WHITE) { hasWhiteKing = true; }
                if (curPieceColor == Color.BLACK) { hasBlackKing = true; }

                if (curPieceColor == turn) {
                    Piece curPieceType = chess.getPieceType(piece.getFirst());
                    int curPieceLoc = piece.getSecond();
                    if (curPieceType == Piece.KING 
                        && (chess.isLegalMove(curConfig, curPieceLoc, curPieceLoc-1, curPieceColor.colorVal) || chess.isLegalMove(curConfig, curPieceLoc, curPieceLoc+1, curPieceColor.colorVal))) {
                        isStalemate = false;
                    } else if (curPieceType == Piece.KNIGHT
                        && (chess.isLegalMove(curConfig, curPieceLoc, curPieceLoc-2, curPieceColor.colorVal) || chess.isLegalMove(curConfig, curPieceLoc, curPieceLoc+2, curPieceColor.colorVal))) {
                        isStalemate = false;
                    } else {
                        for (int i = 0; i < curConfig.size(); i ++) {
                            if (i != curPieceLoc && chess.isLegalMove(curConfig, curPieceLoc, i, curPieceColor.colorVal)) {
                                isStalemate = false;
                            }
                        }
                    }
                }
            }

            if (isStalemate) {
                System.out.println("Stalemate! Care to play again?");
            }

            System.out.println("has white king: " + hasWhiteKing);
            System.out.println("has black king: " + hasBlackKing);

            if (!hasWhiteKing || !hasBlackKing) {
                if (!hasWhiteKing) {
                    System.out.println("Game Over! Black wins!");
                } else {
                    System.out.println("Game Over! White wins!");
                }
                System.exit(0);
            }
        }

        s.close();
    }
}
