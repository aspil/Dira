# Table of Contents

**[Sprint](#sprint)**\
[Get all sprints in a project](#get-all-sprints-in-a-project)\
[Get a sprint in a project](#get-a-sprint-in-a-project)\
[Create a new sprint](#create-a-new-sprint)\
[Update an existing sprint](#update-an-existing-sprint)\
[Delete a sprint](#delete-a-sprint)

## Sprint

### Get all sprints in a project

```
GET /dira/projects/{projectId}/sprints
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
  -X GET "https://localhost:8443/dira/projects/1/sprints" \
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
  "id": 0,
  "sprints": [
    {
      "active": true,
      "dueDate": "2021-07-04T16:54:06.129Z",
      "id": 0,
      "issueModels": [
        {
          "assignee": "string",
          "comments": [
            {
              "key": 0,
              "value": "string"
            }
          ],
          "created": "2021-07-04T16:54:06.129Z",
          "description": "string",
          "dueDate": "2021-07-04T16:54:06.129Z",
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
          "updated": "2021-07-04T16:54:06.129Z"
        }
      ],
      "startDate": "2021-07-04T16:54:06.129Z"
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

### Get a sprint in a project

Fetch information about a sprint of a project in the system.

```
GET /dira/projects/{projectId}/sprints/{sprintId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `sprintId`       | integer | path   | The unique ID of the sprint                        |

#### Code samples

**Shell**

```bash
curl \
  -X GET "https://localhost:8443/dira/projects/1/sprints/1" \
  -H "accept: */*" -H \
  "Authorization: <authorization>"
```

#### Responses

**OK**

```
Status: 200 OK
```
**Example Model**
```json
{
  "active": true,
  "dueDate": "2021-07-04T16:54:57.104Z",
  "id": 0,
  "issueModels": [
    {
      "assignee": "string",
      "comments": [
        {
          "key": 0,
          "value": "string"
        }
      ],
      "created": "2021-07-04T16:54:57.104Z",
      "description": "string",
      "dueDate": "2021-07-04T16:54:57.104Z",
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
      "updated": "2021-07-04T16:54:57.104Z"
    }
  ],
  "startDate": "2021-07-04T16:54:57.104Z"
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

### Create a new sprint

Post a new sprint under a project.

```
POST /dira/projects/{projectId}/sprints
```

#### Parameters

| Name            | Type    | In     | Description                                                  |
| --------------- | ------- | ------ | ------------------------------------------------------------ |
| `Authorization` | string  | header | The authorization token required for this request            |
| `projectId`     | integer | path   | The unique ID of the project                                 |
| `active`        | boolean | body   | Flag a sprint as active                                      |
| `dueDate`       | string  | body   | The date that is the sprint is set to end                    |
| `issueModels`   | array   | body   | The models of the issues inside the sprint                   |
| `startDate`     | string  | body   | The date that the sprint is set to start                     |

#### Code samples

**Shell**

```bash
curl \
  -X POST "https://localhost:8443/dira/projects/1/sprints" \
  -H "accept: */*" \
  -H "Authorization: <authorization>" \
  -H "Content-Type: application/json" \
  -d "{ \"active\": true, \"dueDate\": \"2021-07-04T16:58:49.642Z\", \"id\": 0, \"issueModels\": [ { \"assignee\": \"string\", \"comments\": [ { \"key\": 0, \"value\": \"string\" } ], \"created\": \"2021-07-04T16:58:49.642Z\", \"description\": \"string\", \"dueDate\": \"2021-07-04T16:58:49.642Z\", \"epicId\": 0, \"epicLinks\": [ { \"id\": 0, \"key\": \"string\", \"title\": \"string\" } ], \"fixVersions\": [ { \"key\": 0, \"value\": \"string\" } ], \"id\": 0, \"issueLinks\": [ { \"id\": 0, \"linkType\": \"string\", \"linkedIssue\": { \"id\": 0, \"key\": \"string\", \"title\": \"string\" } } ], \"key\": \"string\", \"labels\": [ { \"key\": 0, \"value\": \"string\" } ], \"priority\": \"Blocked\", \"projectName\": \"string\", \"reporter\": \"string\", \"resolved\": true, \"status\": \"Blocked\", \"title\": \"string\", \"type\": \"Defect\", \"updated\": \"2021-07-04T16:58:49.642Z\" } ], \"startDate\": \"2021-07-04T16:58:49.642Z\"}"
```

#### Responses

**OK**

```
Status: 200 OK
```
**Example Model**
```json
{
  "active": true,
  "dueDate": "2021-07-04T16:58:54.587Z",
  "id": 0,
  "issueModels": [
    {
      "assignee": "string",
      "comments": [
        {
          "key": 0,
          "value": "string"
        }
      ],
      "created": "2021-07-04T16:58:54.587Z",
      "description": "string",
      "dueDate": "2021-07-04T16:58:54.587Z",
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
      "updated": "2021-07-04T16:58:54.587Z"
    }
  ],
  "startDate": "2021-07-04T16:58:54.587Z"
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

### Update an existing sprint

Update specific information in a sprint.

```
PUT /dira/projects/{projectId}/sprints/{sprintId}
```

#### Parameters

| Name            | Type    | In     | Description                                                  |
| --------------- | ------- | ------ | ------------------------------------------------------------ |
| `Authorization` | string  | header | The authorization token required for this request            |
| `projectId`     | integer | path   | The unique ID of the project                                 |
| `active`        | boolean | body   | Flag a sprint as active                                      |
| `dueDate`       | string  | body   | The date that is the sprint is set to end                    |
| `issueModels`   | array   | body   | The models of the issues inside the sprint                   |
| `startDate`     | string  | body   | The date that the sprint is set to start                     |

#### Code samples

**Shell**

```bash
curl \
  -X PUT "https://localhost:8443/dira/projects/1/sprints/1" \
  -H "accept: */*" \
  -H "Authorization: <authorization>" \
  -H "Content-Type: application/json" \
  -d "{ \"active\": true, \"dueDate\": \"2021-07-04T17:01:50.240Z\", \"id\": 0, \"issueModels\": [ { \"assignee\": \"string\", \"comments\": [ { \"key\": 0, \"value\": \"string\" } ], \"created\": \"2021-07-04T17:01:50.240Z\", \"description\": \"string\", \"dueDate\": \"2021-07-04T17:01:50.240Z\", \"epicId\": 0, \"epicLinks\": [ { \"id\": 0, \"key\": \"string\", \"title\": \"string\" } ], \"fixVersions\": [ { \"key\": 0, \"value\": \"string\" } ], \"id\": 0, \"issueLinks\": [ { \"id\": 0, \"linkType\": \"string\", \"linkedIssue\": { \"id\": 0, \"key\": \"string\", \"title\": \"string\" } } ], \"key\": \"string\", \"labels\": [ { \"key\": 0, \"value\": \"string\" } ], \"priority\": \"Blocked\", \"projectName\": \"string\", \"reporter\": \"string\", \"resolved\": true, \"status\": \"Blocked\", \"title\": \"string\", \"type\": \"Defect\", \"updated\": \"2021-07-04T17:01:50.240Z\" } ], \"startDate\": \"2021-07-04T17:01:50.240Z\"}"
```

#### Responses

**OK**

```
Status: 200 OK
```
**Example Model**
```
{
  "active": true,
  "dueDate": "2021-07-04T17:01:50.265Z",
  "id": 0,
  "issueModels": [
    {
      "assignee": "string",
      "comments": [
        {
          "key": 0,
          "value": "string"
        }
      ],
      "created": "2021-07-04T17:01:50.265Z",
      "description": "string",
      "dueDate": "2021-07-04T17:01:50.265Z",
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
      "updated": "2021-07-04T17:01:50.265Z"
    }
  ],
  "startDate": "2021-07-04T17:01:50.265Z"
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

### Delete a sprint

Delete a sprint with a give id

```
DELETE /dira/projects/{projectId}/sprint/{sprintId}
```

#### Parameters

| Name            | Type    | In     | Description                                       |
| --------------- | ------- | ------ | ------------------------------------------------- |
| `Authorization` | string  | header | The authorization token required for this request |
| `projectId`     | integer | path   | The unique ID of the project                      |
| `sprintId`      | integer | path   | The unique ID of the sprint                       |

#### Code samples

**Shell**

```bash
curl \
  -X DELETE "https://localhost:8443/dira/projects/1/sprints/1" \
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