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
public class TicketDAO {

    private static final String NEW_TICKET = "INSERT INTO ticket (subject, description, requester_id, type, priority, status, project_id, createdAt,dueDate,number) VALUES (?,?,?,?,?,?,?,?,?,?)";
    //private static final String SEARCH_BY_ID = "SELECT idTicket, subject, description, requester_id, type, priority,status,project_id,responsible_id, createdAt, editedAt,dueDate FROM ticket WHERE idTicket=?";
    private static final String EDIT_TICKET = "UPDATE ticket SET subject = ?, description = ?, requester_id = ?, type = ?, priority = ?, status = ?, project_id = ?, responsible_id = ?, editedAt = ?, dueDate=? WHERE idTicket = ?";
    private static final String RESOLVE = "UPDATE ticket SET status = ?, closedAt=? WHERE idTicket = ?";
    private static final String SEARCH = "SELECT idTicket, subject , description, requester_id, type, priority, status, project_id, responsible_id, createdAt, editedAt,dueDate, closedAt, number FROM ticket WHERE idTicket=?";
    private static final String DELETE_TICKET = "DELETE FROM ticket WHERE idTicket=?";
    private static final String PENNDING_TICKET = "select count(*) from ticket where status = 'Pendente' AND MONTH(createdAt) = MONTH(CURRENT_DATE())";
    private static final String RESOLVIDO_TICKET = "select count(*) from ticket where status = 'Resolvido' AND MONTH(createdAt) = MONTH(CURRENT_DATE())";
    private static final String VENCIDO_TICKET = "SELECT distinct t.idTicket, "
            + "                t.subject,  "
            + "                t.description,  "
            + "                t.requester_id,  "
            + "                t.type,  "
            + "                t.priority,  "
            + "                t.status,  "
            + "                t.responsible_id,  "
            + "                t.createdAt,  "
            + "                t.editedAt,  "
            + "                t.closedAt,  "
            + "                t.dueDate, "
            + "t.number"
            + "                FROM ticket t WHERE dueDate <= CURRENT_DATE() AND status != 'Resolvido' ORDER BY dueDate ASC ";
    private static final String AVENCER_TICKET = "SELECT distinct t.idTicket, "
            + "                t.subject,  "
            + "                t.description,  "
            + "                t.requester_id,  "
            + "                t.type,  "
            + "                t.priority,  "
            + "                t.status,  "
            + "                t.responsible_id,  "
            + "                t.createdAt,  "
            + "                t.editedAt,  "
            + "                t.closedAt,  "
            + "                t.dueDate, "
            + "t.number"
            + "                FROM ticket t WHERE status != 'resolvido' ORDER BY dueDate ASC ";
    private static final String APROPRIADO_TICKET = "select count(*) from ticket where responsible_id is not null AND responsible_id !=0 AND MONTH(createdAt) = MONTH(CURRENT_DATE())";
    private static final String TOTAL_TICKET = "select count(*) from ticket where MONTH(createdAt) = MONTH(CURRENT_DATE()) ";
    private static final String TICKETS = "SELECT distinct t.idTicket, "
            + "t.number,"
            + "                t.subject,  "
            + "                t.description,  "
            + "                t.requester_id,  "
            + "                t.type,  "
            + "                t.priority,  "
            + "                t.status,  "
            + "                t.responsible_id,  "
            + "                t.createdAt,  "
            + "                t.editedAt,  "
            + "                t.closedAt,  "
            + "                t.dueDate "
            + "                FROM ticket t order by number desc";
    private static final String TOTAL_TK_USER = "SELECT count(*), t.responsible_id, u.name FROM ticket t  \n"
            + "inner join user u on t.responsible_id = u.idUser\n"
            + "where t.responsible_id is not null group by t.responsible_id ";
    private static final String COUNT = "select count(*) from ticket";
    private static final String COUNT_BY_PROJECT = "select count(*) from ticket where project_id = ?";
    private static final String TICKET_COMMENTS = "SELECT idComment, user_id, createdAt, comment FROM comment where idComment=?";
    private static final String TICKET_BY_PROJECT = "SELECT idTicket, subject , description, requester_id, type, priority, status, project_id, responsible_id, "
            + "createdAt, editedAt,dueDate, closedAt, number FROM ticket WHERE project_id = ?";

