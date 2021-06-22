/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import controller.AttachmentController;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Attachment;
import model.Comment;

/**
 *
 * @author Kissy de Melo
 */
@Path("anexos")
public class AttachmentResource {
    
    @Context
    private UriInfo context;
    
    private final AttachmentController attachmentController;

    public AttachmentResource() {
        this.attachmentController = new AttachmentController();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response insert(Part anexo) throws Exception {
        Part hehe  = anexo;
//        anexo = this.attachmentController.insert(anexo);

        return Response
                .ok(Response.Status.CREATED)
                .entity(anexo)
                .build();
    }
}
