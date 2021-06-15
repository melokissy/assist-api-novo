/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Kissy de Melo
 */
public class Ticket {
    private int id;
    private String number; 
    private String subject; 
    private String description;
    private User requester;
    private String type;
    private String priority;
    private String status; 
    private Project project; 
    private User responsible;
    private Date createdAt;
    private Date editedAt; 
    private Date closedAt; 
    private Date dueDate;
    private List<Comment> comment; 
    private List<Historic> historic;
//    private Date estimativa;
            
    public Ticket(){};
    
      public Ticket(int id, String number, String subject, String description, User requester, String type, String priority, String status, Project project, User responsible, Date createdAt, Date editedAt, Date closedAt, Date dueDate) {
        this.id = id;
        this.number = number;
        this.subject = subject;
        this.description = description;
        this.requester = requester;
        this.type = type;
        this.priority = priority;
        this.status = status;
        this.project = project;
        this.responsible = responsible;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
        this.closedAt = closedAt;
        this.dueDate = dueDate;
    }

    public Ticket(int id, String number, String subject, String description, User requester, String type, String priority, String status, Project project, User responsible, Date createdAt, Date editedAt, Date closedAt, Date dueDate, List<Comment> comment, List<Historic> historic) {
        this.id = id;
        this.number = number;
        this.subject = subject;
        this.description = description;
        this.requester = requester;
        this.type = type;
        this.priority = priority;
        this.status = status;
        this.project = project;
        this.responsible = responsible;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
        this.closedAt = closedAt;
        this.dueDate = dueDate;
        this.comment = comment;
        this.historic = historic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Date editedAt) {
        this.editedAt = editedAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }    

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public List<Historic> getHistoric() {
        return historic;
    }

    public void setHistoric(List<Historic> historic) {
        this.historic = historic;
    }
      
    
}
