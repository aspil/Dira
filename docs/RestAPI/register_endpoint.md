
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

**Code samples**

##### Shell

```bash
curl -X POST "https://localhost:8443/dira/register" \
  -H "accept: */*" \
  -H "Content-Type: application/json" \
  -d "{ \"email\": \"string\", \"id\": 0, \"name\": \"string\", \"password\": \"string\", \"subscriptionPlan\": \"PREMIUM\", \"surname\": \"string\", \"username\": \"string\"}"
```

#### Responses

**Created**

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
