package Product.java;

import java.util.List;
public interface DAO<T> {
    T get(int var1);

    List<T> getAll();

    void save(T entity);

    void update(T entity);

    void delete(T entity);
}
