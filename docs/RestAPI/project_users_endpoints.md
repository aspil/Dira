# Table of Contents

**[Project users](#project-users)**\
[Get project members](#get-project-members)\
[Add user to a project with email](#add-user-to-a-project-with-email)\
[Add user to a project](#add-user-to-a-project)\
[Remove a user from a project](#remove-a-user-from-a-project)
## Project users

### Get project members

```
GET /dira/projects/{projectId}/users
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
  -X GET "https://localhost:8443/dira/projects/1/users" \
  -H "accept: */*"
```

#### Responses

**OK**

```
Status: 200 OK
```
**Example Model**
```
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

### Add user to a project with email

```
POST /dira/projects/{projectId}/users
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `userId`        | integer | path   | The unique ID of the new participant              |
| `email`         | string  | query  | The email of the new participant                  |

#### Code samples

**Shell**

```bash
curl \
  -X POST "https://localhost:8443/dira/projects/1/users?email=email%40example.com" \
  -H "accept: */*" \
  -H "Authorization: <authorization>" \
  -d ""
```

#### Responses

**OK**

```
Status: 200 OK
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

### Add user to a project

```
POST /dira/projects/{projectId}/users/{userId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `userId`        | integer | path   | The unique ID of the new participant              |

#### Code samples

**Shell**

```bash
curl \
  -X POST "https://localhost:8443/dira/projects/1/users/1" \
  -H "accept: */*" \
  -H "Authorization: <authorization>" \
  -d ""
```

#### Responses

**OK**

```
Status: 200 OK
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

### Remove a user from a project

Delete all information for the requested user.

```
DELETE /dira/projects/{projectId}/users/{userId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `userId`        | integer | path   | The unique ID of the new participant              |

#### Code samples

**Shell**

```bash
curl \
  -X DELETE "https://localhost:8443/dira/projects/1/users/1" \
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
