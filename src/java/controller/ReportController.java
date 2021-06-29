/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.TicketDAO;
import java.util.List;
import model.Ticket;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
public class ReportController {
    
     private final TicketDAO tDAO = new TicketDAO();
    UserController userController = new UserController();
    
     public List<Ticket> ticketByResponsible(Integer idResponsible) throws Exception {

        List<Ticket> tickets = this.tDAO.ticketsByResponsible(idResponsible);
        if (!tickets.isEmpty()) {
            for (int i = 0; i < tickets.size(); i++) {
                if (tickets.get(i).getRequester().getId() != null) {
                    try {
                        User usuario = userController.getUserById(tickets.get(i).getRequester().getId());
                        usuario.setPassword("");
                        tickets.get(i).setRequester(usuario);
                    } catch (Exception e) {
                        System.out.println("[NAO LOCALIZOU O REQUESTER] - " + e.getMessage());
                    }

                }
                try {
                    if (tickets.get(i).getResponsible().getId() > 0) {
                        User usuario = userController.getUserById(tickets.get(i).getResponsible().getId());
                        usuario.setPassword("");
                        tickets.get(i).setResponsible(usuario);
                    } else if (tickets.get(i).getResponsible().getId() == 0 || tickets.get(i).getResponsible().getId() == null) {
                        User usuario = new User();
                        tickets.get(i).setResponsible(usuario);
                    }
                } catch (Exception e) {
                    System.out.println("[ TICKET CONTROLLER - VALIDACAO DO RESPONSIBLE] - " + e.getMessage());

                }

            }
        }

        return tickets;
    }
}
