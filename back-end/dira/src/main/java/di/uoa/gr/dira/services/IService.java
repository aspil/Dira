package di.uoa.gr.dira.services;


import java.util.List;


public interface IService<TModel, ID> {
    List<TModel> getAll();

    TModel getById(ID id);

    TModel save(TModel model);
}
