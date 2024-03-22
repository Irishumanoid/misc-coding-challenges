package cse143exercises.ConstructionPatterns;

import java.util.List;
import java.util.Objects;

public class Abstraction {
    @FunctionalInterface
    public interface Consumer<T> {
        public void accept(T var);

        default Consumer<T> andThen(Consumer<? super T> var) {
            Objects.requireNonNull(var);
            return varNew -> {
                this.accept(varNew);
                var.accept(varNew);
            };
        }
    }

    @FunctionalInterface
    public interface Supplier<T> {
        public T get();
    }

    public void testing() {
        List<Integer> list = List.of(1,2,3,4);
        Consumer<Integer> printer = x -> System.out.println(x);
        list.forEach((java.util.function.Consumer<? super Integer>) printer);
    
        Supplier<Integer> sayer = () -> 666;
        System.out.println(sayer.get());
        System.out.println(list.stream().filter(num -> num > 2).findAny().orElseGet((java.util.function.Supplier<? extends Integer>) sayer));
    }

    
    
}
