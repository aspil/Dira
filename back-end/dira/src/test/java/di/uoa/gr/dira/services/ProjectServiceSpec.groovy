package di.uoa.gr.dira.services

import di.uoa.gr.dira.configuration.ModelMapperConfiguration
import di.uoa.gr.dira.configuration.spring.SpringProfiles
import di.uoa.gr.dira.entities.customer.Customer
import di.uoa.gr.dira.entities.project.Permission
import di.uoa.gr.dira.entities.project.Project
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException
import di.uoa.gr.dira.models.project.ProjectModel
import di.uoa.gr.dira.repositories.CustomerRepository
import di.uoa.gr.dira.repositories.PermissionRepository
import di.uoa.gr.dira.repositories.ProjectRepository
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

    void setup() {
        service = new ProjectService(projectRepository, customerRepository, permissionRepository, mapper)
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
        ProjectModel projectModel = service.getProject(project.getId(), subscriptionPlanEnum)

        then: "I get an error that action not permitted"
        ActionNotPermittedException ex = thrown(ActionNotPermittedException)
        ex.message == "Action Not Permitted!"
    }

    def "a user with STANDARD plan gets exception when tries to create a private project"() {
        given: "a user with standard plan"
        Customer customer = ObjectGenerator.generateObject(Customer.class)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customerRepository.findById(customer.getId()) >> Optional.of(customer)

        and: "a project model"
        ProjectModel projectModel = ObjectGenerator.generateObject(ProjectModel.class)
        projectModel.setVisibility(ProjectVisibility.PRIVATE)

        when: "this user tries to create a private project"
        ProjectModel model = service.createProject(customer.getId(), projectModel)

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

        when: "this user creates a project"
        ProjectModel model = service.createProject(customer.getId(), projectModel)
        Project project = mapper.map(model, Project.class)

        then: "I get the project model"
        model != null
        model == projectModel
        project.visibility == projectModel.visibility
        project.issues != null
        project.customers != null
        project.getCustomers().size() == 1
        project.getCustomers().get(0).getId() == customer.getId()
        project.permissions != null
        project.getPermissions().size() == 1
        project.getPermissions().get(0).permission == PermissionType.ADMIN
    }

    def "a user with admin rights deletes a project with given id"() {
        given: "We have a public project"
        Project project = ObjectGenerator.generateObject(Project.class)
        project.setVisibility(ProjectVisibility.PUBLIC)
        projectRepository.findById(project.getId()) >> Optional.of(project)

        and: "We have a customer with STANDARD plan"
        Customer customer = ObjectGenerator.generateObject(Customer.class)
        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.STANDARD)
        customerRepository.findByUsername("test") >> Optional.of(customer)

        and: "admin permissions for that customer"
        Permission permission = ObjectGenerator.generateObject(Permission.class)
        permission.setPermission(PermissionType.ADMIN)
        permission.setUser(customer)
        project.getPermissions().add(permission)
        permissionRepository.findById(permission.getId()) >> Optional.of(permission)


        when: "the customer with admin rights deletes the project"
        service.deleteProjectWithId(project.getId(), customer.getId())
        customerRepository.findById(customer.getId()) >> Optional.of(customer)

        then: "the customer does not have the deleted project in his list"
        assert (customer.getProjects().contains(project.getId()))
    }
}
