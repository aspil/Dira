package di.uoa.gr.dira.services

import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException
import di.uoa.gr.dira.models.customer.CustomerModel
import di.uoa.gr.dira.services.customerService.ICustomerService
import di.uoa.gr.dira.services.loginService.LoginService
import di.uoa.gr.dira.utils.ObjectGenerator
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Specification

class LoginServiceSpec extends Specification {
    private LoginService loginService
    private final AuthenticationManager authenticationManager = Mock()
    private final ICustomerService customerService = Mock()

    void setup() {
        loginService = new LoginService(authenticationManager, customerService)
    }

    void "login with existing user"() {
        given: "I have an already registered user"
        CustomerModel customerModel = ObjectGenerator.generateObject(CustomerModel.class)
        customerService.findByUsername(customerModel.getUsername()) >> Optional.of(customerModel)

        and: "The user is authenticated successfully"
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                customerModel.getUsername(),
                customerModel.getPassword()
        )
        authenticationManager.authenticate(authToken) >> authToken

        when: "I try to login with the user"
        CustomerModel authenticated = loginService.authenticateUser(customerModel.getUsername(), customerModel.getPassword())

        then: "The user is logged in successfully"
        authenticated == customerModel
    }

    void "login with non existing user"() {
        given: "I have a user that I would like to login with"
        CustomerModel customerModel = ObjectGenerator.generateObject(CustomerModel.class)

        and: "The user is not found during authentication"
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                customerModel.getUsername(),
                customerModel.getPassword()
        )
        authenticationManager.authenticate(authToken) >> { throw new CustomerNotFoundException("username", customerModel.getUsername()) }

        when: "I try to login with the user"
        CustomerModel authenticated = loginService.authenticateUser(customerModel.getUsername(), customerModel.getPassword())

        then: "I get an error that the user does not exist"
        CustomerNotFoundException ex = thrown(CustomerNotFoundException)
        ex.message == String.format("Customer with username %s was not found", customerModel.getUsername())
    }

    void "login with wrong credentials"() {
        given: "I have an already registered user"
        CustomerModel customerModel = ObjectGenerator.generateObject(CustomerModel.class)

        and: "The user provides wrong credentials during authentication"
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                customerModel.getUsername(),
                customerModel.getPassword()
        )
        authenticationManager.authenticate(authToken) >> { throw new BadCredentialsException("Wrong username or password") }

        when: "I try to login with the user"
        CustomerModel authenticated = loginService.authenticateUser(customerModel.getUsername(), customerModel.getPassword())

        then: "I get an error of wrong credentials"
        BadCredentialsException ex = thrown(BadCredentialsException)
        ex.message == "Wrong username or password"
    }
}
