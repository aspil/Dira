package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.TestModel;
import di.uoa.gr.dira.services.testService.ITestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {
    final ITestService service;

    public TestController(ITestService service) {
        this.service = service;
    }

    @GetMapping("all")
    @ResponseBody
    public List<TestModel> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    @ResponseBody
    public TestModel getById(@PathVariable int id) {
        return service.getById(id);
    }
}
