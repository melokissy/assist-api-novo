/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Kissy de Melo
 */
public class User {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private Boolean status;
    private String userIcon;
    private String profile; 
    private String cpf; 
    private String setor; 
    private byte[] icone; 

    public User(){};

    public User(String name, String password, String email, Boolean status, String userIcon, String profile, String cpf, String setor, byte[] icone) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.status = status;
        this.userIcon = userIcon;
        this.profile = profile; 
        this.cpf = cpf;
        this.setor = setor;
        this.icone = icone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }    

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
        
    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public byte[] getIcone() {
        return icone;
    }

    public void setIcone(byte[] icone) {
        this.icone = icone;
    }
}
