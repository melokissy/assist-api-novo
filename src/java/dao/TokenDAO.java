/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Token;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
public class TokenDAO {
    
    private static final String NEW = "INSERT INTO token ( idUser , token, createdAt) VALUES (?,?,?)";
    private static final String SEARCH = "SELECT idUser , token, createdAt FROM token WHERE token=?";
        
    public TokenDAO (){}
    
    public Token insertToken(String token, int idUser){
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(NEW, prepared.RETURN_GENERATED_KEYS);            
            prepared.setInt(1, idUser);
            prepared.setString(2, token);
            prepared.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
            
            int affectedRows = prepared.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating token failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = prepared.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Token generatedToken = new Token(); 
                    generatedToken.setId(generatedKeys.getInt(1));
                    generatedToken.setToken(token);
                    generatedToken.setIdUser(idUser);
                    
                    return generatedToken; 
                }
                else {
                    throw new SQLException("Creating token failed, no ID obtained.");
                }
            }

        } catch (Exception ex) {
            System.out.println("[TOKEN STORE] - " + ex.getMessage());
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
    
    public Token search(String token) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(SEARCH);
            prepared.setString(1, token);
            rs = prepared.executeQuery();

            if (rs.next()) {

                Token token2 = new Token();
                token2.setIdUser(rs.getInt(1));
                token2.setToken(rs.getString(2));
                token2.setCreatedAt(rs.getDate(3));
               
                return token2;
            }

        } catch (Exception ex) {
            System.out.println("[SEARCH TOKEN] - " + ex.getMessage());
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
