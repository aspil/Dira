package di.uoa.gr.dira.services

import di.uoa.gr.dira.configuration.ModelMapperConfiguration
import di.uoa.gr.dira.configuration.spring.SpringProfiles
import di.uoa.gr.dira.entities.customer.Customer
import di.uoa.gr.dira.entities.issue.Issue
import di.uoa.gr.dira.entities.project.Project
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException
import di.uoa.gr.dira.models.issue.IssueCreateModel
import di.uoa.gr.dira.models.issue.IssueModel
import di.uoa.gr.dira.models.project.ProjectIssuesModel
import di.uoa.gr.dira.repositories.CustomerRepository
import di.uoa.gr.dira.repositories.IssueCommentRepository
import di.uoa.gr.dira.repositories.IssueFixVersionRepository
import di.uoa.gr.dira.repositories.IssueLabelRepository
import di.uoa.gr.dira.repositories.IssueLinkRepository
import di.uoa.gr.dira.repositories.IssueRepository
import di.uoa.gr.dira.repositories.PermissionRepository
import di.uoa.gr.dira.repositories.ProjectRepository
import di.uoa.gr.dira.services.issueService.IssueService
import di.uoa.gr.dira.services.permissionService.IPermissionService
import di.uoa.gr.dira.shared.IssuePriorityEnum
import di.uoa.gr.dira.shared.IssueStatusEnum
import di.uoa.gr.dira.shared.IssueTypeEnum
import di.uoa.gr.dira.shared.PermissionType
import di.uoa.gr.dira.shared.ProjectVisibility
import di.uoa.gr.dira.shared.SubscriptionPlanEnum
import di.uoa.gr.dira.utils.ObjectGenerator
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification


@ActiveProfiles(SpringProfiles.TEST)
@ContextConfiguration(classes = ModelMapperConfiguration.class)
class IssueServiceSpec  extends Specification {
    @Autowired
    private ModelMapper mapper
    private IssueService service
    IssueRepository repository = Mock()
    ProjectRepository projectRepository = Mock()
    CustomerRepository customerRepository = Mock()
    IssueFixVersionRepository fixVersionRepository = Mock()
    IssueLabelRepository labelRepository = Mock()
    IssueCommentRepository commentRepository = Mock()
    IssueLinkRepository linkRepository = Mock()
    IPermissionService permissionService = Mock()
    PermissionRepository permissionRepository = Mock()
    ModelMapper mapper = Mock()

    void setup() {
        service = new IssueService(repository,
                projectRepository,
                customerRepository,
                fixVersionRepository,
                labelRepository,
                commentRepository,
                linkRepository,
                permissionService,
                mapper)
    }

    static void populateIssue(Issue issue,
                              Project project,
                              Customer reporter,
                              Customer assignee,
                              IssueTypeEnum type,
                              IssueStatusEnum status,
                              IssuePriorityEnum priority) {
        issue.setProject(project)
        issue.setType(type)
        issue.setStatus(status)
        issue.setLabels(null)
        issue.setComments(null)
        issue.setFixVersions(null)
        issue.setIssueLinks(null)
        issue.setReporter(reporter)
        issue.setAssignee(assignee)
        issue.setPriority(priority)
        issue.setResolved(false)
    }

    static void populateIssueModel(IssueModel issueModel,
                                   IssueCreateModel issueCreateModel,
                                   Project project,
                                   Customer reporter,
                                   Customer assignee,
                                   IssueStatusEnum status) {
        issueModel.setEpicId(null)
        issueModel.setEpicLinks(null)
        issueModel.setKey(String.format("%s-%d", project.getKey(), project.getIssues().size()))
        issueModel.setTitle(issueCreateModel.getTitle())
        issueModel.setDescription(issueCreateModel.getDescription())
        issueModel.setProjectName(project.getName())
        issueModel.setType(issueCreateModel.getType())
        issueModel.setStatus(status)
        issueModel.setLabels(null)
        issueModel.setComments(null)
        issueModel.setFixVersions(null)
        issueModel.setIssueLinks(null)
        if (reporter != null) {
            issueModel.setReporter(reporter.getName())
        }
        else {
            issueModel.setReporter(null)
        }
        if (assignee != null) {
            issueModel.setAssignee(assignee.getName())
        }
        else {
            issueModel.setAssignee(null)
        }
        issueModel.setPriority(issueCreateModel.getPriority())
        issueModel.setCreated(null)
        issueModel.setUpdated(null)
        issueModel.setDueDate(null)
        issueModel.setResolved(false)
    }

    static void populateIssueCreateModel(IssueCreateModel issueCreateModel, Customer assignee, IssueTypeEnum type, IssuePriorityEnum priority) {
        issueCreateModel.setEpicId(null)
        issueCreateModel.setType(type)
        issueCreateModel.setPriority(priority)
        if (assignee != null) {
            issueCreateModel.setAssigneeId(assignee.getId())
        }
        else {
            issueCreateModel.setAssigneeId(null)
        }
    }

    def "find all issues within a project" () {
        given: "two customers"
        Customer reporter = ObjectGenerator.generateObject(Customer.class)
        reporter.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)

        and: "a project that the issues belong to"
        Project project = ObjectGenerator.generateObject(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)

        and: "a list with one issue"
        List<Issue> issues = ObjectGenerator.generateObjectList(Issue.class, 1)
        populateIssue(issues[0], project, reporter, null, IssueTypeEnum.Epic, IssueStatusEnum.New, IssuePriorityEnum.Normal)

        project.setIssues(issues)
        projectRepository.findById(project.getId()) >> project

