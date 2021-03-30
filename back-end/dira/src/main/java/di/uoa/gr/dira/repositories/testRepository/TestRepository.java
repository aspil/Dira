package di.uoa.gr.dira.repositories.testRepository;

import di.uoa.gr.dira.entities.TestEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TestRepository implements ITestRepository {
    private static final List<TestEntity> entities = new ArrayList<>();

    static {
        entities.add(new TestEntity(1, "Hello World!"));
        entities.add(new TestEntity(2, "Hello Sailor!"));
        entities.add(new TestEntity(3, "I'm the third and longest!"));
    }

    @Override
    public List<TestEntity> getAll() {
        return entities;
    }

    @Override
    public TestEntity getById(int id) {
        for (TestEntity entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }
}
