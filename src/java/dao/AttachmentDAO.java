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
import java.util.ArrayList;
import java.util.List;
import model.Attachment;
import model.Ticket;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
public class AttachmentDAO {

    private static final String NEW_FILE = "INSERT INTO attachment (file, ticket_id, user_id, createdAt) VALUES (?,?,?,?)";
    private static final String DELETE_ATTACH = "DELETE * FROM attachment where idAttachment = ?";
    private static final String SEARCH_BY_ID = "SELECT idAttachment, file, ticket_id, user_id, createdAt FROM attachment where idAttachment = ?";
    private static final String SEARCH_BY_TICKET = "SELECT idAttachment, file, ticket_id, user_id, createdAt FROM attachment where ticket_id = ?";
    
    public AttachmentDAO() {
    }

    public void insertAttachment(Attachment attachment) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(NEW_FILE, Statement.RETURN_GENERATED_KEYS);
            prepared.setBytes(1, attachment.getFile());
            prepared.setInt(2, attachment.getTicket().getId());
            prepared.setInt(3, attachment.getUser().getId());
            prepared.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
            prepared.executeUpdate();
            rs = prepared.getGeneratedKeys();
            System.out.println("PASSOU DO INSERT DO ANEXO");
            if (rs.next()) {
                attachment.setId(rs.getInt(1));
            }
        } catch (Exception ex) {
            System.out.println("[ERROR ANEXO] - " + ex.getMessage());
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
    
    public List<Attachment> listAnexoByTicket(int idTicket){
        Connection conn = null;
        List<Attachment> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        
        try {
            conn = new ConnectionFactory().getConnection();
            list = new ArrayList();
            prepared = conn.prepareStatement(SEARCH_BY_ID);
            prepared.setInt(1, idTicket);
            rs = prepared.executeQuery();

            while (rs.next()) {
               
                Attachment anexo = new Attachment();
                anexo.setId(rs.getInt(1));
                anexo.setFile(rs.getBytes(2));
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(3));
                anexo.setTicket(ticket);
                User user = new User();
                user.setId(rs.getInt(4));
                anexo.setUser(user);
                anexo.setCreatedAt(rs.getDate(5));
                
                list.add(anexo);
            }

            return list;
        } catch (Exception e) {
            System.out.println("ERROR ANEXO BY TICKET - " + e.getMessage());
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

    public Attachment searchById(int id) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(SEARCH_BY_ID);
            prepared.setInt(1, id);
            rs = prepared.executeQuery();

            if (rs.next()) {

                Attachment anexo = new Attachment();
                anexo.setId(rs.getInt(1));
                anexo.setFile(rs.getBytes(2));
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(3));
                anexo.setTicket(ticket);
                User user = new User();
                user.setId(rs.getInt(4));
                anexo.setUser(user);
                anexo.setCreatedAt(rs.getDate(5));

                return anexo;
            }

        } catch (Exception ex) {
            System.out.println("[SEARCH ANEXO BY ID] - " + ex.getMessage());
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

    public Attachment delete(Attachment attachment) {
        Connection conn = null;
        PreparedStatement prepared = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(DELETE_ATTACH);
            prepared.setInt(1, attachment.getId());
            prepared.executeUpdate();
            return attachment;
        } catch (Exception ex) {
            System.out.println("[ANEXO DELETE] - " + ex.getMessage());
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
        return attachment;
    }

}
