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
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Comment;
import model.Historic;

/**
 *
 * @author Kissy de Melo
 */
public class HistoricDAO {

    private static final String SEARCH = "SELECT * FROM historic";
    private static final String SEARCH_BY_TICKET = "SELECT idHistoric, ticket_id, user_id, description, createdAt, status, subject, ticket_description, "
            + "responsible_id, priority, type from historic WHERE ticket_id=?";
    private static final String NEW_HISTORIC = "INSERT INTO historic (ticket_id, user_id, description, createdAt, status,  subject, ticket_description, "
            + "responsible_id, priority, type) VALUES (?,?,?,?,?,?,?,?,?,?)";

    public HistoricDAO() {
    };
    
    public List<Historic> listHistoricByTicketId(int idTicket) {
        Connection conn = null;
        List<Historic> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            list = new ArrayList();
            prepared = conn.prepareStatement(SEARCH_BY_TICKET);
            prepared.setInt(1, idTicket);
            rs = prepared.executeQuery();

            while (rs.next()) {
                Historic historic = new Historic();
                historic.setId(rs.getInt(1));
                historic.setTicket_id(rs.getInt(2));
                historic.setUser_id(rs.getInt(3));
                historic.setDescription(rs.getString(4));
                historic.setCreatedAt(rs.getDate(5));
                historic.setStatus(rs.getString(6));
                historic.setSubject(rs.getString(7));
                historic.setTicket_description(rs.getString(8));
                historic.setResponsible_id(rs.getInt(9));
                historic.setPriority(rs.getString(10));
                historic.setType(rs.getString(11));
                list.add(historic);
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

    public void insertHistoric(Historic historic) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(NEW_HISTORIC, Statement.RETURN_GENERATED_KEYS);
            prepared.setInt(1, historic.getTicket_id());
            prepared.setInt(2, historic.getUser_id());
            prepared.setString(3, historic.getDescription());
            prepared.setTimestamp(4, (Timestamp) historic.getCreatedAt());
            prepared.setString(5, historic.getStatus());
            prepared.setString(6, historic.getSubject());
            prepared.setString(7, historic.getTicket_description());
            prepared.setInt(8, historic.getResponsible_id());
            prepared.setString(9, historic.getPriority());
            prepared.setString(10, historic.getType());               

            prepared.executeUpdate();
            rs = prepared.getGeneratedKeys();
            System.out.println("PASSOU DO INSERT DO HISTORICO");
            if (rs.next()) {
                historic.setId(rs.getInt(1));
            }
        } catch (Exception ex) {
            System.out.println("[HISTORICO ERROR INSERT] - " + ex.getMessage());
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
}
