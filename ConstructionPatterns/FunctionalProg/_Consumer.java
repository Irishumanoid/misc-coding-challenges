import java.util.function.Consumer;
import java.util.function.BiConsumer;

public class _Consumer {
    public static void main(String[] args) {
        Customer customer = new Customer("pleb", "666");
        Customer.greetCustomer.accept(customer);
        Customer.greetCustomerV2.accept(customer, true);
        customerTester(customer.customerName, customer.customerPhoneNumber, name -> 
            {
                System.out.println("no phone number provided for " + name);
            });
            
        customerTester(customer.customerName, null, () -> 
        {
            System.out.println("no phone number provided");
        });
    }

    static void customerTester(String name, String number, Consumer<String> callback) {
        if (number != null) {
            System.out.printf("The phone number is %s for %s", number, name);
        } else {
            callback.accept(name);
        }
    }

    static void customerTester(String name, String number, Runnable callback) {
        if (number != null) {
            System.out.printf("The phone number is %s for %s", number, name);
        } else {
            callback.run();
        }
    }

    static class Customer {
        private final String customerName;
        private final String customerPhoneNumber;

        Customer(String customerName, String customerPhoneNumber) {
            this.customerName= customerName;
            this.customerPhoneNumber = customerPhoneNumber;
        }

        static BiConsumer<Customer, Boolean> greetCustomerV2 = (customer, showNumber) -> 
        System.out.println("Hello " + customer.customerName + 
            (showNumber ? 
                ", thanks for registering phone number " + customer.customerPhoneNumber:
                ", no visible phone number"));

        static Consumer<Customer> greetCustomer = customer -> 
            System.out.println("Hello " + customer.customerName + 
                ", thanks for registering phone number "
                + customer.customerPhoneNumber);

        static void greetCustomer(Customer customer) {
            System.out.println("Hello " + customer.customerName + 
                ", thanks for registering phone number "
                + customer.customerPhoneNumber);
        }

        @Override
        public String toString() {
            return "Customer{" + 
                "name=" + customerName + '\'' +
                ", phoneNumber= " + customerPhoneNumber +
                "}";
        } 
    }
}