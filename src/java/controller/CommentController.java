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

            comentarios.forEach(comment -> {

                Date dataAtual = comment.getCreatedAt();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dataFormatada = dateFormat.format(dataAtual);

                try {
                    comment.setCreatedAt(
                            new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dataFormatada)
                    );
                } catch (ParseException ex) {
                    Logger.getLogger(CommentController.class.getName()).log(Level.SEVERE, null, ex);
                }

                comment.setUser(this.userController.getUserById(comment.getUser().getId()));
            });
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
