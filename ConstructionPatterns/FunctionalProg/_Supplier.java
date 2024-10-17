import java.util.function.Supplier;

public class _Supplier {
    public static void main(String[] args) {
        System.out.println(getDBConnUrlSupplier.get());
    }

    static Supplier<String> getDBConnUrlSupplier = () 
        -> "localhost:5800/users";

    static String getDBConnectionUrl() {
        return "localhost:5800/users";
    }
}
