package di.uoa.gr.dira.services

import di.uoa.gr.dira.configuration.ModelMapperConfiguration
import di.uoa.gr.dira.entities.customer.Customer
import di.uoa.gr.dira.models.customer.CustomerModel
import di.uoa.gr.dira.models.project.ProjectModel
import di.uoa.gr.dira.repositories.CustomerRepository
import di.uoa.gr.dira.services.customerService.CustomerService
import di.uoa.gr.dira.services.projectService.IProjectService
import di.uoa.gr.dira.utils.ObjectGenerator
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.EasyRandomParameters.Range
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = ModelMapperConfiguration.class)
class CustomerServiceSpec extends Specification {
    @Autowired
    private ModelMapper mapper
    private CustomerService service
    private final IProjectService projectService = Mock()
    private final CustomerRepository customerRepository = Mock()

    void setup() {
        service = new CustomerService(customerRepository, projectService, mapper)
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
        given: "there's a customer with username 'test'"
        Customer customer = ObjectGenerator.generateObject(Customer.class)
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

    void "retrieve the projects where the user belongs to"() {
        given: "there's a customer with id '1' and he is part of two projects"
        EasyRandomParameters parameters = new EasyRandomParameters()
        parameters.setCollectionSizeRange(new Range<Integer>(2, 2))
        Customer customer = ObjectGenerator.generateObject(Customer.class, parameters)
        customer.setId(1L)

        and: "the database returns the customer with id '1'"
        customerRepository.findById(1L) >> Optional.of(customer)

        when: "we retrieve the project where the user belongs to"
        List<ProjectModel> projects = service.getCustomerProjects(1L)

        then: "the projects of the customer are retrieved"
        projects.size() == 2
        projects[0] == mapper.map(customer.getProjects()[0], ProjectModel.class)
        projects[1] == mapper.map(customer.getProjects()[1], ProjectModel.class)
    }
}
