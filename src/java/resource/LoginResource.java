/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import DTO.UserDTO;
import controller.UserController;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
@Path("login")
public class LoginResource {

    @Context
    private UriInfo context;

    private final UserController userController;

    public LoginResource() {
        this.userController = new UserController();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response logar(User user) throws Exception {
        UserDTO userDTO = this.userController.login(user);
        if (userDTO != null) {
            return Response
                    .ok(Response.Status.OK)
                    .entity(userDTO)
                    .build();
        }
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .build();
    }

}
