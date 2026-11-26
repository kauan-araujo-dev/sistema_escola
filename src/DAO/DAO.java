package DAO;

import java.util.List;

public interface DAO <T>{
	public void insert(T t);
	public void update(T t);
	public T find(int id);
	public List<T> findAll();
	public void delete(int id);
}
