**[Issues](#issues)**\
[Get all issues in a project](#get-all-issues-in-a-project)\
[Get an issue in a project](#get-an-issue-in-a-project)\
[Create a new issue](#create-a-new-issue)\
[Update an issue](#update-an-issue)\
[Delete an issue](#delete-an-issue)

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

**Shell**

```bash
curl \
  -X GET "https://localhost:8443/dira/projects/1/issues" \
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
      "created": "2021-07-04T16:50:41.814Z",
      "description": "string",
      "dueDate": "2021-07-04T16:50:41.814Z",
      "epicId": 0,
      "epicLinks": [
        {
          "id": 0,
          "key": "string",
          "title": "string"
        }
      ],
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
            "title": "string"
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
      "updated": "2021-07-04T16:50:41.814Z"
    }
  ],
  "name": "string"
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

**Shell**

```bash
curl \
  -X GET "https://localhost:8443/dira/projects/1/issues/1" \
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
  "assignee": "string",
  "comments": [
    {
      "key": 0,
      "value": "string"
    }
  ],
  "created": "2021-07-04T16:49:25.697Z",
  "description": "string",
  "dueDate": "2021-07-04T16:49:25.697Z",
  "epicId": 0,
  "epicLinks": [
    {
      "id": 0,
      "key": "string",
      "title": "string"
    }
  ],
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
        "title": "string"
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
  "updated": "2021-07-04T16:49:25.698Z"
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

**Shell**

```bash
curl \
  -X POST "https://localhost:8443/dira/projects/1/issues" \
  -H "accept: */*" \
  -H "Authorization: <authorization>" \
  -H "Content-Type: application/json" \
  -d "{ \"assigneeId\": 0, \"description\": \"string\", \"epicId\": 0, \"id\": 0, \"priority\": \"Blocked\", \"title\": \"string\", \"type\": \"Defect\"}"
```

#### Responses

**OK**

```
Status: 200 OK
```
**Example Model**
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

**Shell**

```bash
curl \
  -X PUT "https://localhost:8443/dira/projects/1/issues/1" \
  -H "accept: */*" \
  -H "Authorization: <authorization>" \
  -H "Content-Type: application/json" \
  -d "{ \"assignee\": \"string\", \"comments\": [ { \"key\": 0, \"value\": \"string\" } ], \"created\": \"2021-07-04T16:51:13.071Z\", \"description\": \"string\", \"dueDate\": \"2021-07-04T16:51:13.071Z\", \"epicId\": 0, \"epicLinks\": [ { \"id\": 0, \"key\": \"string\", \"title\": \"string\" } ], \"fixVersions\": [ { \"key\": 0, \"value\": \"string\" } ], \"id\": 0, \"issueLinks\": [ { \"id\": 0, \"linkType\": \"string\", \"linkedIssue\": { \"id\": 0, \"key\": \"string\", \"title\": \"string\" } } ], \"key\": \"string\", \"labels\": [ { \"key\": 0, \"value\": \"string\" } ], \"priority\": \"Blocked\", \"projectName\": \"string\", \"reporter\": \"string\", \"resolved\": true, \"status\": \"Blocked\", \"title\": \"string\", \"type\": \"Defect\", \"updated\": \"2021-07-04T16:51:13.071Z\"}"
```

#### Responses

**OK**

```
Status: 200 OK
```

```

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

### Delete an issue

Delete all information for the requested user.

```
DELETE /dira/projects/{projectId}/issues/{issueId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `issueId`       | integer | path   | The unique ID of the issue                        |

#### Code samples

**Shell**

```bash
curl \
  -X DELETE "https://localhost:8443/dira/projects/1/issues/1" \
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

