/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import controller.UserController;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
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
import model.User;

/**
 *
 * @author Kissy de Melo
 */
@Path("users")
public class UserResource {
    @Context
    private UriInfo context;

    private final UserController userController;

    /**
     * Creates a new instance of GenericResource
     */
    public UserResource() {
        this.userController = new UserController();
    }
    
//    @RolesAllowed({"administrador"})
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response index() throws Exception {
        List<User> users = this.userController.users();
        GenericEntity<List<User>> list = new GenericEntity<List<User>>(users) {};
        return Response
                .ok()
                .entity(list)
                .build();
    }
    
//    @RolesAllowed({"administrador"})
    @GET
    @Path("/by-profile")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response usersByProfile() throws Exception {
        List<User> users = this.userController.usersByProfile();
        GenericEntity<List<User>> list = new GenericEntity<List<User>>(users) {};
        return Response
                .ok()
                .entity(list)
                .build();
    } 
    
//    @RolesAllowed({"administrador"})
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response userById(@PathParam("id") int id) throws Exception {
        User user = this.userController.getUserById(id);
        GenericEntity<User> list = new GenericEntity<User>(user) {};
        return Response
                .ok()
                .entity(list)
                .build();
    }  

//    @RolesAllowed({"administrador"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response insert(User user) throws Exception {
        System.out.println("CHEGANDO AQUI");
        user = this.userController.insert(user);
          return Response
                .ok(Response.Status.CREATED)
                .entity(user)
                .build();
    }  
    
//    @RolesAllowed({"administrador"})
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response update(@PathParam("id")  String id, User user) {
        user.setId(Integer.parseInt(id));
        user = this.userController.update(user);
        return Response
                .ok()
                .entity(user)
                .build();
    }
    
//    @RolesAllowed({"administrador"})
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response delete(@PathParam("id")  String id, User user) {
        Integer idUser = 0;
          idUser = Integer.parseInt(id);
//        user.setId(Integer.parseInt(id));
        user = this.userController.delete(idUser);
        return Response
                .ok()
                .entity(user)
                .build();
    }
}
