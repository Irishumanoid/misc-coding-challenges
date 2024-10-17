import java.time.LocalDate;
import java.util.Objects;

public class Customer {
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final LocalDate dob;

    public Customer(String name, String email, String phoneNumber, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
    }

    public String getName() {
        return this.name;
    }


    public String getEmail() {
        return this.email;
    }


    public String getPhoneNumber() {
        return this.phoneNumber;
    }


    public LocalDate getDob() {
        return this.dob;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(phoneNumber, customer.phoneNumber) && Objects.equals(dob, customer.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, phoneNumber, dob);
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", dob='" + getDob() + "'" +
            "}";
    }
}
