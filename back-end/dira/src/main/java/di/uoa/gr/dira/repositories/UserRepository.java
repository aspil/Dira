package di.uoa.gr.dira.repositories;

import di.uoa.gr.dira.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByUsername(String username);
}
