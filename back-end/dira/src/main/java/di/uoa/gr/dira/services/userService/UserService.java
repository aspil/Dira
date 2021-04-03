package di.uoa.gr.dira.services.userService;

import di.uoa.gr.dira.entities.User;
import di.uoa.gr.dira.models.UserModel;
import di.uoa.gr.dira.repositories.UserRepository;
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
    public List<UserModel> getAll() {
        List<User> users = repository.findAll();
        List<UserModel> models = new ArrayList<>();
        for (User user : users) {
            UserModel model = new UserModel();
            model.setName(user.getName());
            model.setSurname(user.getSurname());
            model.setEmail(user.getEmail());
            models.add(model);
        }

        return models;
    }

    @Override
    public UserModel getById(String id) {
        User user = repository.findById(id).orElse(null);
        if (user != null) {
            UserModel model = new UserModel();
            model.setName(user.getName());
            model.setSurname(user.getSurname());
            model.setEmail(user.getEmail());
            return model;
        }
        return null;
    }

    @Override
    public UserModel save(UserModel userModel) {
        User user = new User(userModel.getName(), userModel.getSurname(), userModel.getEmail(), "gliontos", "saiko");
        User newUser = repository.save(user);
        return getById(newUser.getId());
    }
}
