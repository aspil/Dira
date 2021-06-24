# Table of Contents

**[REST Endpoints](#rest-endpoints)**

**[Register](#register)**

**[Login](#login)**

**[Logout](#logout)**

**[User](#user)**
[Retrieve all users](#retrieve-all-users)\
[Get a user](#get-a-user)\
[Get a user's project](#get-a-users-project)\
[Update user's plan](#update-users-plan)\
[Delete all users](#delete-all-users)\
[Delete a user](#delete-a-users)\

**[Project](#project)**
[Get all projects](#get-all-projects)\
[Get a specific project](#get-a-specific-project)\
[Create a project](#create-a-project)\
[Update a project's information](#update-a-projects-information)\
[Delete a project](#delete-a-project)\

**[Project users](#project-users)**\
[Get a specific project](#get-a-specific-project)\
[Add user to a project](#add-user-to-a-project)\
[Remove a user from a project](#remove-a-user-from-a-project)\

**[Project permissions](#project-permissions)**\
[Get a specific project](#get-a-specific-project)\
[Create a permission](#create-a-permission)\
[Update a permission](#update-a-permission)\
[Delete a permission](#delete-a-permission)\

**[Issues](#issues)**\
[Get all issues in a project](#get-all-issues-in-a-project)\
[Get an issue in a project](#get-an-issue-in-a-project)\
[Create a new issue](#create-a-new-issue)\
[Update an issue](#update-an-issue)\



# REST Endpoints

- POST          /keepalive      (refresh the user's JWT tokens)

## Register

Register a new account to the system.

```
POST /register
```

#### Parameters

| Name               | Type   | In     | Description                                                  |
| ------------------ | ------ | ------ | ------------------------------------------------------------ |
| `Authorization`    | string | header | The authentication token required for this request           |
| `email`            | string | body   | **Required**. The email of the new user                      |
| `username`         | string | body   | **Required**. The unique username                            |
| `name`             | string | body   | **Required**. The name of the user                           |
| `surname`          | string | body   | **Required**. The surname of the user                        |
| `password`         | string | body   | **Required**. The password of the user                       |
| `subscriptionPlan` | string | body   | **Required**. The subscription type of the account. Can be either *PUBLIC* or *PRIVATE* |

#### Code samples

##### Shell

```bash
curl \
  -X POST \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{"email": "alexis.spiliot@gmail.com", "name": "alex", "password": "02081999", "subscriptionPlan": "PREMIUM", "surname": "spil", "username": "sleks"}' \
  http://localhost:8080/dira/register
```

#### Responses

##### Created

```
Status: 201 Created
```

```
[
  {
    "email": "email@example.com",
    "id": 0,
    "name": "John",
    "subscriptionPlan": "PREMIUM",
    "surname": "Smith",
    "username": "js504"
  }
]
```

#### Unauthorized

```
Status: 401 Unauthorized
```

#### Forbidden

```
Status: 403 Forbidden
```

#### Not Found

```
Status: 404 Not Found
```



## Login

```
POST /login
```

#### Parameters

| Name            | Type   | In     | Description                                        |
| --------------- | ------ | ------ | -------------------------------------------------- |
| `Authorization` | string | header | The authentication token required for this request |
| `username`      | string | body   | The username of the user requesting to login       |
| `password`      | string | body   | The password of the user's account                 |

#### Code samples

##### Shell

```
curl \
  -H "Accept: application/json" \
  https://localhost:8080/dira/users
```

#### Responses

##### OK

```
Status: 200 OK
```

```
{
  "email": "string",
  "id": 0,
  "name": "string",
  "subscriptionPlan": "PREMIUM",
  "surname": "string",
  "username": "string"
}
```

##### Created

```
Status: 201 Created
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```



## Logout

```
POST /logout
```

## User

### Retrieve all users

List all users, in the order that they singed up on Dira. It includes both standard and premium accounts.

```
GET  /dira/users
```

#### Parameters

| Name            | Type   | In     | Description                                        |
| --------------- | ------ | ------ | -------------------------------------------------- |
| `Authorization` | string | header | The authentication token required for this request |

#### Code samples

##### Shell

```bash
curl \
  -H "Accept: application/json" \
  https://localhost:8080/dira/users
```

#### Responses

##### OK

```
Status: 200 OK
```

```json
[
  {
    "email": "email@example.com",
    "id": 0,
    "name": "John",
    "subscriptionPlan": "PREMIUM",
    "surname": "Smith",
    "username": "js504"
  }
]
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Get a user

Provides publicly available information about a Dira user.

```
GET  /dira/users/{userId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `userId`        | integer | path   | The unique ID of the user to be fetched           |

#### Code samples

##### Shell

```bash
curl \
  -H "Accept: application/json" \
  https://localhost:8080/dira/users/USERID
```

#### Responses

##### OK

```
Status: 200 OK
```

```json
{
  "email": "email@example.com",
  "id": 0,
  "name": "John",
  "subscriptionPlan": "PREMIUM",
  "surname": "Smith",
  "username": "js504"
}
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Get a user's project

Provides the information of all the projects that the requested user participates into.

```
GET  /dira/users/{userId}/projects
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `userId`        | integer | path   | The unique ID of the user                         |

#### Code samples

##### Shell

```bash
curl \
  -H "Accept: application/json" \
  https://localhost:8080/dira/users/USERID/projects
```

#### Responses

##### OK

```
Status: 200 OK
```

```json
[
  {
    "description": "string",
    "id": 0,
    "key": "string",
    "name": "string",
    "visibility": "PRIVATE"
  }
]
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Update user's plan

Upgrade an account's subscription to Premium.

```
PUT  /dira/users/{userId}/updatePlan
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `userId`        | integer | path   | The unique ID of the user                         |

#### Code samples

##### Shell

```bash
curl \
  -X PUT "Accept: application/json" \
  -H "Accept: application/json" \
  https://localhost:8080/dira/users/USERID/projects
```

#### Responses

##### OK

```
Status: 200 OK
```

##### Created

```
Status: 201 Created
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Delete all users

Delete all information for every account in the system. 

```
DELETE  /dira/users
```

#### Parameters

| Name            | Type   | In     | Description                                       |
| --------------- | ------ | ------ | ------------------------------------------------- |
| `Authorization` | string | header | The authorization token required for this request |

#### Code samples

##### Shell

```bash
curl \
  -X DELETE \
  "Accept: application/json" \
  https://localhost:8080/dira/users
```

#### Responses

##### OK

```
Status: 200 OK
```

##### No Content

```
Status: 204 No Content
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

------

### Delete a user

Delete all information for the requested user. 

```
DELETE  /dira/users/{userId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `userId`        | integer | path   | The unique ID of the user                         |

#### Code samples

##### Shell

```bash
curl \
  -X DELETE \
  "Accept: application/json" \
  https://localhost:8080/dira/users/USERID
```

#### Responses

##### OK

```
Status: 200 OK
```

##### No Content

```
Status: 204 No Content
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

---



## Project

### Get all projects

Fetch the information of every project in the system. Private projects will be shown only if the user has Premium subscription.

```
GET  /dira/projects
```

#### Parameters

| Name            | Type   | In     | Description                                       |
| --------------- | ------ | ------ | ------------------------------------------------- |
| `Authorization` | string | header | The authorization token required for this request |

#### Code samples

##### Shell

```bash
curl \
  -H "Accept: application/json" \
  https://localhost:8080/dira/projects
```

#### Responses

##### OK

```
Status: 200 OK
```

```json
[
  {
    "description": "string",
    "id": 0,
    "key": "string",
    "name": "string",
    "visibility": "PRIVATE"
  }
]
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Get a permission

Fetch information about a project in the system. Private projects will be shown only if the user has Premium subscription.

```
GET /dira/projects/{projectId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |

#### Code samples

##### Shell

```bash
curl \
  -H "Accept: application/json" \
  https://localhost:8080/dira/projects/PROJECTID
```

#### Responses

##### OK

```
Status: 200 OK
```

```json
{
  "description": "string",
  "id": 0,
  "key": "string",
  "name": "string",
  "visibility": "PUBLIC"
}
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Create a project

Create a new project. The user can create a private project only if they have a Premium subscription.

```
POST /dira/projects
```

#### Parameters

| Name            | Type   | In     | Description                                                  |
| --------------- | ------ | ------ | ------------------------------------------------------------ |
| `Authorization` | string | header | The authentication token required for this request           |
| `key`           | string | body   | **Required**. The unique key of the project                  |
| `name`          | string | body   | **Required**. The name of the project                        |
| `description`   | string | body   | **Required**. The description for the project                |
| `visibility`    | string | body   | **Required**. Makes the project visible to a certain group of users. Can be either *PUBLIC* or *PRIVATE*. |

#### Code samples

##### Shell

```bash
curl \
  -X POST \
  -H "Accept: application/json" \
  -d "{"description": "string", "id": 0, "key": "string", "name": "string", "visibility": "PUBLIC" }"
  https://localhost:8080/dira/projects
```

#### Responses

##### Created

```
Status: 201 Created
```

```json
{
  "description": "string",
  "id": 0,
  "key": "string",
  "name": "string",
  "visibility": "PUBLIC"
}
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Update a project's information

Update specific information about a project in the system

```
PUT /dira/projects/{projectId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |

#### Code samples

##### Shell

```bash
curl \
  -X PUT "Accept: application/json" \
  -H "Accept: application/json" \
  https://localhost:8080/dira/projects/PROJECTID
```

#### Request Body

```json
{
  "description": "string",
  "id": 0,
  "key": "string",
  "name": "string",
  "visibility": "PRIVATE"
}
```

#### Responses

##### OK

```
Status: 200 OK
```

```json
{
  "description": "string",
  "id": 0,
  "key": "string",
  "name": "string",
  "visibility": "PRIVATE"
}
```

##### Created

```
Status: 201 Created
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Delete a project

Delete all information for the requested user. 

```
DELETE /dira/projects/{projectId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |

#### Code samples

##### Shell

```bash
curl \
  -X DELETE \
  "Accept: application/json" \
  https://localhost:8080/dira/projects/PROJECTID
```

#### Responses

##### OK

```
Status: 200 OK
```

##### No Content

```
Status: 204 No Content
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

---



## Project users

### Get a specific project

Fetch information about a project in the system. Private projects will be shown only if the user has Premium subscription.

```
GET /dira/projects/{projectId}/users
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |

#### Code samples

##### Shell

```bash
curl \
  -H "Accept: application/json" \
  https://localhost:8080/dira/projects/PROJECTID/users
```

#### Responses

##### OK

```
Status: 200 OK
```

```json
{
  "id": 0,
  "key": "string",
  "users": [
    {
      "email": "string",
      "id": 0,
      "name": "string",
      "subscriptionPlan": "PREMIUM",
      "surname": "string",
      "username": "string"
    }
  ]
}
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Add user to a project

Create a new project. The user can create a private project only if they have a Premium subscription.

```
POST /dira/projects/{projectId}/users/{userId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `userId`        | integer | path   | The unique ID of the new participant              |

#### Parameters

#### Code samples

##### Shell

```bash
curl \
  -X POST \
  -H "Accept: application/json" \
  https://localhost:8080/dira/projects/users/USERID
```

#### Responses

##### OK

```
Status: 200 OK
```

##### Created

```
Status: 201 Created
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Remove a user from a project

Delete all information for the requested user. 

```
DELETE /dira/projects/{projectId}/users/{userId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `userId`        | integer | path   | The unique ID of the new participant              |

#### Code samples

##### Shell

```bash
curl \
  -X DELETE \
  "Accept: application/json" \
  https://localhost:8080/dira/projects/PROJECTID/users/USERID
```

#### Responses

##### OK

```
Status: 200 OK
```

##### No Content

```
Status: 204 No Content
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```



## Project permissions

### Get a specific project

Fetch information about a project in the system. Private projects will be shown only if the user has Premium subscription.

```
GET /dira/projects/{projectId or projectKey}/users/permissions/{permissionId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `permissionId`  | integer | path   | The unique ID of the permission                   |

#### Code samples

##### Shell

```bash
curl \
  -H "Accept: application/json" \
  https://localhost:8080/dira/projects/PROJECTID
```

#### Responses

##### OK

```
Status: 200 OK
```

```json
[
  {
    "customerId": 0,
    "id": 0,
    "permissions": [
      "ADMIN"
    ]
  }
]
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Create a permission

Create a new project. The user can create a private project only if they have a Premium subscription.

```
POST /dira/projects/{projectID or projectKey}/users/permissions/{permissionID}
```

#### Parameters

| Name                     | Type          | In     | Description                                         |
| ------------------------ | ------------- | ------ | --------------------------------------------------- |
| `Authorization`          | string        | header | The authorization token required for this request   |
| `projectId`              | integer       | path   | The unique ID of the project                        |
| `permissionId`           | integer       | path   | The unique ID of the permission                     |
| `customerId`             | integer       | body   | The ID of the user to associate the permission with |
| `permissions`            | array(string) | body   | A group of permissions                              |
| `permissionsFromInteger` | integer       | body   |                                                     |

#### Code samples

##### Shell

```bash
curl \
  -X POST \
  -H "Accept: application/json" \
  -d "{"customerId": 0, "id": 0, "permissions": ["ADMIN"], "permissionsFromInteger": 0 }" \
  https://localhost:8080/dira/projects
```

#### Responses

##### Created

```
Status: 201 Created
```

```json
{
  "customerId": 0,
  "id": 0,
  "permissions": [
    "ADMIN"
  ]
}
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### 

### Update a permission

Update specific information about a project in the system

```
PUT /dira/projects/{projectID or projectKey}/users/permissions/{permissionID}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `permissionId`  | integer | path   | The unique ID of the permission                   |

#### Code samples

##### Shell

```bash
curl \
  -X PUT "Accept: application/json" \
  -H "Accept: application/json" \
  https://localhost:8080/dira/projects/PROJECTID
```

#### Request Body

```json
{
  "customerId": 0,
  "id": 0,
  "permissions": [
    "ADMIN"
  ]
}
```

#### Responses

##### OK

```
Status: 200 OK
```

```json
{
  "description": "string",
  "id": 0,
  "key": "string",
  "name": "string",
  "visibility": "PRIVATE"
}
```

##### Created

```
Status: 201 Created
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Delete a permission

Delete all information for the requested user. 

```
DELETE /dira/projects/{projectID or projectKey}/users/permissions/{permissionID}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `permissionId`  | integer | path   | The unique ID of the permission                   |

#### Code samples

##### Shell

```bash
curl \
  -X DELETE \
  "Accept: application/json" \
  https://localhost:8080/dira/projects/PROJECTID
```

#### Responses

##### OK

```
Status: 200 OK
```

##### No Content

```
Status: 204 No Content
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```



## Issues

### Get all issues in a project

Fetch information about a project in the system. Private projects will be shown only if the user has Premium subscription.

```
GET /dira/projects/{projectId}/issues
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |

#### Code samples

##### Shell

```bash
curl \
  -H "Accept: application/json" \
  https://localhost:8080/dira/projects/PROJECTID/issues
```

#### Responses

##### OK

```
Status: 200 OK
```

```json
{
  "id": 0,
  "issues": [
    {
      "assignee": "string",
      "comments": [
        {
          "key": 0,
          "value": "string"
        }
      ],
      "created": "2021-06-24T12:58:51.528Z",
      "description": "string",
      "dueDate": "2021-06-24T12:58:51.528Z",
      "fixVersions": [
        {
          "key": 0,
          "value": "string"
        }
      ],
      "id": 0,
      "issueLinks": [
        {
          "id": 0,
          "linkType": "string",
          "linkedIssue": {
            "id": 0,
            "key": "string",
            "name": "string"
          }
        }
      ],
      "key": "string",
      "labels": [
        {
          "key": 0,
          "value": "string"
        }
      ],
      "priority": "Blocked",
      "projectName": "string",
      "reporter": "string",
      "resolved": true,
      "status": "Blocked",
      "title": "string",
      "type": "Defect",
      "updated": "2021-06-24T12:58:51.529Z"
    }
  ],
  "name": "string"
}
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Get an issue in a project

Fetch information about a project in the system. Private projects will be shown only if the user has Premium subscription.

```
GET /dira/projects/{projectId}/issues/{issueId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `issueId`       | integer | path   | The unique ID of the issue                        |

#### Code samples

##### Shell

```bash
curl \
  -H "Accept: application/json" \
  https://localhost:8080/dira/projects/PROJECTID/issues
```

#### Responses

##### OK

```
Status: 200 OK
```

```json
{
  "assignee": "string",
  "comments": [
    {
      "key": 0,
      "value": "string"
    }
  ],
  "created": "2021-06-24T13:05:36.374Z",
  "description": "string",
  "dueDate": "2021-06-24T13:05:36.374Z",
  "fixVersions": [
    {
      "key": 0,
      "value": "string"
    }
  ],
  "id": 0,
  "issueLinks": [
    {
      "id": 0,
      "linkType": "string",
      "linkedIssue": {
        "id": 0,
        "key": "string",
        "name": "string"
      }
    }
  ],
  "key": "string",
  "labels": [
    {
      "key": 0,
      "value": "string"
    }
  ],
  "priority": "Blocked",
  "projectName": "string",
  "reporter": "string",
  "resolved": true,
  "status": "Blocked",
  "title": "string",
  "type": "Defect",
  "updated": "2021-06-24T13:05:36.374Z"
}
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Create a new issue

Post an issue under a project.

```
POST /dira/projects/{projectId}/issues
```

#### Parameters

| Name            | Type    | In     | Description                                                  |
| --------------- | ------- | ------ | ------------------------------------------------------------ |
| `Authorization` | string  | header | The authorization token required for this request            |
| `projectId`     | integer | path   | The unique ID of the project                                 |
| `title`         | string  | body   | The name of the issue                                        |
| `description`   | string  | body   | A description for the new issue                              |
| `type`          | string  | body   | The type of the issue. Can be one of *Defect*, *Epic*, *Story* |
| `priority`      | string  | body   | The priority of the issue. Can be one of *Blocked*, *Low*, *Major*, *Normal* |
| `assigneeId`    | integer | body   | The ID of the customer assigned to perform the task          |
| `epicId`        | integer | body   | The ID of the associated Epic issue                          |

#### Code samples

##### Shell

```bash
curl \
  -X POST \
  -H "Accept: application/json" \
  -d "{ "assigneeId": 0, "description": "string", "epicId": 0, "id": 0, "priority": "Blocked", "title": "string",  "type": "Defect" }" \
  https://localhost:8080/dira/projects/PROJECTID/issues
```

#### Responses

##### OK

```
Status: 200 OK
```

```json
{
  "assignee": "string",
  "comments": [
    {
      "key": 0,
      "value": "string"
    }
  ],
  "created": "2021-06-24T13:00:10.685Z",
  "description": "string",
  "dueDate": "2021-06-24T13:00:10.685Z",
  "fixVersions": [
    {
      "key": 0,
      "value": "string"
    }
  ],
  "id": 0,
  "issueLinks": [
    {
      "id": 0,
      "linkType": "string",
      "linkedIssue": {
        "id": 0,
        "key": "string",
        "name": "string"
      }
    }
  ],
  "key": "string",
  "labels": [
    {
      "key": 0,
      "value": "string"
    }
  ],
  "priority": "Blocked",
  "projectName": "string",
  "reporter": "string",
  "resolved": true,
  "status": "Blocked",
  "title": "string",
  "type": "Defect",
  "updated": "2021-06-24T13:00:10.685Z"
}
```

##### Created

```
Status: 201 Created
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Update an issue

Update specific information in an issue.

```
PUT /dira/projects/{projectId}/issues/{issueId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `issueId`       | integer | path   | The unique ID of the issue                        |

#### Code samples

##### Shell

```bash
curl \
  -X PUT "Accept: application/json" \
  -H "Accept: application/json" \
  https://localhost:8080/dira/projects/PROJECTID/issues/ISSUEID
```

#### Responses

##### OK

```
Status: 200 OK
```

```

```

##### Created

```
Status: 201 Created
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```

##### Not Found

```
Status: 404 Not Found
```

------

### Delete an issue

Delete all information for the requested user. 

```
DELETE /dira/projects/{projectId}/issues/{issueId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `issueId`       | integer | path   | The unique ID of the issue                        |

#### Code samples

##### Shell

```bash
curl \
  -X DELETE \
  "Accept: application/json" \
  https://localhost:8080/dira/projects/PROJECTID
```

#### Responses

##### OK

```
Status: 200 OK
```

##### No Content

```
Status: 204 No Content
```

##### Unauthorized

```
Status: 401 Unauthorized
```

##### Forbidden

```
Status: 403 Forbidden
```



## Sprint

- GET           /projects/{projectID or projectKey}/sprints (retrieve all sprints with the given ID)
- POST          /projects/{projectID or projectKey}/sprints (create a new sprint in the project with the given ID)

- GET           /projects/{projectID or projectKey}/sprints/{sprintID} (retrieve sprint with the given ID)
- PUT           /projects/{projectID or projectKey}/sprints/{sprintID} (update a sprint with the given ID)
- DELETE        /projects/{projectID or projectKey}/sprints/{sprintID} (delete the sprint with the given ID)

## Class Diagram

![Class Diagram](images/dira_class_diagram.jpg?raw=true "Class Diagram")
