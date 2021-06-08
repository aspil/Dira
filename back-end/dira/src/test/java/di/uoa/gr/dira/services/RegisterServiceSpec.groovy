package di.uoa.gr.dira.services

import di.uoa.gr.dira.exceptions.customer.CustomerAlreadyExistsException
import di.uoa.gr.dira.models.customer.CustomerModel
import di.uoa.gr.dira.services.customerService.ICustomerService
import di.uoa.gr.dira.services.registerService.RegisterService
import di.uoa.gr.dira.utils.ObjectCloner
import di.uoa.gr.dira.utils.ObjectGenerator
import spock.lang.Specification

class RegisterServiceSpec extends Specification {
    private RegisterService registerService
    private final ICustomerService customerService = Mock()

    void setup() {
        registerService = new RegisterService(customerService)
    }

    void "register new customer"() {
        given: "I have a new customer that wants to register"
        CustomerModel customerModel = ObjectGenerator.generateObject(CustomerModel.class)

        and: "There's no other customer in the system with the same username or email"
        customerService.findByUsername(customerModel.getUsername()) >> Optional.empty()
        customerService.findByEmail(customerModel.getEmail()) >> Optional.empty()
        CustomerModel saved = ObjectCloner.deepCloneObject(customerModel, CustomerModel.class);
        saved.setId(1L)
        customerService.save(customerModel) >> saved

        when: "I register the new user"
        CustomerModel registeredCustomer = registerService.registerCustomer(customerModel)

        then: "The registration is successful"
        registeredCustomer == saved
    }

    void "register new customer with existing username fails"() {
        given: "I have a new customer that wants to register"
        CustomerModel customerModel = ObjectGenerator.generateObject(CustomerModel.class)

        and: "There's another customer in the system with the same username"
        customerService.findByUsername(customerModel.getUsername()) >> Optional.of(customerModel)

        when: "I register the new user"
        registerService.registerCustomer(customerModel)

        then: "The registration fails"
        CustomerAlreadyExistsException ex = thrown(CustomerAlreadyExistsException)
        ex.message == String.format("Customer with username %s already exists", customerModel.getUsername())
    }

    void "register new customer with existing email fails"() {
        given: "I have a new customer that wants to register"
        CustomerModel customerModel = ObjectGenerator.generateObject(CustomerModel.class)

        and: "There's another customer in the system with the same email"
        customerService.findByUsername(customerModel.getUsername()) >> Optional.empty()
        customerService.findByEmail(customerModel.getEmail()) >> Optional.of(customerModel)

        when: "I register the new user"
        registerService.registerCustomer(customerModel)

        then: "The registration fails"
        CustomerAlreadyExistsException ex = thrown(CustomerAlreadyExistsException)
        ex.message == String.format("Customer with email %s already exists", customerModel.getEmail())
    }
}
