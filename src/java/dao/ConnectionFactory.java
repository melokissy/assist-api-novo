/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Kissy de Melo
 */
public class ConnectionFactory {
        public Connection getConnection() throws SQLException, ClassNotFoundException, IOException, Exception{
        try{
           Class.forName("com.mysql.jdbc.Driver");
           Properties prop = new Properties();
           InputStream in = getClass().getClassLoader().getResourceAsStream("resources/database/config.properties");
           prop.load(in);
           in.close();
           String url = prop.getProperty("url");
           String senha = prop.getProperty("senha");
           String usuario = prop.getProperty("usuario");
           return DriverManager.getConnection(url, usuario, senha);          
        } catch(SQLException e){
            throw new SQLException("Erro ao abrir conexao com banco de dados "+e.getMessage());
        } catch (IOException e) {
            throw new IOException("Erro IOException "+e.getMessage());           
        } catch(ClassNotFoundException e){
            throw new ClassNotFoundException("Erro ClassNotFoundException "+e.getMessage());
        } catch (Exception e) {
            throw new Exception("Erro Generic Exception "+e.getMessage());
        }     
    } 
}
