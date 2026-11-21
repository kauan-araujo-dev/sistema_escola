package DAO;

import java.util.List;

public interface Dao <T>{
	public void insert(T t);
	public void update(T t);
	public T fetch(int id);
	public List<T> fetchAll();
	public void delete(int id);
}
