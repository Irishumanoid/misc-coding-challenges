import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
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
        for (int i = 0; i < config.size()-2; i++) {
            if (i > 2) {
                pieceLocs.add(new Tuple<String,Integer>(config.get(i+2), i+2));
            } else {
                pieceLocs.add(new Tuple<String,Integer>(config.get(i), i));
            }
        }
        pieceLocs.forEach((p) -> {System.out.println(p);});
    }

    public static boolean isInRange(int target, int min, int max) {
        return target <= max && target >= min;
    }

    public List<String> getInitialConfig() {
        return config;
    }

    public Color getColor(String curPiece) {
        if (curPiece == Piece.BLANK.strVal) {
            return Color.BLANK;
        }
        if (curPiece.toUpperCase() == curPiece) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    public boolean isLegalMove(List<String> curConfig, int startPoint, int endPoint, String turnColor) {
        String curPiece = curConfig.get(startPoint);
        System.out.println("current piece is: " + curPiece);
        Color color = getColor(curPiece);
        //check if turn color is correct, endpoints exist, and not moving to location with piece of the same color already on it
        if (color.colorVal != turnColor || 
            !isInRange(startPoint, 0, curConfig.size()) || !isInRange(endPoint, 0, curConfig.size()) ||
            getColor(curConfig.get(endPoint)).colorVal == turnColor) {
            System.out.println("not working");
            return false;
        }
        
        if (Piece.KING.strVal.equals(curPiece)) {
            if (Math.abs(endPoint - startPoint) == 1) { return true; }
        } else if (Piece.KNIGHT.strVal.equals(curPiece)) {
            if (Math.abs(endPoint - startPoint) == 1) { return true; }
        } else if (Piece.ROOK.strVal.equals(curPiece)) {
            boolean noHopping = true;
            List<String> subList = curConfig.subList(startPoint+1, endPoint);
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
            Color turn = turnCounter % 2 == 0 ? Color.WHITE : Color.BLACK;
            System.out.printf("start and end position for %s piece: ", turn);
            int startPos = s.nextInt(), endPos = s.nextInt();

            if (chess.isLegalMove(curConfig, startPos, endPos, turn.colorVal)) {
                //alter curConfig
                curConfig.set(endPos, curConfig.get(startPos));
                curConfig.set(startPos, Piece.BLANK.strVal);
                //also update piece locations in tuple

                turnCounter++;
            } else {
                System.out.println("Not a legal move, please try again.");
                continue;
            }

            curConfig.forEach((entry) -> {System.out.printf(" %s ", entry);});
            //use tuple values for dynamic access (storing game piece and location (-1 if piece has been eliminated))
            //check for stalemate (no more legal moves, only kings left), or other king is eliminated
        }

        s.close();
    }
}
