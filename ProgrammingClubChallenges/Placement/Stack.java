import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Stack<V> {
    private List<V> contents;

    public Stack() {
        this.contents = new ArrayList<>();
    }

    public Stack(List<V> contents) {
        this.contents = contents;
    }

    public void push(V entry) {
        contents.add(entry);
    }

    public void pop() {
        if (contents.size() > 0) {
            contents.remove(contents.size() - 1);
        } else {
            throw new NoSuchElementException("The stack is already empty, there are no elements to be removed.");
        }
    }

    public V peek() {
        return contents.get(contents.size() - 1);
    }

    public boolean isEmpty() {
        return contents.size() == 0 ? true : false;
    }

    public int getSize() {
        return contents.size();
    }

    public void printContents() {
        contents.forEach(c -> System.out.print(c + " "));
        System.out.println();
    }
}
