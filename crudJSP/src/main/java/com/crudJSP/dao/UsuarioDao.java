package com.crudJSP.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.crudJSP.bean.Usuario;

public class UsuarioDao {

	public static Connection getConnection() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/crudjspjava?useTimezone=true&serverTimezone=UTC", "root", "bcd127");
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}

	public static int deletarUsuario(Usuario u) {
		int status = 0;

		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM usuario WHERE id=?");
			ps.setInt(1, u.getId());
			status = ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}

		return status;
	}

	public static int salvarUsuario(Usuario u) {

		int status = 0;

		try {
			Connection con = getConnection();
			PreparedStatement ps = con
					.prepareStatement("INSERT INTO usuario (nome, password, email, sexo, pais) VALUES (?,?,?,?,?)");
			ps.setString(1, u.getNome());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getEmail());
			ps.setString(4, u.getSexo());
			ps.setString(5, u.getPais());

			status = ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	public static Usuario getRegistroById(int id) {
		Usuario usuario = null;

		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM usuario WHERE id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setPassword(rs.getString("password"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSexo(rs.getString("sexo"));
				usuario.setPais(rs.getString("pais"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return usuario;
	}

	public static int updateUsuario(Usuario usuario) {
		int status = 0;

		try {
			Connection con = getConnection();
			PreparedStatement ps = con
					.prepareStatement("UPDATE usuario SET nome=?," + "password=?, email=?, sexo=?, pais=? WHERE id=?");

			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getPassword());
			ps.setString(3, usuario.getEmail());
			ps.setString(4, usuario.getSexo());
			ps.setString(5, usuario.getPais());
			ps.setInt(6, usuario.getId());

			status = ps.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

	public static List<Usuario> getAllUsuarios() {
		List<Usuario> list = new ArrayList<Usuario>();

		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM usuario");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setPassword(rs.getString("password"));
				usuario.setSexo(rs.getString("sexo"));
				usuario.setEmail(rs.getString("email"));
				usuario.setPais(rs.getString("pais"));
				list.add(usuario);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

	// PAGINAÇÃO
	public static List<Usuario> getRecords(int start, int total){
		List<Usuario> list = new ArrayList<>();
		
		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM LIMIT "+(start)+","+total);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Usuario usuario = new Usuario();
				
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setPassword(rs.getString("password"));
				usuario.setSexo(rs.getString("sexo"));
				usuario.setEmail(rs.getString("email"));
				usuario.setPais(rs.getString("pais"));
				list.add(usuario);
			}
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
}