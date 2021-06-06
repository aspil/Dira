package di.uoa.gr.dira.services

import di.uoa.gr.dira.configuration.ModelMapperConfiguration
import di.uoa.gr.dira.entities.customer.Customer
import di.uoa.gr.dira.models.customer.CustomerModel
import di.uoa.gr.dira.repositories.CustomerRepository
import di.uoa.gr.dira.services.customerService.CustomerService
import di.uoa.gr.dira.services.projectService.IProjectService
import di.uoa.gr.dira.shared.SubscriptionPlanEnum
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification;

@ContextConfiguration(classes = ModelMapperConfiguration.class)
class CustomerServiceSpec extends Specification {
    private CustomerService service
    private final IProjectService projectService = Mock()
    private final CustomerRepository customerRepository = Mock()

    @Autowired
    private ModelMapper mapper

    void setup() {
        service = new CustomerService(customerRepository, projectService, mapper)
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
        customerRepository.findById(1L) >> Optional.empty()

        when: "we search for the customer"
        Optional<CustomerModel> customer = service.findById(1L)

        then: "the customer returned should be null"
        customer.isEmpty()
    }

    void "customer with username is returned"() {
        given: "there's a customer with some username"
        Customer customer = exampleCustomer()

        and: "the database has a record of the customer with specific username"
        customerRepository.findByUsername("test") >> Optional.of(customer)

        when: "we search the customer by username"
        Optional<CustomerModel> optionalModel = service.findByUsername("test")

        then: "the customer is returned"
        optionalModel.isPresent()
        CustomerModel model = optionalModel.get()
        model.getId() == customer.getId()
        model.getName() == customer.getName()
        model.getSurname() == customer.getSurname()
        model.getUsername() == customer.getUsername()
        model.getEmail() == customer.getEmail()
        model.getSubscriptionPlan() == customer.getSubscriptionPlan().getPlan()
    }


}
