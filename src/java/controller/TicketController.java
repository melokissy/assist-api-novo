/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.TicketDAO;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.ext.ParamConverter.Lazy;
import model.Attachment;
import model.Comment;
import model.Counter;
import model.Historic;
import model.Ticket;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
public class TicketController {

    private final TicketDAO tDAO = new TicketDAO();
    UserController userController = new UserController();
    CommentController commentController = new CommentController();
    HistoricController historicController = new HistoricController();
    AttachmentController attachmentController = new AttachmentController();
    

    private String status = "Resolvido";

    public Ticket insert(Ticket ticket, User userLogado) throws Exception {
        try {

            int contador = tDAO.countTickets() + 1;
            if (contador < 100) {
                ticket.setNumber("TICKET-" + "00" + contador);
            }
            if (contador > 100) {
                ticket.setNumber("TICKET-" + "0" + contador);
            }

            ticket.setStatus("Pendente");
            tDAO.insertTicket(ticket);
            saveHistoric("Ticket criado", ticket, userLogado);

        } catch (Exception e) {
            throw new Exception("Não foi possivel cadastrar ticket");
        }
        return ticket;
    }

    public List<Ticket> tickets() {
        List<Ticket> tickets = this.tDAO.tickets();
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

    public List<Ticket> ticketByProject(Integer idProject) throws Exception {

        List<Ticket> tickets = this.tDAO.ticketsByProject(idProject);
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
    
    public List<Ticket> ticketByUser(Integer idUser) throws Exception {

        List<Ticket> tickets = this.tDAO.ticketsByUser(idUser);
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
    
    public Ticket search(Integer id) throws Exception {
        try {
            Ticket ticket = tDAO.search(id);
            List<Comment> comments = commentController.searchCommentsByTicket(id);
            List<Historic> historic = historicController.searchHistoricByTicket(id);
            List<Attachment> anexos = attachmentController.searchAnexosByTicket(id);


            if (!comments.isEmpty()) {
                ticket.setComment(comments);
            }
            if (!historic.isEmpty()) {
                ticket.setHistoric(historic);
            }
            if (!anexos.isEmpty()) {
                ticket.setAnexos(anexos);
            }
            return ticket;

        } catch (Exception e) {
            throw new Exception("Não foi possível localizar o ticket");
        }
    }

    public int countTicketByProject(Integer idProject) throws Exception {
        try {
            int contador = tDAO.countTicketsByProject(idProject);
            System.out.println("QTD NO CONTROLLER TICKET: " + contador);
            return contador;

        } catch (Exception e) {
            throw new Exception("Não foi possível localizar o ticket");
        }
    }

    public List<Ticket> ticketsVencidos() {
        List<Ticket> tickets = this.tDAO.ticketsVencidos();
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
                    System.out.println("[ TICKET CONTROLLER (VENCIDOS) - VALIDACAO DO RESPONSIBLE] - " + e.getMessage());

                }

            }
        }

        return tickets;
    }

    public void saveHistoric(String description, Ticket ticket, User userLogado) throws Exception {
        Historic historic = new Historic();

        historic.setDescription(description);
        historic.setTicket_description(ticket.getDescription());
        historic.setPriority(ticket.getPriority());
        historic.setSubject(ticket.getSubject());
        historic.setType(ticket.getType());
        if (ticket.getResponsible() != null) {
            historic.setTicket_responsible(ticket.getResponsible());
        }
        historic.setUser(userLogado);
        historic.setTicket_id(ticket.getId());
        historic.setStatus(ticket.getStatus());
        historic.setCreatedAt(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        historicController.insert(historic);
    }

    public Ticket update(Ticket ticket, User userLogado) throws Exception {

        Ticket selectedTicket = this.tDAO.search(ticket.getId());

        if (ticket.getSubject() != null){
            selectedTicket.setSubject(ticket.getSubject());
        }

        if (ticket.getDescription() != null) {
            selectedTicket.setDescription(ticket.getDescription());
        }

        if (ticket.getResponsible() != null) {
            selectedTicket.setResponsible(ticket.getResponsible());
        }

        if (ticket.getPriority() != null) {
            selectedTicket.setPriority(ticket.getPriority());
        }

        if (ticket.getType() != null) {
            selectedTicket.setType(ticket.getType());
        }

        if (ticket.getStatus() != null) {
            selectedTicket.setStatus(ticket.getStatus());
        }

        selectedTicket.setEditedAt(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        Ticket response = this.tDAO.update(selectedTicket);
        saveHistoric("Ticket atualizado", selectedTicket, userLogado);
        response.setRequester(userController.getUserById(response.getRequester().getId()));
        return response;

    }

    public Ticket resolveTicket(String id) throws Exception {
        try {
            Ticket selectedTicket = this.tDAO.search(Integer.parseInt(id));

            if (!selectedTicket.getStatus().equals("Resolvido")) {
                return this.tDAO.resolveTicket(selectedTicket);
            }
            
            return selectedTicket;
        } catch (Exception e) {
            throw new Exception("Ticket já se encontra resolvido");
        }

    }

    public Ticket delete(Integer idTicket) {
        Ticket selectedTicket = this.tDAO.search(idTicket);
        return this.tDAO.delete(selectedTicket);
    }

    public Integer totalPendente() {
        Integer qtdPendentes = tDAO.totalPendente();
        return qtdPendentes;
    }

    public Integer totalTickets() {
        Integer qtdTotal = tDAO.totalTickets();
        return qtdTotal;
    }

    public Integer totalApropriados() {
        Integer qtdApropriado = tDAO.totalApropriados();
        return qtdApropriado;
    }

    public Integer totalConcluidos() {
        Integer qtdConcluido = tDAO.totalConcluidos();
        return qtdConcluido;
    }

    public Counter contaTickets() {
        Counter contadorTickets = new Counter();
        contadorTickets.setQtdConcluidos(this.totalConcluidos().toString());
        contadorTickets.setQtdPendentes(this.totalPendente().toString());
        contadorTickets.setQtdApropriados(this.totalApropriados().toString());
        contadorTickets.setQtdTotal(this.totalTickets().toString());

        return contadorTickets;
    }

    public LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public List<Ticket> ticketsAVencer() {
        List<Ticket> ticketsUnsolved = tDAO.ticketsAVencer();
        List<Ticket> ticketsAVencer = new ArrayList<>();
        for (Ticket ticket : ticketsUnsolved) {
            LocalDate localDatetime = this.convertToLocalDateViaMilisecond(ticket.getDueDate());
            LocalDate dueDateCasting = LocalDate.of(localDatetime.getYear(), localDatetime.getMonth(), localDatetime.getDayOfMonth());
            Long diferencaEmDias = ChronoUnit.DAYS.between(LocalDate.now(), dueDateCasting);
            System.out.println("diferencaEmDias: " + diferencaEmDias);
            if (diferencaEmDias <= 3 && diferencaEmDias >= 0) {
                ticketsAVencer.add(ticket);
            }
        }
        return ticketsAVencer;
    }

}
