/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import controller.ProjectController;
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
import model.Project;
import model.Ticket;

/**
 *
 * @author Kissy de Melo
 */
@Path("projects")
public class ProjectResource {

    @Context
    private UriInfo context;

    private final ProjectController projectController;

    public ProjectResource() {
        this.projectController = new ProjectController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response index() throws Exception {
        List<Project> projects = this.projectController.projects();
        GenericEntity<List<Project>> list = new GenericEntity<List<Project>>(projects) {
        };
        return Response
                .ok()
                .entity(list)
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response search(@PathParam("id") String id) throws Exception {
        Project project = this.projectController.search(Integer.parseInt(id));
        if(project != null){
             return Response
                .ok(Response.Status.FOUND)
                .entity(project)
                .build();            
        }
         return Response
                .status(Response.Status.NOT_FOUND)
                 .entity(null)
                .build();
       
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response insert(Project project) throws Exception {
        project = this.projectController.insert(project);
        return Response
                .ok(Response.Status.CREATED)
                .entity(project)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response update(@PathParam("id") String id, Project project) throws Exception {
        project.setId(Integer.parseInt(id));
        project = this.projectController.update(project);
        return Response
                .ok()
                .entity(project)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response delete(@PathParam("id") String id, Project project) {
        Integer idProject = 0;
        idProject = Integer.parseInt(id);
//        user.setId(Integer.parseInt(id));
        project = this.projectController.delete(idProject);
        return Response
                .ok()
                .entity(project)
                .build();
    }
}
