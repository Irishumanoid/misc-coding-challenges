import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class BrainFck {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.next();
        String[] inputs = input.split("");

        //ignore characters not in first 8
        LinkedList<Integer> output = new LinkedList<>();
        ListIterator<Integer> iterator = output.listIterator();

        //only add new element to linked list if get >
        for (String value : inputs) {
            int curIndex = 0;
            if (iterator.hasNext()) {
                iterator.nextIndex();
            }

            switch (value) {
                case ">":
                    if (iterator.hasNext()) {
                        iterator.next();
                    } else {
                        output.add(0);
                        System.out.println("Added");
                    }
                    System.out.println("> case");
                case "<":
                    if (iterator.hasPrevious()) {
                        iterator.previous();
                    }
                case "+":
                    output.set(curIndex, output.get(curIndex) + 1);
                    System.out.println("+ case");
                case "-":
                    output.set(curIndex, output.get(curIndex) - 1);
                case ".":
                    //convert from number (bytes) to ASCII
                    System.out.println(String.valueOf((char) (int) output.get(curIndex)));
                case ",":
                default:
                    break;
            }
        }

        s.close();
    }
}