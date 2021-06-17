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
import model.User;

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

            if (!historicos.isEmpty()) {
                for (int i = 0; i < historicos.size(); i++) {
                    if (historicos.get(i).getUser().getId() != null) {
                        try {
                            User usuario = userController.getUserById(historicos.get(i).getUser().getId());
                            historicos.get(i).setUser(usuario);
                        } catch (Exception e) {
                            System.out.println("[NAO LOCALIZOU O USUARIO HISTORICO] - " + e.getMessage());
                        }
                    }
                    if (historicos.get(i).getTicket_responsible().getId() != null) {
                        try {
                            User usuario = userController.getUserById(historicos.get(i).getTicket_responsible().getId());
                            historicos.get(i).setTicket_responsible(usuario);
                        } catch (Exception e) {
                            System.out.println("[NAO LOCALIZOU O USUARIO SOLICITANTE HISTORICO] - " + e.getMessage());
                        }
                    }
                }
            }
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
