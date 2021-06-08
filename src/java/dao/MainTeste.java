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

/**
 *
 * @author Kissy de Melo
 */
public class MainTeste {
    private static final String NEW_USER = "INSERT INTO user (name , password, email, status) VALUES (?,?,?,?)";

    public static void main(String[] args) throws Exception {
        ConnectionFactory cf = new ConnectionFactory();  
        cf.getConnection();
        System.out.println("Deu certo");
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        conn = new ConnectionFactory().getConnection();
        prepared = conn.prepareStatement(NEW_USER, PreparedStatement.RETURN_GENERATED_KEYS);//
        prepared.setString(1, "kissy nemelo");
        prepared.setString(2, "123");
        prepared.setString(3, "kissy@gmail.com");
        prepared.setBoolean(4, true);
        prepared.executeUpdate();

        rs = prepared.getGeneratedKeys();
        rs.next();
        rs.getInt(1);
        System.out.println("chega aqui");
      
    }
    
}
