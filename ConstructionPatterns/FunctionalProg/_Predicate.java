import java.util.function.Predicate;

public class _Predicate {
    public static void main(String[] args) {
        System.out.println(isPhoneNumberValid.or(containsNum3).test("07000003081"));
    }

    static Predicate<String> isPhoneNumberValid = phoneNumber ->
        phoneNumber.startsWith("07") && phoneNumber.length() == 11;
    
    static Predicate<String> containsNum3 = phoneNumber ->
        phoneNumber.contains("3");

    static boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.startsWith("07") && phoneNumber.length() == 11;
    }
 }
