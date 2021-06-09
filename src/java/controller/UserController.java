/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Autenticacao.JWTTokenUtils;
import DTO.UserDTO;
import dao.TokenDAO;
import dao.UserDAO;
import helpers.ValidaCpf;
import java.util.List;
import model.Token;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
public class UserController {

    private final UserDAO userDao = new UserDAO();
    private final TokenDAO tokenDAO = new TokenDAO(); 
    private final ValidaCpf validaCpf = new ValidaCpf();

    public User getUserById(Integer idUser) {
        return this.userDao.search(idUser);
    }

    public User getUserByCpf(User user) {
        return this.userDao.searchByCpf(user.getCpf());
    }
    
      public UserDTO login(User user) {        
        user = this.userDao.login(user);
        if(user != null){
            String token = (new JWTTokenUtils()).generateToken(user);
                     
            Token token1 = tokenDAO.insertToken(token, user.getId());
            
            UserDTO userDTO = new UserDTO(user.getEmail(),user.getProfile(),token,user.getName(), user.getId());
            return userDTO;
        }
        return null;
    }
        
    public User insert(User user) throws Exception {
        try {
            
            if (!validaCpf.isValidCPF(user.getCpf())){
                return null;                    
            }
            
            User userExist = getUserByCpf(user); 
            
            if(userExist == null){
                user.setStatus(true);
                user.setPassword("123");
                userDao.insertUser(user);                
            }else{
                return null;                      
            }           

        } catch (Exception e) {
            throw new Exception("Não foi possivel cadastrar usuário");
        }
        return user;
    }

    public List<User> users() throws Exception {
        try {
            return userDao.users();
        } catch (Exception e) {
            throw new Exception("Não foi possível listar usuários");
        }
    }
    
    public List<User> usersByProfile() throws Exception {
        try {
            return userDao.usersByProfile();
        } catch (Exception e) {
            throw new Exception("Não foi possível listar usuários");
        }
    }

    public User update(User user) {
        User selectedUser = this.userDao.search(user.getId());

        if (user.getName() != null) {
            selectedUser.setName(user.getName());
        }

        if (user.getEmail() != null) {
            selectedUser.setEmail(user.getEmail());
        }

        if (user.getPassword() != null) {
            selectedUser.setPassword(user.getPassword());
        }

        if (user.getStatus() != selectedUser.getStatus()) {
            selectedUser.setStatus(user.getStatus());
        }
        
        if (user.getProfile()!= selectedUser.getProfile()){
            selectedUser.setProfile(user.getProfile());
        }           
                
        if (user.getCpf()!= selectedUser.getCpf()){
            selectedUser.setCpf(user.getCpf());
        }    
        return this.userDao.update(selectedUser);
    }

    public User delete(Integer idUser) {
        User selectedUser = this.userDao.search(idUser);
        return this.userDao.delete(selectedUser);
    }
}
