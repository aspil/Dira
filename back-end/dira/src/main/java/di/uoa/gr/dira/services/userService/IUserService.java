package di.uoa.gr.dira.services.userService;

import di.uoa.gr.dira.models.UserLoginModel;
import di.uoa.gr.dira.models.UserModel;
import di.uoa.gr.dira.services.IService;

public interface IUserService extends IService<UserModel, String> {
    boolean authenticateUser(UserLoginModel user);
}
