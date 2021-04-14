package di.uoa.gr.dira.services;

import di.uoa.gr.dira.util.MapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        TModel model = entity != null ? mapper.map(entity, modelType) : null;
        return model;
    }

    @Override
    public TModel insert(TModel model) {
        TEntity entity = mapper.map(model, entityType);
        TEntity saved = repository.save(entity);
        TModel res = mapper.map(saved, modelType);
        return res;
    }

    @Override
    public TModel update(TModel model) {
        return null;
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(TModel model) {
    }

    @Override
    public void deleteAll(Iterable<? extends TModel> tModels) {
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
