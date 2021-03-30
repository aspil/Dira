package di.uoa.gr.dira.services;

import java.util.List;

public interface IService<TModel> {
    List<TModel> getAll();
    TModel getById(int id);
}
