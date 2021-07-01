package di.uoa.gr.dira.services;

import java.util.List;
import java.util.Optional;

public interface IService<TModel, ID, TRepo> {
    TRepo getRepository();

    List<TModel> findAll();

    Optional<TModel> findById(ID id);

    TModel save(TModel model);

    void deleteById(ID id);

    void delete(TModel model);

    void deleteAll(Iterable<? extends TModel> models);

    void deleteAll();
}
