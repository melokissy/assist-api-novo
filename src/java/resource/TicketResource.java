/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import controller.TicketController;
import controller.UserController;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Counter;
import model.Ticket;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
@Path("tickets")
public class TicketResource {

    @Context
    private UriInfo context;

    private final TicketController ticketController;

    /**
     * Creates a new instance of GenericResource
     */
    public TicketResource() {
        this.ticketController = new TicketController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response index() throws Exception {
        List<Ticket> tickets = this.ticketController.tickets();
        GenericEntity<List<Ticket>> list = new GenericEntity<List<Ticket>>(tickets) {
        };
        return Response
                .ok()
                .entity(list)
                .build();
    }

    @GET
    @Path("/tickets-vencidos")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response ticketsVencidos() throws Exception {
        List<Ticket> tickets = this.ticketController.ticketsVencidos();
        GenericEntity<List<Ticket>> list = new GenericEntity<List<Ticket>>(tickets) {
        };
        return Response
                .ok()
                .entity(list)
                .build();
    }

    @GET
    @Path("/tickets-vencendo")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response ticketsAvencer() throws Exception {
        List<Ticket> tickets = this.ticketController.ticketsAVencer();
        GenericEntity<List<Ticket>> list = new GenericEntity<List<Ticket>>(tickets) {
        };
        return Response
                .ok()
                .entity(list)
                .build();
    }

    @GET
    @Path("/tickets-pendentes")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response totalPendentes() throws Exception {
        List<Counter> contadorTickets = new ArrayList<>();
        contadorTickets.add(this.ticketController.contaTickets());
        GenericEntity<List<Counter>> contador = new GenericEntity<List<Counter>>(contadorTickets) {
        };
        return Response
                .ok()
                .entity(contador)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response insert(Ticket ticket) throws Exception {
        
        //COLCOAR AQUI UMA VALIDAÇÃO PARA O CASO DO SOLICITANTE ESTAR DESATIVADO, NAO PODE CADASTRAR
        ticket = this.ticketController.insert(ticket);
        return Response
                .ok(Response.Status.CREATED)
                .entity(ticket)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response update(@PathParam("id") String id, Ticket ticket) throws Exception {
        ticket.setId(Integer.parseInt(id));
        ticket = this.ticketController.update(ticket);
        return Response
                .ok()
                .entity(ticket)
                .build();
    }

    @PUT
    @Path("ticket-resolve/{id}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response resolveTicket(@PathParam("id") String id, Ticket ticket) throws Exception {
        ticket = this.ticketController.resolveTicket(id);
        return Response
                .ok()
                .entity(ticket)
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response search(@PathParam("id") String id) throws Exception {
        Ticket ticket = this.ticketController.search(Integer.parseInt(id));
        return Response
                .ok(Response.Status.FOUND)
                .entity(ticket)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response delete(@PathParam("id") String id, Ticket ticket) {
        Integer idTicket = 0;
        idTicket = Integer.parseInt(id);
        ticket = this.ticketController.delete(idTicket);
        return Response
                .ok()
                .entity(ticket)
                .build();
    }

}
