package di.uoa.gr.dira.repositories

import di.uoa.gr.dira.entities.customer.Customer
import di.uoa.gr.dira.entities.issue.Issue
import di.uoa.gr.dira.entities.project.Project
import di.uoa.gr.dira.shared.ProjectVisibility
import di.uoa.gr.dira.utils.ObjectGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification


@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IssueRepositorySpec extends Specification {
    @Autowired
    private IssueRepository issueRepository

    @Autowired
    private ProjectRepository projectRepository

    @Autowired
    private CustomerRepository customerRepository

    void setup() {
        ObjectGenerator.initializeDefaultTypeParameters()
    }

    void "insert a new issue"() {
        given: "I have a customer and a project"
        Customer customer = customerRepository.findById(1L).get()

        Project project = ObjectGenerator.generateObjectWithDefaultTypeParams(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)
        project.getCustomers().add(customer)

        project = projectRepository.save(project)

        and: "The customer creates a new issue"
        Issue issue = ObjectGenerator.generateObjectWithDefaultTypeParams(Issue.class)
        issue.setProject(project)
        issue.setReporter(customer)
        project.getIssues().add(issue)
        when: "I save the project in the database"
        Issue saved = issueRepository.save(issue)

        then: "The project is saved successfully"
        saved
        saved.getId() == 1L
        saved.getProject() == project
        saved.getReporter() == customer
        project.getIssues().contains(saved)
    }

    void "insert two issues in the same project"() {
        given: "I have a customer and a project"
        Customer customer = customerRepository.findById(1L).get()

        Project project = ObjectGenerator.generateObjectWithDefaultTypeParams(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)
        project.getCustomers().add(customer)
        project = projectRepository.save(project)

        and: "The customer creates two issues under that project"
        List<Issue> listOfIssues = ObjectGenerator.generateObjectListWithDefaultTypeParams(Issue.class, 2)
        listOfIssues[0].setProject(project)
        listOfIssues[0].setReporter(customer)
        listOfIssues[1].setProject(project)
        listOfIssues[1].setReporter(customer)
        project.getIssues().addAll(listOfIssues)

        when: "I save the issues in the database"
        List<Issue> saved = issueRepository.saveAll(listOfIssues)

        then: "The issues are saved successfully"
        saved.size() == 2

        saved[0].getId() == 1L
        saved[0].getProject() == project
        saved[0].getReporter() == customer

        saved[1].getId() == 2L
        saved[1].getProject() == project
        saved[1].getReporter() == customer

        project.getIssues().containsAll(saved)
    }

    void "delete an existing issue"() {
        given: "I have an issue and the project it belongs in my database"
        Customer customer = customerRepository.findById(1L).get()

        Project project = ObjectGenerator.generateObjectWithDefaultTypeParams(Project.class)
        project = projectRepository.save(project)
        project.setVisibility(ProjectVisibility.PUBLIC)

        Issue issue = ObjectGenerator.generateObjectWithDefaultTypeParams(Issue.class)
        issue.setProject(project)
        issue.setReporter(customer)
        project.getIssues().add(issue)
        issueRepository.save(issue)

        when: "I try to delete the issue and remove it from the project"
        issueRepository.delete(issue)
        project.getIssues().remove(0)

        then: "The issue is deleted successfully"
        issueRepository.findById(issue.getId()).isEmpty()
        project.getIssues().size() == 0
    }
}
