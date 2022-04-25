package DAO;

import java.util.List;

public interface DAO <T> {
	public List<T> getAll();
	public List<T> getByPage(int page);
	public T findById(int id);
	public T create(T entity) throws Exception;
	public T update(T entity) throws Exception;
	public T delete(T entity) throws Exception;
}
