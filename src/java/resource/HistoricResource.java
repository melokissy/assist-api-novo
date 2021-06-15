/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import controller.HistoricController;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Comment;
import model.Historic;
import model.Project;

/**
 *
 * @author Kissy de Melo
 */
@Path("historic")
public class HistoricResource {
    
    @Context
    private UriInfo context;
    
    private final HistoricController historicController;
    
    public HistoricResource() {
        this.historicController = new HistoricController();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response insert(Historic historic) throws Exception {
        historic = this.historicController.insert(historic);
        
        if (historic == null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Não foi possivel criar historico")
                    .build();
        }
        return Response
            .ok(Response.Status.CREATED)
            .entity(historic)
            .build();

    }
    
}
