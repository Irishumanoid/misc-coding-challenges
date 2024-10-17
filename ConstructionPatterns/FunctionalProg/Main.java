import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.lang.IllegalStateException;
import CombinatorPattern.Customer;
import CombinatorPattern.CustomerValidatorService;
import CombinatorPattern.CustomerValidatorService.ValidationResult;
import CustomerRegistrationValidator.ValidationResult;
import CombinatorPattern.CustomerRegistrationValidator;


public class Main {
    public static void main(String[] args) {
        List<Person> people = List.of(
            new Person("Pleb", Gender.MALE),
            new Person("Plebette", Gender.FEMALE),
            new Person("Jeffette", Gender.ANONYMOUS)
        );

        List<Person> females = new ArrayList<>();
        Predicate<Person> pred = p -> Gender.FEMALE.equals(p.gender);

        people.stream()
            .filter(pred)
            .collect(Collectors.toList())
            .forEach(System.out::println);
        
        people.stream()
            .map(person -> person.name)
            .mapToInt(String::length)
            .forEach(System.out::println);
        
        boolean someFemales = people.stream()
            .anyMatch(person -> Gender.FEMALE.equals(person.gender));
        System.out.println(someFemales);

        Object value = Optional.ofNullable("hi")
            .orElseThrow(() -> new IllegalStateException("bad"));
        System.out.println(value);

        Optional.ofNullable(null)
            .ifPresentOrElse(email -> System.out.println("sending email to " + email),
            () -> {
                System.out.println("cannot send email");
            }
            );
        
        Customer customer = new Customer("pleb", "pleb@plebmail.com", "+0666", LocalDate.of(2000, 01, 14));
        CustomerValidatorService service = new CustomerValidatorService();
        System.out.println(service.isValid(customer));

        ValidationResult res = CustomerRegistrationValidator
            .isEmailValid()
            .and(CustomerRegistrationValidator.isPhoneNumberValid())
            .and(CustomerRegistrationValidator.isAdult())
            .apply(customer);

        if (res != ValidationResult.SUCCESS) {
            throw new IllegalStateException("user not valid");
        }
    }

    static class Person {
        private final String name;
        private final Gender gender;

        Person(String name, Gender gender) {
            this.name = name;
            this.gender = gender;
        }


        @Override
        public String toString() {
            return "Person{" + 
                "name= " + name +
                ", gender= " + gender +
                "}";
        }
    }

    enum Gender {
        MALE,
        FEMALE,
        ANONYMOUS
    }
}
