# Table of Contents

**[REST Endpoints](#rest-endpoints)**\
[User](#user)\
[Project](#project)\
[Issues](#issues)\
[Sprint](#sprint)

**[Class Diagram](#class-diagram)**

# Specs

## REST Endpoints

### User
- GET           /users          (retrieve all users)
- GET           /users/{userId} (retrieve user information based on userId)

- POST          /register       (register a new user)

- POST          /login          (perform a user login and produce a JWT token on successful login)
- POST          /logout         (logout a user and delete all their JWT tokens)

- POST          /keepalive      (refresh the user's JWT tokens)

### Project

- GET           /projects (retrieve all projects, depending on user visibility)
- POST          /projects (create a new project, return a ProjectModel with the new project info)

- GET           /projects/{projectID or projectKey} (retrieve project with given project ID or given project Key)
- PUT           /projects/{projectID or projectKey} (update a project with the given ID or project Key)
- DELETE        /projects/{projectID or projectKey} (delete a project with the given ID or project Key)

- GET           /projects/{projectID or projectKey}/users          (retrieve project's users)
- POST          /projects/{projectID or projectKey}/users          (add a new user to project)
- DELETE        /projects/{projectID or projectKey}/users/{userID} (delete a user from the project)

- GET           /projects/{projectID or projectKey}/users/permissions                (retrieve project's permissions for all users)
- POST          /projects/{projectID or projectKey}/users/permissions                (create a new user permission for the user)
- PUT           /projects/{projectID or projectKey}/users/permissions/{permissionID} (update permission with the given ID)
- DELETE        /projects/{projectID or projectKey}/users/permissions/{permissionID} (delete permission with the given ID)

### Issues

- GET           /projects/{projectID or projectKey}/issues (retrieve all issues for the given project)
- POST          /projects/{projectID or projectKey}/issues (create a new issue)

- GET           /projects/{projectID or projectKey}/issues/{issueID or issueKey} (retrieve the issue with the given ID under the given project)
- PUT           /projects/{projectID or projectKey}/issues/{issueID or issueKey} (update an issue with the given ID or issue Key)
- DELETE        /projects/{projectID or projectKey}/issues/{issueID or issueKey} (delete an issue with the given ID or issue Key)

### Sprint

- GET           /projects/{projectID or projectKey}/sprints (retrieve all sprints with the given ID)
- POST          /projects/{projectID or projectKey}/sprints (create a new sprint in the project with the given ID)

- GET           /projects/{projectID or projectKey}/sprints/{sprintID} (retrieve sprint with the given ID)
- PUT           /projects/{projectID or projectKey}/sprints/{sprintID} (update a sprint with the given ID)
- DELETE        /projects/{projectID or projectKey}/sprints/{sprintID} (delete the sprint with the given ID)


## Class Diagram

![Class Diagram](images/dira_class_diagram.jpg?raw=true "Class Diagram")
