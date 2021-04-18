package di.uoa.gr.dira.services

import di.uoa.gr.dira.entities.Customer
import di.uoa.gr.dira.models.customer.CustomerModel
import di.uoa.gr.dira.repositories.CustomerRepository
import di.uoa.gr.dira.services.customerService.CustomerService
import di.uoa.gr.dira.shared.SubscriptionPlanEnum
import spock.lang.Shared;
import spock.lang.Specification;

class CustomerServiceSpec extends Specification {
    @Shared
    private CustomerService service
    private CustomerRepository repository

    void setup() {
        repository = Stub(CustomerRepository.class)
        service = new CustomerService(repository)
    }

    static Customer exampleCustomer() {
        Customer customer = new Customer()
        customer.setId(1L)
        customer.setName("Tester")
        customer.setSurname("McTester")
        customer.setUsername("test")
        customer.setEmail("test@gmail.com")
        customer.setPassword("safe")
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        return customer
    }

    void "customer not in db is not returned"() {
        given: "the database has no record of the customer"
        repository.findById(1L) >> Optional.empty()

        when: "we search for the customer"
        CustomerModel customer = service.findById(1L)

        then: "the customer returned should be null"
        customer == null
    }

    void "customer with username is returned"() {
        given: "there's a customer with some username"
        Customer customer = exampleCustomer()

        and: "the database has a record of the customer with specific username"
        repository.findByUsername("test") >> Optional.of(customer)

        when: "we search the customer by username"
        CustomerModel model = service.findByUsername("test")

        then: "the customer is returned"
        model.getId() == customer.getId()
        model.getName() == customer.getName()
        model.getSurname() == customer.getSurname()
        model.getUsername() == customer.getUsername()
        model.getEmail() == customer.getEmail()
        model.getSubscriptionPlan() == customer.getSubscriptionPlan().getPlan()
    }
}
