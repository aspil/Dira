package di.uoa.gr.dira.repositories

import di.uoa.gr.dira.entities.customer.Customer
import di.uoa.gr.dira.shared.SubscriptionPlanEnum
import di.uoa.gr.dira.utils.ObjectGenerator
import org.jeasy.random.EasyRandomParameters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
@AutoConfigureTestDatabase
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
}
