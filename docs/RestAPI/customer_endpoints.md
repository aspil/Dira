# Table of Contents
**[User](#user)**\
[Retrieve all users](#retrieve-all-users)\
[Get a user](#get-a-user)\
[Get a user's project](#get-a-users-project)\
[Update user's plan](#update-users-plan)\
[Delete all users](#delete-all-users)\
[Delete a user](#delete-a-user)

## User

### Retrieve all users

List all users, in the order that they singed up on Dira. It includes both standard and premium accounts.

```
GET /dira/users
```

#### Parameters

| Name            | Type   | In     | Description                                        |
| --------------- | ------ | ------ | -------------------------------------------------- |
| `Authorization` | string | header | The authentication token required for this request |

#### Code samples

**Shell**

```bash
curl -X GET "https://localhost:8443/dira/users" \
  -H "accept: */*"
```

#### Responses

**OK**

```
Status: 200 OK
```

```json
[
  {
    "id": 1,
    "username": "tester",
    "name": "Tester",
    "surname": "Mc Tester",
    "email": "test@otenet.gr",
    "subscriptionPlan": "STANDARD"
  },
  {
    "id": 2,
    "username": "tester2",
    "name": "Tester2",
    "surname": "Mc Tester Cousin",
    "email": "test2@otenet.gr",
    "subscriptionPlan": "STANDARD"
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

### Get a user

Provides publicly available information about a Dira user.

```
GET /dira/users/{id}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `id`        | integer | path   | The unique ID of the user to be fetched           |

#### Code samples

**Shell**

```bash
curl -X GET "https://localhost:8443/dira/users/1" \
  -H "accept: */*"
```

#### Responses

**OK**

```
Status: 200 OK
```
**Example Model**
```json
{
  "id": 1,
  "username": "tester",
  "name": "Tester",
  "surname": "Mc Tester",
  "email": "test@otenet.gr",
  "subscriptionPlan": "STANDARD"
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

### Get a user's project

Provides the information of all the projects that the requested user participates into.

```
GET /dira/users/{id}/projects
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `id`        | integer | path   | The unique ID of the user                         |

#### Code samples

**Shell**

```bash
curl -X GET "https://localhost:8443/dira/users/1/projects" \
  -H "accept: */*"
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

### Update user's plan

Upgrade an account's subscription to Premium.

```
PUT /dira/users/{id}/updatePlan
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `id`        | integer | path   | The unique ID of the user                         |

#### Code samples

**Shell**

```bash
curl -X PUT "https://localhost:8443/dira/users/1/updatePlan" \
  -H "accept: */*"
```

#### Responses

**OK**

```
Status: 200 OK
```

##### Created

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

### Delete all users

Delete all information for every account in the system.

```
DELETE  /dira/users
```

#### Parameters

| Name            | Type   | In     | Description                                       |
| --------------- | ------ | ------ | ------------------------------------------------- |
| `Authorization` | string | header | The authorization token required for this request |

#### Code samples

**Shell**

```bash
curl -X DELETE "https://localhost:8443/dira/users" \
  -H "accept: */*"
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

------

### Delete a user

Delete all information for the requested user.

```
DELETE  /dira/users/{id}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `id`        | integer | path   | The unique ID of the user                         |

#### Code samples

**Shell**

```bash
curl -X DELETE "https://localhost:8443/dira/users/1" \
  -H "accept: */*"
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
