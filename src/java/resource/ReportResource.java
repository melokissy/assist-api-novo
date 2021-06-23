/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import controller.TicketController;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Ticket;

/**
 *
 * @author Kissy de Melo
 */
@Path("reports")
public class ReportResource {
    
    @Context
    private UriInfo context;

    private final TicketController ticketController;
    
    public ReportResource() {
        this.ticketController = new TicketController();
    }
    
    @GET
    @Path("/ticket-by-project/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response searchByProject(@PathParam("id") String id) throws Exception {

        Integer idProject = 0;
        idProject = Integer.parseInt(id);

        List<Ticket> tickets = this.ticketController.ticketByProject(idProject);
        
        GenericEntity<List<Ticket>> list = new GenericEntity<List<Ticket>>(tickets) {
        };
        return Response
                .ok()
                .entity(list)
                .build();
    }
    
}