    public TicketDAO() {
    }

    //lista todos os tickets
    public List<Ticket> tickets() {
        Connection conn = null;
        List<Ticket> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            list = new ArrayList();
            prepared = conn.prepareStatement(TICKETS);
            rs = prepared.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setNumber(rs.getString(2));
                ticket.setSubject(rs.getString(3));
                ticket.setDescription(rs.getString(4));

                //id e nome do usuario solicitante
                User userRequest = new User();
                userRequest.setId(rs.getInt(5));
                ticket.setRequester(userRequest);

                ticket.setType(rs.getString(6));
                ticket.setPriority(rs.getString(7));
                ticket.setStatus(rs.getString(8));

                //pega o ususario responsavel
                User userResponsible = new User();
                userResponsible.setId(rs.getInt(9));
                ticket.setResponsible(userResponsible);

                ticket.setCreatedAt(rs.getDate(10));
                ticket.setEditedAt(rs.getDate(11));
                ticket.setClosedAt(rs.getDate(12));
                ticket.setDueDate(rs.getDate(13));
                list.add(ticket);
            }

            return list;
        } catch (Exception e) {
            System.out.println("ERROR LISTA TICKETS- " + e.getMessage());
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

    public Ticket search(int id) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(SEARCH);
            prepared.setInt(1, id);
            rs = prepared.executeQuery();

            if (rs.next()) {

                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setSubject(rs.getString(2));
                ticket.setDescription(rs.getString(3));

                //id e nome do usuario solicitante
                User userRequest = new User();
                userRequest.setId(rs.getInt(4));
                ticket.setRequester(userRequest);

                ticket.setType(rs.getString(5));
                ticket.setPriority(rs.getString(6));
                ticket.setStatus(rs.getString(7));

                //pega o ususario projeto
                Project project = new Project();
                project.setId(rs.getInt(8));
                ticket.setProject(project);

                //pega o ususario responsavel
                User userResponsible = new User();
                userResponsible.setId(rs.getInt(9));
                ticket.setResponsible(userResponsible);

                ticket.setCreatedAt(rs.getDate(10));
                ticket.setEditedAt(rs.getDate(11));
                ticket.setDueDate(rs.getDate(12));
                ticket.setClosedAt(rs.getDate(13));
                ticket.setNumber(rs.getString(14));

                return ticket;
            }

        } catch (Exception ex) {
            System.out.println("[SEARCH TICKET] - " + ex.getMessage());
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

    //lista tickets por IDProject
    public List<Ticket> ticketsByProject() {
        Connection conn = null;
        List<Ticket> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(TICKET_BY_PROJECT);

            Project project = new Project();
            prepared.setInt(1, project.getId());

            rs = prepared.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setSubject(rs.getString(2));
                ticket.setDescription(rs.getString(3));

                User userRequest = new User();
                userRequest.setId(rs.getInt(4));
                ticket.setRequester(userRequest);

                ticket.setType(rs.getString(5));
                ticket.setPriority(rs.getString(6));
                ticket.setStatus(rs.getString(7));

                project.setId(rs.getInt(8));
                ticket.setProject(project);

                User userResponsible = new User();
                userResponsible.setId(rs.getInt(9));
                ticket.setResponsible(userResponsible);

                ticket.setCreatedAt(rs.getDate(10));
                ticket.setEditedAt(rs.getDate(11));
                ticket.setDueDate(rs.getDate(12));
                ticket.setClosedAt(rs.getDate(13));
                ticket.setNumber(rs.getString(14));
                list.add(ticket);
            }

            return list;
        } catch (Exception e) {
            System.out.println("ERROR LISTA TICKETS BY PROJECT - " + e.getMessage());
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

    public Integer countTickets() {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(COUNT);
            rs = prepared.executeQuery();

            if (rs.next()) {
                Integer qtdTicket = null;
                qtdTicket = rs.getInt(1);
                return qtdTicket;
            }
        } catch (Exception ex) {
            System.out.println("[CONTADOR DE TICKETS] - " + ex.getMessage());
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
    
    // conta tickets pelo projecr
    public Integer countTicketsByProject(int idProject) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(COUNT_BY_PROJECT);
            prepared.setInt(1, idProject);
            rs = prepared.executeQuery();

            if (rs.next()) {
                Integer qtdTicketProject = null;
                qtdTicketProject = rs.getInt(1);
                return qtdTicketProject;
            }
        } catch (Exception ex) {
            System.out.println("[CONTADOR DE TICKETS] - " + ex.getMessage());
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

    //adiciona novo ticket
    public void insertTicket(Ticket ticket) {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;
        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(NEW_TICKET, Statement.RETURN_GENERATED_KEYS);
            prepared.setString(1, ticket.getSubject());
            prepared.setString(2, ticket.getDescription());

            //id e nome do usuario solicitante           
            prepared.setInt(3, ticket.getRequester().getId());
            // prepared.setString(4, ticket.getType());
            prepared.setString(4, ticket.getType());
            prepared.setString(5, ticket.getPriority());
            prepared.setString(6, "Pendente");
            //projeto
            prepared.setInt(7, ticket.getProject().getId());
            //pega o ususario responsavel
            prepared.setDate(8, java.sql.Date.valueOf(java.time.LocalDate.now()));
            prepared.setDate(9, java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(8)));
            prepared.setString(10, ticket.getNumber());

            prepared.executeUpdate();
            rs = prepared.getGeneratedKeys();
            System.out.println("PASSOU DO INSERT DO TICKET");

            if (rs.next()) {
                ticket.setId(rs.getInt(1));
            }

        } catch (Exception ex) {
            System.out.println("[TICKET STORE] - " + ex.getMessage());
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

    public Ticket update(Ticket ticket) {
        Connection conn = null;
        PreparedStatement prepared = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(EDIT_TICKET);
            prepared.setString(1, ticket.getSubject());
            prepared.setString(2, ticket.getDescription());
            prepared.setString(3, ticket.getRequester().getName());
            prepared.setString(4, ticket.getType());
            prepared.setString(5, ticket.getPriority());
            prepared.setInt(6, ticket.getId());
            prepared.setString(7, ticket.getStatus());
            prepared.setInt(8, ticket.getProject().getId());
            prepared.setString(9, ticket.getResponsible().getName());
            prepared.setDate(10, java.sql.Date.valueOf(java.time.LocalDate.now()));
//            prepared.setString(11, ticket.getDueDate());
            prepared.executeUpdate();
            return ticket;

        } catch (Exception ex) {
            System.out.println("[TICKET UPDATE] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }
            } catch (Exception ex) {
                System.out.println("Error close connections" + ex.getMessage());
            }
        }

        return ticket;
    }

    public Ticket resolveTicket(Ticket ticket) {
        Connection conn = null;
        PreparedStatement prepared = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(RESOLVE);
            prepared.setString(1, "Resolvido");
            prepared.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
            prepared.setInt(3, ticket.getId());
            prepared.executeUpdate();
            return ticket;

        } catch (Exception ex) {
            System.out.println("[TICKET RESOLVE] - " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (prepared != null) {
                    prepared.close();
                }
            } catch (Exception ex) {
                System.out.println("Error close connections" + ex.getMessage());
            }
        }

        return ticket;
    }

    public Ticket delete(Ticket ticket) {
        Connection conn = null;
        PreparedStatement prepared = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(DELETE_TICKET);
            prepared.setInt(1, ticket.getId());
            prepared.executeUpdate();
            return ticket;

        } catch (Exception ex) {
            System.out.println("[TICKET DELETE] - " + ex.getMessage());
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

        return ticket;
    }

    public Integer totalPendente() {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(PENNDING_TICKET);
            rs = prepared.executeQuery();

            if (rs.next()) {
                Integer ticketsPednentes = null;
                ticketsPednentes = rs.getInt(1);
                return ticketsPednentes;
            }

        } catch (Exception ex) {
            System.out.println("[COUNT PENDENTE TICKET] - " + ex.getMessage());
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

    public Integer totalTickets() {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(TOTAL_TICKET);
            rs = prepared.executeQuery();

            if (rs.next()) {
                Integer ticketsPednentes = null;
                ticketsPednentes = rs.getInt(1);
                return ticketsPednentes;
            }

        } catch (Exception ex) {
            System.out.println("[COUNT PENDENTE TICKET] - " + ex.getMessage());
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

    public Integer totalApropriados() {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(APROPRIADO_TICKET);
            rs = prepared.executeQuery();

            if (rs.next()) {
                Integer ticketsPednentes = null;
                ticketsPednentes = rs.getInt(1);
                return ticketsPednentes;
            }

        } catch (Exception ex) {
            System.out.println("[COUNT PENDENTE TICKET] - " + ex.getMessage());
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

    public Integer totalConcluidos() {
        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            prepared = conn.prepareStatement(RESOLVIDO_TICKET);
            rs = prepared.executeQuery();

            if (rs.next()) {
                Integer ticketsPednentes = null;
                ticketsPednentes = rs.getInt(1);
                return ticketsPednentes;
            }

        } catch (Exception ex) {
            System.out.println("[COUNT PENDENTE TICKET] - " + ex.getMessage());
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

    //tickets vencidos
    public List<Ticket> ticketsVencidos() {
        Connection conn = null;
        List<Ticket> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            list = new ArrayList();
            prepared = conn.prepareStatement(VENCIDO_TICKET);
            rs = prepared.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setSubject(rs.getString(2));
                ticket.setDescription(rs.getString(3));

                //id e nome do usuario solicitante
                User userRequest = new User();
                userRequest.setId(rs.getInt(4));
                ticket.setRequester(userRequest);

                ticket.setType(rs.getString(5));
                ticket.setPriority(rs.getString(6));
                ticket.setStatus(rs.getString(7));

                //pega o ususario responsavel
                User userResponsible = new User();
                userResponsible.setId(rs.getInt(8));
                ticket.setResponsible(userResponsible);

                ticket.setCreatedAt(rs.getDate(9));
                ticket.setEditedAt(rs.getDate(10));
                ticket.setClosedAt(rs.getDate(11));
                ticket.setDueDate(rs.getDate(12));
                ticket.setNumber(rs.getString(13));
                list.add(ticket);
                System.out.println("PASSOU NO SELECT DO TICKTES VENCIDOS");
            }

            return list;
        } catch (Exception e) {
            System.out.println("ERROR LISTA TICKETS VENCIDOS - " + e.getMessage());
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

    public List<Ticket> ticketsAVencer() {
        Connection conn = null;
        List<Ticket> list = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            list = new ArrayList();
            prepared = conn.prepareStatement(AVENCER_TICKET);
            rs = prepared.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setSubject(rs.getString(2));
                ticket.setDescription(rs.getString(3));

                //id e nome do usuario solicitante
                User userRequest = new User();
                userRequest.setId(rs.getInt(4));
                ticket.setRequester(userRequest);

                ticket.setType(rs.getString(5));
                ticket.setPriority(rs.getString(6));
                ticket.setStatus(rs.getString(7));

                //pega o ususario responsavel
                User userResponsible = new User();
                userResponsible.setId(rs.getInt(8));
                ticket.setResponsible(userResponsible);

                ticket.setCreatedAt(rs.getDate(9));
                ticket.setEditedAt(rs.getDate(10));
                ticket.setClosedAt(rs.getDate(11));
                ticket.setDueDate(rs.getDate(12));
                ticket.setNumber(rs.getString(13));
                list.add(ticket);
            }
            System.out.println("PASSOU NO SELECT DO TICKTES A VENCER");
            return list;
        } catch (Exception e) {
            System.out.println("ERROR LISTA TICKETS VENCIDOS - " + e.getMessage());
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

}
