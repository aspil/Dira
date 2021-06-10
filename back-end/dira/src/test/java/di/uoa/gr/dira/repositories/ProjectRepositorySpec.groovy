package di.uoa.gr.dira.repositories

import di.uoa.gr.dira.entities.customer.Customer
import di.uoa.gr.dira.entities.project.Project
import di.uoa.gr.dira.utils.ObjectGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProjectRepositorySpec extends Specification {
    @Autowired
    private ProjectRepository projectRepository

    @Autowired
    private CustomerRepository customerRepository

    void setup() {
        ObjectGenerator.initializeDefaultTypeParameters()
    }

    void "insert a repository"() {
        given: "I have a customer with STANDARD subscription plan"
        Customer customer = customerRepository.findById(1L).get()

        and: "The customer creates a new project"
        Project project = ObjectGenerator.generateObjectWithDefaultTypeParams(Project.class)
        project.getCustomers().add(customer)

        when: "I save the project in the database"
        Project saved = projectRepository.save(project)

        then: "The project is saved successfully"
        saved
        saved.getId() == 1L
        saved.getCustomers().first() == customer
    }
}
