package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.UserModel;
import di.uoa.gr.dira.services.userService.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping("all")
    @ResponseBody
    public List<UserModel> getAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    @ResponseBody
    public UserModel getById(@PathVariable String id) {
        return service.findById(id);
    }

    /*@PostMapping
    public UserModel createUser(@RequestBody UserModel user) {
        return service.save(user);
    }*/
}
