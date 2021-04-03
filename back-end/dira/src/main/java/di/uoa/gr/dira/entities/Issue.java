package di.uoa.gr.dira.entities;

import java.util.Date;

public class Issue {
    long id;
    long epicId;
    long typeId;
    long statusId;
    long labelId;
    long commentId;
    long assigneeId;
    long reporterId;
    long fixVersionId;
    int priority;
    String key;
    String title;
    String description;
    Date created;
    Date updated;
    Date dueDate;
    long estimatedTime;
    long remainingTime;
    long loggedTime;
    String timeUnit;
    boolean resolved;
}
