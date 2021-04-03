package di.uoa.gr.dira.entities;

import java.util.List;

public class Project {
    long id;
    String key;
    String name;
    String description;
    List<User> users; // TODO: Do we really need this?
}
