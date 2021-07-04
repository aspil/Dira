# Table of Contents

**[Project permissions](#project-permissions)**\
[Get project permissions](#get-project-permission)\
[Create a permission](#create-a-permission)\
[Update a permission](#update-a-permission)\
[Delete a permission](#delete-a-permission)


## Project permissions

### Get project permissions

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

**Shell**

```bash
curl \
  -X GET "https://localhost:8443/dira/projects/1/users/permissions" \
  -H "accept: */*" \
  -H "Authorization: <authorization>"
```

#### Responses

**OK**

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

**Shell**

```bash
curl \
  -X POST "https://localhost:8443/dira/projects/1/users/permissions" \
  -H "accept: */*" \
  -H "Authorization: <authorization>" \
  -H "Content-Type: application/json" \
  -d "{ \"customerId\": 0, \"id\": 0, \"permissions\": [ \"ADMIN\" ], \"permissionsFromInteger\": 0}"
```

#### Responses

**Created**

```
Status: 201 Created
```
**Example Model**
```json
{
  "customerId": 0,
  "id": 0,
  "permissions": [
    "ADMIN"
  ]
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

**Shell**

```bash
curl \
  -X PUT "https://localhost:8443/dira/projects/1/users/permissions/1" \
  -H "accept: */*" \
  -H "Authorization: <authorization>" \
  -H "Content-Type: application/json" \
  -d "{ \"customerId\": 0, \"id\": 0, \"permissions\": [ \"ADMIN\" ], \"permissionsFromInteger\": 0}"
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

### Delete a permission

Delete all information for the requested user.

```
DELETE /dira/projects/{projectID or projectKey}/users/permissions/{permissionID}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `permissionId`  | integer | path   | The unique ID of the permission                   |

#### Code samples

**Shell**

```bash
curl \
  -X DELETE "https://localhost:8443/dira/projects/1/users/permissions/1" \
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
