/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.HistoricDAO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Comment;
import model.Historic;

/**
 *
 * @author Kissy de Melo
 */
public class HistoricController {
    
    private final HistoricDAO historicDAO = new HistoricDAO();
    private final UserController userController = new UserController();

    
    public List<Historic> searchHistoricByTicket(Integer id) throws Exception {
        try {
            List<Historic> historicos = historicDAO.listHistoricByTicketId(id);

            historicos.forEach(historic -> {

                Date dataAtual = historic.getCreatedAt();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dataFormatada = dateFormat.format(dataAtual);

                try {
                    historic.setCreatedAt(
                            new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dataFormatada)
                    );
                } catch (ParseException ex) {
                    Logger.getLogger(CommentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            return historicos;
            
        } catch (Exception e) {
            throw new Exception("Não foi possível localizar o projeto");
        }
    }

    public Historic insert(Historic historic) throws Exception {
        try {
            historicDAO.insertHistoric(historic);
        } catch (Exception e) {
            throw new Exception("Não foi possivel cadastrar historico!");
        }
        return historic;
    }

}
