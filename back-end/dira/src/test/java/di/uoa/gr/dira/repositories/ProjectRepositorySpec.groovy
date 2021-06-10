package di.uoa.gr.dira.repositories

import di.uoa.gr.dira.entities.customer.Customer
import di.uoa.gr.dira.entities.project.Project
import di.uoa.gr.dira.shared.ProjectVisibility
import di.uoa.gr.dira.shared.SubscriptionPlanEnum
import di.uoa.gr.dira.utils.ObjectGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

import javax.transaction.Transactional

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
        project.setVisibility(ProjectVisibility.PUBLIC)
        project.getCustomers().add(customer)

        when: "I save the project in the database"
        Project saved = projectRepository.save(project)

        then: "The project is saved successfully"
        saved
        saved.getId() == 1L
        saved.getCustomers().first() == customer
    }

    @Transactional
    void "insert a customer to existing repository"() {
        given: "I have an existing project in my database"
        Project project = ObjectGenerator.generateObjectWithDefaultTypeParams(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)
        project = projectRepository.save(project)

        and: "I have a customer that I would like to add into the repository"
        Customer customer = ObjectGenerator.generateObjectWithDefaultTypeParams(Customer.class)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customer = customerRepository.save(customer)

        when: "I add the customer into the repository and save the repository to the database"
        project.getCustomers().add(customer)
        Project saved = projectRepository.save(project)

        then: "The repository is saved successfully and the customer is part of the project"
        saved
        saved.getCustomers().contains(customer)
    }

    void "insert two projects for the same customer"() {
        given: "I have an existing customer in my database"
        Customer customer = customerRepository.findById(1L).get()

        and: "I create a new project with that customer"
        Project firstProject = ObjectGenerator.generateObjectWithDefaultTypeParams(Project.class)
        firstProject.setVisibility(ProjectVisibility.PUBLIC)
        firstProject.getCustomers().add(customer)

        and: "I create a second project with the same customer"
        Project secondProject = ObjectGenerator.generateObjectWithDefaultTypeParams(Project.class)
        secondProject.setVisibility(ProjectVisibility.PUBLIC)
        secondProject.getCustomers().add(customer)

        when: 'I save both repositories'
        Project firstSaved = projectRepository.save(firstProject)
        Project secondSaved = projectRepository.save(secondProject)

        then: "Both projects are saved successfully"
        firstSaved
        firstSaved.getCustomers().contains(customer)
        secondSaved
        secondProject.getCustomers().contains(customer)
    }

    @Transactional
    void "delete existing project without customers"() {
        given: "I have an existing project without any customers"
        Project project = ObjectGenerator.generateObjectWithDefaultTypeParams(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)
        project = projectRepository.save(project)
        Long projectId = project.getId()

        when: "I try to delete the project"
        projectRepository.delete(project)

        then: "The project is deleted successfully"
        projectRepository.findById(projectId).isEmpty()
    }

    @Transactional
    void "delete existing project with customers"() {
        given: "I have an existing project with customers"
        List<Customer> customers = ObjectGenerator.generateObjectListWithDefaultTypeParams(Customer.class, 2)
        customers[0].setUsername("User1")
        customers[0].setEmail("user1@otenet.gr")
        customers[0].setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)

        customers[1].setUsername("User2")
        customers[1].setEmail("user2@otenet.gr")
        customers[1].setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customers = customerRepository.saveAll(customers)

        Project project = ObjectGenerator.generateObjectWithDefaultTypeParams(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)
        project.getCustomers().addAll(customers)
        project = projectRepository.save(project)
        Long projectId = project.getId()

        when: "I try to delete the project"
        projectRepository.delete(project)

        then: "The project is deleted and the customers are no longer in the project"
        projectRepository.findById(projectId).isEmpty()
        customerRepository.findById(customers[0].getId()).get().getProjects().isEmpty()
        customerRepository.findById(customers[1].getId()).get().getProjects().isEmpty()
    }
}
