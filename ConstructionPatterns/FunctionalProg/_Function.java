import java.util.function.Function;
import java.util.function.BiFunction;

public class _Function {
    public static void main(String[] args) {
        int inc = incrementByOneFunction.apply(1);
        int mult = multiplyByTenFunction.apply(1);

        Function<Integer, Integer> composite =
            incrementByOneFunction.andThen(multiplyByTenFunction);
        
        BiFunction<Integer, Integer, Integer> compBiFunction = 
            incAndMult;
        
        System.out.println(composite.apply(1));
        System.out.println(incAndMult.apply(4, 100));
        System.out.println(upperCaseName.apply("iris", 17));
    }   

    static Function<Integer, Integer> incrementByOneFunction = 
        number -> number + 1;
    
    static Function<Integer, Integer> multiplyByTenFunction = 
        number -> number * 10;

    static BiFunction<Integer, Integer, Integer> incAndMult =
        (numToInc, numToMult) -> (numToInc + 1) * numToMult;
    
    static BiFunction<String, Integer, String> upperCaseName = (name, age) -> {
        if (name.isBlank()) {
            throw new IllegalArgumentException();
        } else {
            return name.toUpperCase() + " is of age: " + age;
        }
    };
}