        when: "We ask for all the issues in the project"
        ProjectIssuesModel projectIssuesModel = service.findAllIssuesByProjectId(project.getId()) // TODO: fix StackOverflowError

        then: "We get all the issues"
//        projectIssuesModel.getIssues().size() == 1
//        projectIssuesModel.getIssues()[0] == mapper.map(issues[0], IssueModel.class)
    }

    def "a customer with WRITE permissions creates a new issue"() {
        given: "a customer "
        Customer reporter = ObjectGenerator.generateObject(Customer.class)
        reporter.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customerRepository.findById(reporter.getId()) >> Optional.of(reporter)

        and: "a project"
        Project project = ObjectGenerator.generateObject(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)
        projectRepository.findById(project.getId()) >> Optional.of(project)

        and: "the customer has WRITE permissions"
        permissionService.checkProjectUserPermissions(reporter.getId(), project, PermissionType.WRITE) >> true

        and: "an issue model given from the front end"
            IssueCreateModel issueCreateModel = ObjectGenerator.generateObject(IssueCreateModel.class)
            populateIssueCreateModel(issueCreateModel, null, IssueTypeEnum.Epic, IssuePriorityEnum.Major)

        and: "an issue model that will be returned to front end"
        IssueModel issueModel = ObjectGenerator.generateObject(IssueModel.class)
        populateIssueModel(issueModel, issueCreateModel, project, reporter, null, IssueStatusEnum.New)

        repository.save(_ as Issue) >> {Issue issue -> issue}

        when: "they try to create a new issue"
        IssueModel createdModel = service.createIssueWithProjectId(project.getId(), reporter.getId(), issueCreateModel)
        /* We need to nullify these dates as we cannot know those beforehand */
        createdModel.setCreated(null)
        createdModel.setUpdated(null)

        then: "the model of the new issue is returned"
        createdModel == issueModel
    }

    def "a customer without WRITE permissions tries to create a new issue"() {
        given: "a customer "
        Customer reporter = ObjectGenerator.generateObject(Customer.class)
        reporter.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customerRepository.findById(reporter.getId()) >> Optional.of(reporter)

        and: "a project"
        Project project = ObjectGenerator.generateObject(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)
        projectRepository.findById(project.getId()) >> Optional.of(project)

        and: "the customer has READ permissions"
        permissionService.checkProjectUserPermissions(reporter.getId(), project, PermissionType.WRITE) >> false

        and: "an issue model given from the front end"
        IssueCreateModel issueCreateModel = ObjectGenerator.generateObject(IssueCreateModel.class)
        populateIssueCreateModel(issueCreateModel, null, IssueTypeEnum.Epic, IssuePriorityEnum.Major)

        and: "an issue model that will be returned to front end"
        IssueModel issueModel = ObjectGenerator.generateObject(IssueModel.class)
        populateIssueModel(issueModel, issueCreateModel, project, reporter, null, IssueStatusEnum.New)

        repository.save(_ as Issue) >> {Issue issue -> issue}

        when: "they try to create a new issue"
        IssueModel createdModel = service.createIssueWithProjectId(project.getId(), reporter.getId(), issueCreateModel)
        /* We need to nullify these dates as we cannot know those beforehand */
        createdModel.setCreated(null)
        createdModel.setUpdated(null)

        then: "an error is given stating the the action is not permitted"
        ActionNotPermittedException ex = thrown(ActionNotPermittedException)
        ex.message == "You need WRITE permissions in order to create an issue"
    }

    def "a customer with READ permissions retrieves an issue"() {
        given: "a customer "
        Customer customer = ObjectGenerator.generateObject(Customer.class)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)

        and: "a project"
        Project project = ObjectGenerator.generateObject(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)
        projectRepository.findById(project.getId()) >> Optional.of(project)

        and: "an issue inside the project"
        Issue issue = ObjectGenerator.generateObject(Issue.class)
        populateIssue(issue, project, null, null, IssueTypeEnum.Epic, IssueStatusEnum.New, IssuePriorityEnum.Normal)

        project.getIssues().add(issue)

        and: "the customer has READ permissions"
        permissionService.checkProjectUserPermissions(customer.getId(), project, PermissionType.READ) >> true

        when: "the customer tries to fetch the issue"
        Optional<IssueModel> issueModel = service.findIssueWithProjectId(project.getId(), customer.getId(), issue.getId())

        then: "the model is present and returned"
        issueModel.isPresent()
        IssueModel model = issueModel.get()
        model == mapper.map(issue, IssueModel.class)
    }

    def "a customer without READ permissions tries to retrieve an issue"() {
        given: "a customer "
        Customer customer = ObjectGenerator.generateObject(Customer.class)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)

        and: "a project"
        Project project = ObjectGenerator.generateObject(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)
        projectRepository.findById(project.getId()) >> Optional.of(project)

        and: "an issue inside the project"
        Issue issue = ObjectGenerator.generateObject(Issue.class)
        populateIssue(issue, project, null, null, IssueTypeEnum.Epic, IssueStatusEnum.New, IssuePriorityEnum.Normal)

        project.getIssues().add(issue)

        and: "the customer has READ permissions"
        permissionService.checkProjectUserPermissions(customer.getId(), project, PermissionType.READ) >> false

        when: "the customer tries to fetch the issue"
        Optional<IssueModel> issueModel = service.findIssueWithProjectId(project.getId(), customer.getId(), issue.getId())

        then: "an error is given stating the the action is not permitted"
        ActionNotPermittedException ex = thrown(ActionNotPermittedException)
        ex.message == "You need READ permissions in order to view an issue"
    }
}
