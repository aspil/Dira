package di.uoa.gr.dira.repositories;

import di.uoa.gr.dira.entities.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
    public Project findByName(String name);
}