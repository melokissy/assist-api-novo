/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CommentDAO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Comment;
import model.User;
import sun.text.resources.FormatData;

/**
 *
 * @author Kissy de Melo
 */
public class CommentController {

    private final CommentDAO commentDAO = new CommentDAO();
    private final UserController userController = new UserController();

    public List<Comment> searchCommentsByTicket(Integer id) throws Exception {
        try {
            List<Comment> comentarios = commentDAO.listCommentsByTicketId(id);

            if (!comentarios.isEmpty()) {
                for (int i = 0; i < comentarios.size(); i++) {
                    if (comentarios.get(i).getUser().getId() != null) {
                        try {
                            User usuario = userController.getUserById(comentarios.get(i).getUser().getId());
                            comentarios.get(i).setUser(usuario);
                        } catch (Exception e) {
                            System.out.println("[NAO LOCALIZOU O USUARIO COMMENT] - " + e.getMessage());
                        }

                    }
                }
            }
            return comentarios;
            
        } catch (Exception e) {
            throw new Exception("Não foi possível localizar o projeto");
        }
    }

    public Comment insert(Comment comment) throws Exception {
        try {
            commentDAO.insertComment(comment);
        } catch (Exception e) {
            throw new Exception("Não foi possivel cadastrar comentário!");
        }
        return comment;
    }

}
