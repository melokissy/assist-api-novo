/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Kissy de Melo
 */
public class Comment {
    
    private Integer id;
    private String comment; 
    private User user; 
    private Date createdAt; 
    private Ticket ticket;

    
    public Comment(){};

    public Comment(Integer id, String comment, User user, Date createdAt, Ticket ticket) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.createdAt = createdAt;
        this.ticket = ticket;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

       public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    
    
      
    
}
