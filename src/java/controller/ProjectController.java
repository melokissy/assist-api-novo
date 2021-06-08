/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProjectDAO;
import java.util.List;
import model.Project;
import model.Ticket;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
public class ProjectController {

    private final ProjectDAO projectDao = new ProjectDAO();
    private final UserController userController = new UserController();

    public List<Project> projects() throws Exception {
        try {
            return projectDao.projects();
        } catch (Exception e) {
            throw new Exception("Não foi possível listar projeto");
        }
    }

    public Project search(Integer id) throws Exception {
        try {
            Project project = projectDao.search(id);
            if(project == null){
               return project;
            }
            User userResponsible = userController.getUserById(project.getResponsible().getId());
            project.setResponsible(userResponsible);
            return project;
        } catch (Exception e) {
            throw new Exception("Não foi possível localizar o projeto");
        }
    }

    public Project insert(Project project) throws Exception {
        try {
            int contador = projectDao.countProjects() + 1;
            if (contador < 100) {
                project.setNumber("PROJECT-" + "00" + contador);
            }
            if (contador > 100) {
                project.setNumber("PROJECT-" + "0" + contador);
            }
            projectDao.insertProject(project);
        } catch (Exception e) {
            throw new Exception("Não foi possivel cadastrar projeto");
        }
        return project;
    }

    public Project update(Project project) throws Exception {
        try {
            Project selectedProject = this.projectDao.search(project.getId());

            if (project.getResponsible() != null) {
                selectedProject.setResponsible(project.getResponsible());
            }
            if (project.getName() != null) {
                selectedProject.setName(project.getName());
            }
            if (project.getStatus()!= null) {
                selectedProject.setStatus(project.getStatus());
            }
            if (project.getDescription()!= null) {
                selectedProject.setDescription(project.getDescription());
            }
            projectDao.update(selectedProject);

        } catch (Exception e) {
            throw new Exception("Não foi possivel cadastrar projeto");
        }
        return project;
    }

    public Project delete(Integer idProject) {
        Project selectProject = this.projectDao.search(idProject);
        return this.projectDao.delete(selectProject);
    }
}
