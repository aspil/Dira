package di.uoa.gr.dira.repositories

import di.uoa.gr.dira.entities.customer.Customer
import di.uoa.gr.dira.shared.SubscriptionPlanEnum
import di.uoa.gr.dira.utils.ObjectGenerator
import org.jeasy.random.EasyRandomParameters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerRepositorySpec extends Specification {
    @Autowired
    private CustomerRepository repository

    void setup() {
        ObjectGenerator.initializeDefaultTypeParameters()
    }

    void "insert single customer works"() {
        given: "I have a customer"
        Customer customer = ObjectGenerator.generateObjectWithDefaultTypeParams(Customer.class)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)

        when: "I insert the customer to the database"
        Customer saved = repository.save(customer)

        then: "The customer is saved successfully"
        saved
        saved.getId() == 2L
    }

    void "insert two customers works"() {
        given: "I have two customers"
        List<Customer> customers = ObjectGenerator.generateObjectListWithDefaultTypeParams(Customer.class, 2)
        customers[0].setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customers[0].setUsername("User1")
        customers[1].setEmail("user1@otenet.gr")
        customers[1].setSubscriptionPlanFromEnum(SubscriptionPlanEnum.PREMIUM)
        customers[1].setUsername("User2")
        customers[1].setEmail("user2@otenet.gr")

        when: "I insert the customers to the database"
        List<Customer> saved = repository.saveAll(customers)

        then: "The customers are saved successfully"
        saved.size() == 2
        saved[0].getId() == 2L
        saved[1].getId() == 3L
    }

    void "insert customer with same username throws exception"() {
        given: "I have a customer"
        String username = "usernamer"
        Customer customer = ObjectGenerator.generateObjectWithDefaultTypeParams(Customer.class)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customer.setUsername(username)

        and: "I insert the customer in the database"
        repository.save(customer)

        when: "I try to insert a customer with the same username"
        Customer secondCustomer = ObjectGenerator.generateObjectWithDefaultTypeParams(Customer.class)
        secondCustomer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        secondCustomer.setUsername(username)
        repository.save(secondCustomer)

        then: "An exception is thrown due to unique constraint"
        thrown(DataIntegrityViolationException)
    }

    void "retrieve existing customer by username"() {
        given: "I have a customer in my database with username 'tester'"
        String username = "tester"

        when: "I search for that user by username"
        // This user exists by default in our database
        Optional<Customer> customer = repository.findByUsername(username)

        then: "The user is retrieved"
        customer.isPresent()
        customer.get().getUsername() == username
    }

    void "retrieve existing customer by email"() {
        given: "I have a customer in my database with email 'test@otenet.gr'"
        String email = "test@otenet.gr"

        when: "I search for that user by email"
        // This user exists by default in our database
        Optional<Customer> customer = repository.findByEmail(email)

        then: "The user is retrieved"
        customer.isPresent()
        customer.get().getEmail() == email
    }

    void "retrieve non existent user"() {
        given: "I have a username to search for"
        String username = "non-existent"

        and: "There's no user with that username in my database"

        when: "I search for that user by username"
        Optional<Customer> customer = repository.findByUsername(username)

        then: "THe user is not found"
        customer.isEmpty()
    }
}
