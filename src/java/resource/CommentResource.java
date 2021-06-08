/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import controller.CommentController;
import controller.ProjectController;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Comment;
import model.Project;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
@Path("comments")
public class CommentResource {
    
    @Context
    private UriInfo context;

    private final CommentController commentController;

    public CommentResource() {
        this.commentController = new CommentController();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response insert(Comment comment) throws Exception {
        comment = this.commentController.insert(comment);
        return Response
                .ok(Response.Status.CREATED)
                .entity(comment)
                .build();
    }

}
