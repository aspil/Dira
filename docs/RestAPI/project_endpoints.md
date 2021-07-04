# Table of Contents

**[Project](#project)**\
[Get all projects](#get-all-projects)\
[Get a project](#get-a-project)\
[Create a project](#create-a-project)\
[Update a project's information](#update-a-projects-information)\
[Delete a project](#delete-a-project)

## Project

### Get all projects

Fetch the information of every project in the system. Private projects will be shown only if the user has Premium subscription.

```
GET /dira/projects
```

#### Parameters

| Name            | Type   | In     | Description                                       |
| --------------- | ------ | ------ | ------------------------------------------------- |
| `Authorization` | string | header | The authorization token required for this request |

#### Code samples

**Shell**

```bash
curl \
  -H " content-type: application/json" \
  https://localhost:8080/dira/projects
```

#### Responses

**OK**

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

**Unauthorized**

```
Status: 401 Unauthorized
```

**Forbidden**

```
Status: 403 Forbidden
```

**Not Found**

```
Status: 404 Not Found
```

------

### Get a project

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

**Shell**

```bash
curl \
  -X GET "https://localhost:8443/dira/projects/1" \
  -H "accept: */*" \
  -H "Authorization: <authorization>"
```

#### Responses

**OK**

```
Status: 200 OK
```
**Example Model**
```json
{
  "description": "string",
  "id": 0,
  "key": "string",
  "name": "string",
  "visibility": "PUBLIC"
}
```

**Unauthorized**

```
Status: 401 Unauthorized
```

**Forbidden**

```
Status: 403 Forbidden
```

**Not Found**

```
Status: 404 Not Found
```

------

### Create a project

Create a new project. The user can create a private project only if they have a Premium subscription.

```
POST /dira/projects
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

**Shell**

```bash
curl \
  -X POST "https://localhost:8443/dira/projects" \
  -H "accept: */*" \
  -H "Authorization: <authorization>" \
  -H "Content-Type: application/json" \
  -d "{ \"description\": \"string\", \"id\": 0, \"key\": \"string\", \"name\": \"string\", \"visibility\": \"PRIVATE\"}"
```

#### Responses

**Created**

```
Status: 201 Created
```
**Example Model**
```json
{
  "description": "string",
  "id": 0,
  "key": "string",
  "name": "string",
  "visibility": "PUBLIC"
}
```

**Unauthorized**

```
Status: 401 Unauthorized
```

**Forbidden**

```
Status: 403 Forbidden
```

**Not Found**

```
Status: 404 Not Found
```

------

### Update a project's information

Update specific information about a project in the system

```
PUT /dira/projects/{projectId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |

#### Code samples

**Shell**

```bash
curl \
  -X PUT "https://localhost:8443/dira/projects/1" \
  -H "accept: */*" \
  -H "Authorization: <authorization>" \
  -H "Content-Type: application/json" \
  -d "{ \"description\": \"string\", \"id\": 0, \"key\": \"string\", \"name\": \"string\", \"visibility\": \"PRIVATE\"}"
```

#### Request Body
**Example Model**
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

**OK**

```
Status: 200 OK
```
**Example Model**
```
https://
  "description": "string",
  "id": 0,
  "key": "string",
  "name": "string",
  "visibility": "PRIVATE"
}
```

**Created**

```
Status: 201 Created
```

**Unauthorized**

```
Status: 401 Unauthorized
```

**Forbidden**

```
Status: 403 Forbidden
```

**Not Found**

```
Status: 404 Not Found
```

------

### Delete a project

Delete all information for the requested user.

```
DELETE /dira/projects/{projectId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |

#### Code samples

**Shell**

```bash
curl \
  -X DELETE "https://localhost:8443/dira/projects/1" \
  -H "accept: */*" \
  -H "Authorization: <authorization>"
```

#### Responses

**OK**

```
Status: 200 OK
```

**No Content**

```
Status: 204 No Content
```

**Unauthorized**

```
Status: 401 Unauthorized
```

**Forbidden**

```
Status: 403 Forbidden
```
