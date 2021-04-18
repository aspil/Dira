package di.uoa.gr.dira.groovy.services;

import di.uoa.gr.dira.util.MapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseService<TModel, TEntity, ID, TRepo extends JpaRepository<TEntity, ID>> implements IService<TModel, ID> {
    protected final TRepo repository;
    protected final ModelMapper mapper;
    private final Type modelType;
    private final Type entityType;

    protected BaseService(TRepo repository) {
        Type[] genericClassTypes = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        this.repository = repository;
        this.mapper = new ModelMapper();
        this.modelType = genericClassTypes[0];
        this.entityType = genericClassTypes[1];
    }

    @Override
    public List<TModel> findAll() {
        List<TEntity> entities = repository.findAll();
        return MapperHelper.mapList(mapper, entities, modelType);
    }

    @Override
    public TModel findById(ID id) {
        TEntity entity = repository.findById(id).orElse(null);
        return entity != null ? mapper.map(entity, modelType) : null;
    }

    @Override
    public TModel save(TModel model) {
        TEntity entity = mapper.map(model, entityType);
        TEntity saved = repository.save(entity);
        return mapper.map(saved, modelType);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(TModel model) {
        TEntity entity = mapper.map(model, entityType);
        repository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends TModel> models) {
        List<TEntity> entities = new ArrayList<>();
        for (TModel model : models) {
            TEntity entity = mapper.map(model, entityType);
            entities.add(entity);
        }
        repository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
