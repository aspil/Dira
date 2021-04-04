package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.UserLoginModel;
import di.uoa.gr.dira.services.userService.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    IUserService service;

    public LoginController(IUserService service) {
        this.service = service;
    }

    @PostMapping("login")
    public boolean login(@RequestBody UserLoginModel userLoginModel) {
        return service.authenticateUser(userLoginModel);
    }
}
