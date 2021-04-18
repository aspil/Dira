package di.uoa.gr.dira.groovy.services

import di.uoa.gr.dira.entities.Customer
import di.uoa.gr.dira.models.customer.CustomerModel
import di.uoa.gr.dira.groovy.services.customerService.CustomerService
import di.uoa.gr.dira.repositories.CustomerRepository
import di.uoa.gr.dira.shared.SubscriptionPlanEnum
import spock.lang.Shared
import spock.lang.Specification

class CustomerServiceSpec extends Specification {
    private @Shared service;
    private repository;

    def setup() {
        service = CustomerService.newInstance()
        repository = Stub(CustomerRepository.class)
    }

    def "customer is not in the database"() {
        given: "the customer has no record for a specific customer"
        repository.findById(Customer.class, 1L) >> null

        when: "we search for the customer with id 1"
        customer = service.findById(1L)

        then: "the customer returned should be null"
        customer == null
    }

    def "customer with id is returned"() {
        given: "A customer username"
        username = "saiko"

        and: "A customer in our database with this username exists"
        customer = new Customer()
        customer.setId(1L)
        customer.setName("Kostas")
        customer.setSurname("Saidis")
        customer.setUsername("saiko")
        customer.setEmail("saiko@gmail.com")
        customer.setPassword("makaroniaMeSalts@")
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)

        and: "Repository returns that customer"
        repository.findById(1L) >> customer

        when: "We search for that customer by id"
        customerModel = service.findById(1L)

        then: "We get the customer back"
        expected = new CustomerModel()
        expected.setId(1L)
        expected.setName("Kostas")
        expected.setSurname("Saidis")
        expected.setUserName("saiko")
        expected.setEmail("saiko@gmail.com")
        expected.setPassword("makaroniaMeSalts@")
        expected.setSubscriptionPlan(SubscriptionPlanEnum.STANDARD)

        customerModel == expected
    }
}
