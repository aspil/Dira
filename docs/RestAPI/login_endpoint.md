## Login
Authenticate user and produce JWT token.
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

**Shell**

```
curl -X POST "https://localhost:8443/dira/login" \
  -H "accept: */*" \
  -H "Content-Type: application/json" \
  -d "{ \"password\": \"12345678\", \"username\": \"tester\"}"
```

#### Responses

**OK**

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
