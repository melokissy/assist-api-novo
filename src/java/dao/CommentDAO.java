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
import model.Comment;
import model.Project;
import model.Ticket;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
public class CommentDAO {

    private static final String SEARCH = "SELECT * FROM comments";
    private static final String SEARCH_BY_ID = "SELECT idComment, ticket_id, comment,user_id, createdAt FROM comment WHERE ticket_id=?";
    private static final String EDIT_COMMENT = "UPDATE comment SET comment = ? WHERE idComment = ?";
    private static final String NEW_COMMENT = "INSERT INTO comment (user_id, comment, ticket_id, createdAt) VALUES (?,?,?,?)";
    private static final String DELETE_COMMENT = "DELETE * FROM comment WHERE idComment = ?";

    public CommentDAO() {
    }

    public List<Comment> listCommentsByTicketId(int idTicket) {
        Connection conn = null;
        List<Comment> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            list = new ArrayList();
            prepared = conn.prepareStatement(SEARCH_BY_ID);
            prepared.setInt(1, idTicket);
            rs = prepared.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt(1));
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(2));
                comment.setTicket(ticket);
                comment.setComment(rs.getString(3));
                User user = new User();
                user.setId(rs.getInt(4));
                comment.setUser(user);
                comment.setCreatedAt(rs.getDate(5));
                
                list.add(comment);
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
    
    public Comment searchById(int id) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(SEARCH_BY_ID);
            prepared.setInt(1, id);
            rs = prepared.executeQuery();

            if (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt(1));
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(2));
                comment.setTicket(ticket);
                comment.setComment(rs.getString(3));
                User user = new User();
                user.setId(rs.getInt(4));
                comment.setUser(user);
                comment.setCreatedAt(rs.getDate(5));

                return comment;
            }

        } catch (Exception ex) {
            System.out.println("[SEARCH COMEMNT BY ID] - " + ex.getMessage());
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
    
    public void insertComment(Comment comment){
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(NEW_COMMENT, Statement.RETURN_GENERATED_KEYS);
            prepared.setInt(1, comment.getUser().getId());
            prepared.setString(2, comment.getComment());
            prepared.setInt(3, comment.getTicket().getId());
            prepared.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
            prepared.executeUpdate();
            rs = prepared.getGeneratedKeys();
            System.out.println("PASSOU DO INSERT DO COMMENT");
            if (rs.next()) {
                comment.setId(rs.getInt(1));
            }
         } catch (Exception ex) {
            System.out.println("[COMMNET ERROR INSERT] - " + ex.getMessage());
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
    
     public Comment delete(Comment comment) {
        Connection conn = null;
        PreparedStatement prepared = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(DELETE_COMMENT);
            prepared.setInt(1, comment.getId());
            prepared.executeUpdate();
            return comment;
            
        } catch (Exception ex) {
            System.out.println("[COMMENT DELETE] - " + ex.getMessage());
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
        return comment;
    }
}
