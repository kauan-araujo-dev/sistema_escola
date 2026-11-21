package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import core.Database;
import models.User;

public class UserDao implements Dao<User> {

	public void insert(User user) {
		String sql = "INSERT INTO users (nome, idade) VALUES (?, ?)";

		try {
			Connection connection = Database.getConnection();

			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, user.getNome());
			stmt.setInt(2, user.getIdade());

			stmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("Erro ao inserir usuario");
		}
	}

	@Override
	public void update(User user) {
		String sql = "UPDATE users SET nome = ?, idade = ? WHERE id = ?";

		try {
			Connection connection = Database.getConnection();

			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, user.getNome());
			stmt.setInt(2, user.getIdade());
			stmt.setInt(3, user.getId());

			stmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("Erro ao atualizar usuario");
		}
	}

	@Override
	public User fetch(int id) {
		String sql = "SELECT * FROM users WHERE id = ?";
		try {

			Connection connection = Database.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new User(rs.getInt("id"), rs.getString("nome"), rs.getInt("idade"));
			}

		} catch (Exception e) {
			System.out.println("Erro ao buscar usuário");
		}

		return null;
	}

	@Override
	public List<User> fetchAll() {
		String sql = "SELECT * FROM users";
		try {

			Connection connection = Database.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			List<User> listUsers = new ArrayList<User>();
			while (rs.next()) {
				listUsers.add(new User(rs.getInt("id"), rs.getString("nome"), rs.getInt("idade")));
			}
			return listUsers;
		} catch (Exception e) {
			System.out.println("Erro ao buscar usuário");
		}

		return null;
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM users WHERE id = ?";
		try {
			Connection connection = Database.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setInt(1, id);

		} catch (Exception e) {
			System.out.println("Erro ao deletar usuário");
		}
	}

}
