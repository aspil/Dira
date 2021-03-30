package di.uoa.gr.dira.repositories;

import java.util.List;

public interface IRepository<TEntity> {
    List<TEntity> getAll();
    TEntity getById(int id);
}
