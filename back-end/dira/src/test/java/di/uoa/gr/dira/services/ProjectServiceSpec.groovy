package di.uoa.gr.dira.services

import com.sun.org.apache.xpath.internal.res.XPATHErrorResources_ja
import di.uoa.gr.dira.configuration.ModelMapperConfiguration
import di.uoa.gr.dira.configuration.spring.SpringProfiles
import di.uoa.gr.dira.entities.customer.Customer
import di.uoa.gr.dira.entities.project.Permission
import di.uoa.gr.dira.entities.project.Project
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException
import di.uoa.gr.dira.exceptions.project.ProjectAlreadyExistsException
import di.uoa.gr.dira.models.project.ProjectModel
import di.uoa.gr.dira.repositories.CustomerRepository
import di.uoa.gr.dira.repositories.PermissionRepository
import di.uoa.gr.dira.repositories.ProjectRepository
import di.uoa.gr.dira.services.permissionService.PermissionService
import di.uoa.gr.dira.services.projectService.ProjectService
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
class ProjectServiceSpec extends Specification {
    @Autowired
    private ModelMapper mapper
    private ProjectService service
    private final ProjectRepository projectRepository = Mock()
    private final CustomerRepository customerRepository = Mock()
    private final PermissionRepository permissionRepository = Mock()
    private final PermissionService permissionService = Mock()

    void setup() {
        service = new ProjectService(projectRepository, customerRepository, permissionService, mapper)
    }

    def "find all public projects"() {
        given: "We have a collection of PRIVATE and PUBLIC projects"
        List<Project> projects = ObjectGenerator.generateObjectList(Project.class, 4)
        projects[0].setVisibility(ProjectVisibility.PUBLIC)
        projects[1].setVisibility(ProjectVisibility.PRIVATE)
        projects[2].setVisibility(ProjectVisibility.PUBLIC)
        projects[3].setVisibility(ProjectVisibility.PRIVATE)
        projectRepository.findAll() >> projects

        when: "We ask for only the PUBLIC projects"
        List<ProjectModel> publicProjects = service.findAllPublicProjects()

        then: "We get the PUBLIC projects"
        publicProjects.size() == 2
        publicProjects[0] == mapper.map(projects[0], ProjectModel.class)
        publicProjects[1] == mapper.map(projects[2], ProjectModel.class)
    }

    def "get public project when customer has STANDARD plan"() {
        given: "We have a public project"
        Project project = ObjectGenerator.generateObject(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)
        projectRepository.findById(project.getId()) >> Optional.of(project)

        and: "We have a customer with STANDARD plan"
        Customer customer = ObjectGenerator.generateObject(Customer.class)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customerRepository.findByUsername("test") >> Optional.of(customer)

        when: "The customer requests to retrieve the public project"
        SubscriptionPlanEnum subscriptionPlanEnum = customerRepository.findByUsername("test").get().getSubscriptionPlan().getPlan()
        ProjectModel projectModel = service.getProject(project.getId(), subscriptionPlanEnum)

        then: "We get the project"
        projectModel == mapper.map(project, ProjectModel.class)
    }

    def "return exception when customer with STANDARD plan requests for private project"() {
        given: "We have a private project"
        Project project = ObjectGenerator.generateObject(Project.class)
        project.setVisibility(ProjectVisibility.PRIVATE)
        projectRepository.findById(project.getId()) >> Optional.of(project)

        and: "We have a customer with STANDARD plan"
        Customer customer = ObjectGenerator.generateObject(Customer.class)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customerRepository.findByUsername("test") >> Optional.of(customer)

        when: "The customer requests to retrieve the private project"
        SubscriptionPlanEnum subscriptionPlanEnum = customerRepository.findByUsername("test").get().getSubscriptionPlan().getPlan()
        service.getProject(project.getId(), subscriptionPlanEnum)

        then: "I get an error that action not permitted"
        ActionNotPermittedException ex = thrown(ActionNotPermittedException)
        ex.message == "Action Not Permitted!"
    }

    def "try to create a project while already exists"() {
        given: "we have a customer with STANDARD plan"
        Customer customer = ObjectGenerator.generateObject(Customer.class)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customerRepository.findById(customer.getId()) >> Optional.of(customer)

        and: "a project in db"
        Project project = ObjectGenerator.generateObject(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)
        projectRepository.findByKey(project.getKey()) >> Optional.of(project)

        and: "a given project model retrieved from frontend"
        ProjectModel projectModel = ObjectGenerator.generateObject(ProjectModel.class)
        projectModel.setKey(project.getKey())

        when: "we create a project which already exists"
        service.createProject(customer.getId(), projectModel)

        then: "we get an ProjectAlreadyExistsException error"
        ProjectAlreadyExistsException ex = thrown(ProjectAlreadyExistsException)
        ex.message == String.format("Project with projectId %s already exists", projectModel.getId().toString())
    }

    def "a user with STANDARD plan gets exception when tries to create a private project"() {
        given: "a user with standard plan"
        Customer customer = ObjectGenerator.generateObject(Customer.class)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customerRepository.findById(customer.getId()) >> Optional.of(customer)

        and: "a project model"
        ProjectModel projectModel = ObjectGenerator.generateObject(ProjectModel.class)
        projectModel.setVisibility(ProjectVisibility.PRIVATE)
        projectModel.setKey("blabla")
        projectRepository.findByKey(projectModel.getKey()) >> Optional.empty()

        when: "this user tries to create a private project"
        service.createProject(customer.getId(), projectModel)

        then: "I get error that action not permitted"
        ActionNotPermittedException ex = thrown(ActionNotPermittedException)
        ex.message == "Action Not Permitted!"
    }

    def "a user creates a project and gets its model"() {
        given: "a user"
        Customer customer = ObjectGenerator.generateObject(Customer.class)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customerRepository.findById(customer.getId()) >> Optional.of(customer)

        and: "a project model"
        ProjectModel projectModel = ObjectGenerator.generateObject(ProjectModel.class)
        projectModel.setVisibility(ProjectVisibility.PUBLIC)
        projectModel.setId(null)
        projectModel.setKey("blabla")
        projectRepository.findByKey(projectModel.getKey()) >> Optional.empty()
        projectRepository.save(_ as Project) >> {Project project -> project}

        and: "a given permission"
        permissionRepository.save(_ as Permission) >> { Permission perm -> perm }
        permissionService.getRepository() >> permissionRepository

        when: "this user creates a project"
        ProjectModel createdModel = service.createProject(customer.getId(), projectModel)

        then: "I get the project model"
        createdModel != null
        createdModel == projectModel
    }
}
