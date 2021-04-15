package di.uoa.gr.dira.services;


import java.util.List;


public interface IService<TModel, ID> {
    List<TModel> findAll();

    TModel findById(ID id);

    TModel save(TModel model);

    void deleteById(ID id);

    void delete(TModel model);

    void deleteAll(Iterable<? extends TModel> models);

    void deleteAll();
}
