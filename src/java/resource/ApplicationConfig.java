/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Kissy de Melo
 */
@javax.ws.rs.ApplicationPath("resource")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(provider.CORSFilter.class);
        resources.add(resource.AttachmentResource.class);
        resources.add(resource.CommentResource.class);
        resources.add(resource.HistoricResource.class);
        resources.add(resource.LoginResource.class);
        resources.add(resource.ProjectResource.class);
        resources.add(resource.TicketResource.class);
        resources.add(resource.UserResource.class);
    }
    
}
