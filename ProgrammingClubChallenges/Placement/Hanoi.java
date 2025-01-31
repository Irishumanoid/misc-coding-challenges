import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Hanoi {
    private Stack<Integer> towerA;
    private Stack<Integer> towerB;
    private Stack<Integer> towerC;

    public Hanoi(List<Integer> pegs) {
        this.towerA = new Stack<>(pegs);
        this.towerB = new Stack<>();
        this.towerC = new Stack<>();
    }

    private void display() {
        System.out.print("A: ");
        towerA.printContents();
        System.out.print("B: ");
        towerB.printContents();
        System.out.print("C: ");
        towerC.printContents();
        System.out.println();
    }

    public void move(int sourceTower, int endTower) {
        int value;
        switch (sourceTower) {
            case 0:
                value = towerA.peek();
                towerA.pop();
                break;
            case 1:
                value = towerB.peek();
                towerB.pop();
                break;
            case 2:
                value = towerC.peek();
                towerC.pop();
                break;
            default:
                System.out.println("this is not an option");
                value = -1;
                break;
        }

        switch (endTower) {
            case 0:
                if (!towerA.isEmpty() && value > towerA.peek()) {
                    throw new Error("Invalid placement sequence");
                }
                towerA.push(value);
                break;
            case 1:
                if (!towerB.isEmpty() && value > towerB.peek()) {
                    throw new Error("Invalid placement sequence");
                }
                towerB.push(value);
                break;
            case 2:
                if (!towerC.isEmpty() && value > towerC.peek()) {
                    throw new Error("Invalid placement sequence");
                }
                towerC.push(value);
                break;
            default:
                System.out.println("this is not an option");
                break;
        }
        display();
    }

    public void solve(int numMove, int source, int end, int by) {
        if (numMove == 1) {
            move(source, end);
            return;
        }
        solve(numMove-1, source, by, end);
        move(source, end);
        solve(numMove-1, by, end, source);
    }

    public void solve() {
        solve(towerA.getSize(), 0, 1, 2);

    }

    public boolean isFinished() {
        if (towerA.isEmpty() && towerC.isEmpty()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        List<Integer> pegs;
        try {
            pegs = Arrays.asList(args).stream().map(arg -> Integer.parseInt(arg)).collect(Collectors.toList());
        } catch (java.lang.NumberFormatException nfe) {
            System.out.println("Only numbers can be passed in as arguments");
            return;
        }

        if (pegs.stream().anyMatch(e -> e <= 0)) {
            System.out.println("All inputs must be positive");
            return;
        }

        Hanoi game = new Hanoi(pegs);

        while (!game.isFinished()) {
            game.solve();
        }
    }
}
