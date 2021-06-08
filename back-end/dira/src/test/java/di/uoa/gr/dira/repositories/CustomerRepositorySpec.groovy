package di.uoa.gr.dira.repositories

import di.uoa.gr.dira.entities.customer.Customer
import di.uoa.gr.dira.shared.SubscriptionPlanEnum
import di.uoa.gr.dira.utils.ObjectGenerator
import org.jeasy.random.EasyRandomParameters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerRepositorySpec extends Specification {
    @Autowired
    private CustomerRepository repository

    void "insert single customer works"() {
        given: "I have a customer"
        EasyRandomParameters parameters = new EasyRandomParameters()
        parameters.setCollectionSizeRange(new EasyRandomParameters.Range<Integer>(0, 0))
        Customer customer = ObjectGenerator.generateObject(Customer.class, parameters)
        customer.setId(null)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)

        when: "I insert the customer to the database"
        Customer saved = repository.save(customer)

        then: "The customer is saved successfully"
        saved
        saved.getId() == 2L
    }

    void "insert two customers works"() {
        given: "I have two customers"
        EasyRandomParameters parameters = new EasyRandomParameters()
        parameters.setCollectionSizeRange(new EasyRandomParameters.Range<Integer>(0, 0))
        List<Customer> customers = ObjectGenerator.generateObjectList(Customer.class, parameters, 2)
        customers[0].setId(null)
        customers[0].setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customers[1].setId(null)
        customers[1].setSubscriptionPlanFromEnum(SubscriptionPlanEnum.PREMIUM)

        when: "I insert the customers to the database"
        List<Customer> saved = repository.saveAll(customers)

        then: "The customers are saved successfully"
        saved.size() == 2
        saved[0].getId() == 2L
        saved[1].getId() == 3L
    }
}
