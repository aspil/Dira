package di.uoa.gr.dira.services

import di.uoa.gr.dira.entities.customer.Customer
import di.uoa.gr.dira.entities.issue.Issue
import di.uoa.gr.dira.models.customer.CustomerModel
import di.uoa.gr.dira.models.issue.IssueModel
import di.uoa.gr.dira.repositories.CustomerRepository
import di.uoa.gr.dira.repositories.IssueRepository
import di.uoa.gr.dira.services.customerService.CustomerService
import di.uoa.gr.dira.services.issueService.IssueService
import di.uoa.gr.dira.shared.SubscriptionPlanEnum
import spock.lang.Specification

class IssueServiceSpec extends Specification{

    private IssueService service
    private IssueRepository repository

    void setup(){
        repository = Stub(CustomerRepository.class)
        service = new CustomerService(repository)
    }

    static Issue exampleIssue() {

        Customer customer = new Customer()
        customer.setId(1L)
        customer.setName("Tester")
        customer.setSurname("McTester")
        customer.setUsername("test")
        customer.setEmail("test@gmail.com")
        customer.setPassword("safe")
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)

        Issue issue = new Issue()
        issue.setId(1L)
        issue.setPriority(10)
        issue.setKey("key")
        issue.setTitle("Test")
        issue.setAssignee(customer)
        return issue
    }

    void "issue not in db is not returned"() {
        given: "the database has no record of this issue"
        repository.findById(1L) >> Optional.empty()

        when: "we search for the issue"
        IssueModel issue = service.findById(1L)

        then: "the customer returned should be null"
        issue == null
    }

    void "issue with id is returned"() {
        given: "there's an issue with an id"
        Issue issue = exampleIssue()

        and: "the database has a record of the issue with specific id"
        repository.findById(1L)

        when: "we search the issue by id"
        IssueModel model = service.findById(1L)

        then: "the issue is returned"
        model.getId() == issue.getId()
    }

}
