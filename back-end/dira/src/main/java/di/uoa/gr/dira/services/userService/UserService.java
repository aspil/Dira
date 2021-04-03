package di.uoa.gr.dira.services.userService;

import di.uoa.gr.dira.entities.User;
import di.uoa.gr.dira.models.UserModel;
import di.uoa.gr.dira.repositories.UserRepository;
import di.uoa.gr.dira.security.PasswordManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {
    UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserModel> findAll() {
        List<User> users = repository.findAll();
        List<UserModel> models = new ArrayList<>();
        for (User user : users) {
            models.add(new UserModel(user));
        }

        return models;
    }

    @Override
    public UserModel findById(String id) {
        User user = repository.findById(id).orElse(null);
        return user != null ? new UserModel(user) : null;
    }

    @Override
    public UserModel insert(UserModel userModel) {
        User user = new User();

        user.setName(userModel.getName());
        user.setSurname(userModel.getSurname());
        user.setEmail(userModel.getEmail());
        user.setUsername(userModel.getUsername());

        String password = PasswordManager.encoder().encode(userModel.getPassword());
        user.setPassword(password);

        User newUser = repository.insert(user);
        return new UserModel(newUser);
    }

    @Override
    public UserModel update(UserModel userModel) {
        return null;
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(UserModel userModel) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserModel> userModels) {

    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
