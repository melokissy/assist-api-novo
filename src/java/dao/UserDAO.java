package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDAO {

    private static final String NEW_USER = "INSERT INTO user ( name , password, email, status, userIcon, profile,cpf,setor) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SEARCH_BY_ID = "SELECT idUser, name, email, status, profile,cpf,setor FROM user WHERE idUser=?";
    private static final String USERS = "SELECT idUser, name, email, status, profile,cpf,setor FROM user ORDER BY UPPER(name) ASC";
    private static final String USERS_BY_PROFILE = "SELECT idUser, name, email, status, profile,cpf,setor FROM user WHERE profile in ('Tecnico','Administrador') and status = '1' ORDER BY UPPER(name) ASC";
    private static final String EDIT_USER = "UPDATE user SET name = ?, email = ?, password = ?, status = ?, userIcon = ?, profile = ?, cpf=?, setor=? WHERE idUser = ?";
    private static final String SEARCH = "SELECT idUser, name, email, status, profile,cpf,setor FROM user WHERE idUser=?";
    private static final String SEARCH_BY_CPF = "SELECT idUser, name, email, status, profile,cpf,setor FROM user WHERE cpf=?";
    private static final String SEARCH_BY_EMAIL = "SELECT idUser, name, email, status, profile,cpf,setor FROM user WHERE email=?";
    private static final String LOGIN = "SELECT name, email, profile,idUser FROM user WHERE email=? and password=?";

    private static final String DELETE_USER = "DELETE FROM user WHERE idUser=?";

    public UserDAO() {
    }

    //lista todos os usuarios
    public List<User> users() {
        Connection conn = null;
        List<User> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            list = new ArrayList();
            prepared = conn.prepareStatement(USERS);
            rs = prepared.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setStatus(rs.getBoolean(4));
                user.setProfile(rs.getString(5));
                user.setCpf(rs.getString(6));
                user.setSetor(rs.getString(7));
                list.add(user);
            }

            return list;
        } catch (Exception e) {
            System.out.println("ERROR - " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }

                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error close connections" + ex.getMessage());
            }
        }

        return null;
    }

    //lista usuarios tecnicos
    public List<User> usersByProfile() {
        Connection conn = null;
        List<User> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            list = new ArrayList();
            prepared = conn.prepareStatement(USERS_BY_PROFILE);
            rs = prepared.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setStatus(rs.getBoolean(4));
                user.setProfile(rs.getString(5));
                user.setCpf(rs.getString(6));
                user.setSetor(rs.getString(7));
                list.add(user);
            }

            return list;
        } catch (Exception e) {
            System.out.println("ERROR - " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }

                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error close connections" + ex.getMessage());
            }
        }

        return null;
    }

    public User search(int id) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(SEARCH);
            prepared.setInt(1, id);
            rs = prepared.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setStatus(rs.getBoolean(4));
                user.setProfile(rs.getString(5));
                user.setCpf(rs.getString(6));
                user.setSetor(rs.getString(7));
                return user;
            }

        } catch (Exception ex) {
            System.out.println("[SEARCH USER] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }

                if (rs != null) {
                    rs.close();
                }

            } catch (Exception ex) {
                System.out.println("Error Close connections " + ex.getMessage());
            }
        }
        return null;

    }

    //adiciona novo usuario
    public void insertUser(User user) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(NEW_USER, prepared.RETURN_GENERATED_KEYS);
            prepared.setString(1, user.getName());
            prepared.setString(2, user.getPassword());
            prepared.setString(3, user.getEmail());
            prepared.setBoolean(4, user.getStatus());
            prepared.setString(5, user.getUserIcon());
            prepared.setString(6, user.getProfile());
            prepared.setString(7, user.getCpf());
            prepared.setString(8, user.getSetor());

            int affectedRows = prepared.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = prepared.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (Exception ex) {
            System.out.println("[USER STORE] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }

                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error close connections" + ex.getMessage());
            }
        }

    }

    public User update(User user) {
        Connection conn = null;
        PreparedStatement prepared = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(EDIT_USER);
            prepared.setString(1, user.getName());
            prepared.setString(2, user.getEmail());
            prepared.setString(3, user.getPassword());
            prepared.setBoolean(4, user.getStatus());
            prepared.setString(5, user.getUserIcon());
            prepared.setString(6, user.getProfile());
            prepared.setString(7, user.getCpf());
            prepared.setString(8, user.getSetor());
            prepared.setInt(9, user.getId());
            prepared.executeUpdate();
            return user;
        } catch (Exception ex) {
            System.out.println("[USER UPDATE] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }
            } catch (Exception ex) {
                System.out.println("Error close connections" + ex.getMessage());
            }
        }

        return user;
    }

    public User delete(User user) {
        Connection conn = null;
        PreparedStatement prepared = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(DELETE_USER);
            prepared.setInt(1, user.getId());
            prepared.executeUpdate();
            return user;
        } catch (Exception ex) {
            System.out.println("[USER DELETE] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }
            } catch (Exception ex) {
                System.out.println("Error Close connections " + ex.getMessage());
            }
        }

        return user;
    }

    public User searchByCpf(String cpf) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(SEARCH_BY_CPF);
            prepared.setString(1, cpf);
            rs = prepared.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setStatus(rs.getBoolean(4));
                user.setProfile(rs.getString(5));
                user.setCpf(rs.getString(6));
                user.setSetor(rs.getString(7));
                return user;
            }

        } catch (Exception ex) {
            System.out.println("[SEARCH USER BY NAME] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }

                if (rs != null) {
                    rs.close();
                }

            } catch (Exception ex) {
                System.out.println("Error Close connections " + ex.getMessage());
            }
        }
        return null;

    }
    
    public User searchByEmail(String email) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(SEARCH_BY_EMAIL);
            prepared.setString(1, email);
            rs = prepared.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setStatus(rs.getBoolean(4));
                user.setProfile(rs.getString(5));
                user.setCpf(rs.getString(6));
                user.setSetor(rs.getString(7));
                return user;
            }

        } catch (Exception ex) {
            System.out.println("[SEARCH USER BY EMAIL] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }

                if (rs != null) {
                    rs.close();
                }

            } catch (Exception ex) {
                System.out.println("Error Close connections " + ex.getMessage());
            }
        }
        return null;

    }

    public User login(User user) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(LOGIN);
            prepared.setString(1, user.getEmail());
            prepared.setString(2, user.getPassword());

            rs = prepared.executeQuery();

            if (rs.next()) {
                user.setName(rs.getString(1));
                user.setEmail(rs.getString(2));
                user.setProfile(rs.getString(3));
                user.setId(rs.getInt(4));
                return user;
            }

        } catch (Exception ex) {
            System.out.println("[LOGIN] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }

                if (rs != null) {
                    rs.close();
                }

            } catch (Exception ex) {
                System.out.println("Error Close connections " + ex.getMessage());
            }
        }
        return null;

    }
}
