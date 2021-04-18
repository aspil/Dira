package di.uoa.gr.dira.services

import di.uoa.gr.dira.models.customer.CustomerModel
import di.uoa.gr.dira.repositories.CustomerRepository
import di.uoa.gr.dira.services.customerService.CustomerService
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

    void "customer not in db is not returned"() {
        given: "the database has no record of the customer"
        repository.findById(1L) >> Optional.empty()

        when: "we search for the customer"
        CustomerModel customer = service.findById(1L)

        then: "the customer returned should be null"
        customer == null
    }
}
