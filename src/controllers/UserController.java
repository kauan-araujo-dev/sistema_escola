package controllers;

import java.util.List;

import DAO.UserDao;
import models.User;

public class UserController {
	UserDao dao;
	
	public UserController() {
		dao = new UserDao();
	}
	
	public void add(String nome, int idade) {
		dao.insert(new User(null, nome, idade));
	}
	
	public void edit(int id, String nome, int idade) {
		dao.insert(new User(id, nome, idade));
	}
	
	public User list(int id) {
		return dao.find(id);
	}
	
	public List<User> listAll() {
		return dao.findAll();
	}
	
}
