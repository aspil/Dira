package di.uoa.gr.dira.services.testService;

import di.uoa.gr.dira.entities.TestEntity;
import di.uoa.gr.dira.models.TestModel;
import di.uoa.gr.dira.repositories.testRepository.ITestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService implements ITestService {
    ITestRepository repository;

    public TestService(ITestRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TestModel> getAll() {
        List<TestEntity> entities = repository.getAll();
        List<TestModel> models = new ArrayList<>();

        for (TestEntity entity : entities) {
            TestModel model = new TestModel(entity.getId(), entity.getDescription());
            models.add(model);
        }

        return models;
    }

    @Override
    public TestModel getById(int id) {
        TestEntity entity = repository.getById(id);
        return new TestModel(entity.getId(), entity.getDescription());
    }
}
