/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AttachmentDAO;
import java.util.List;
import model.Attachment;
import model.Comment;
import model.User;

/**
 *
 * @author Kissy de Melo
 */
public class AttachmentController {

    private final AttachmentDAO attachmentDAO = new AttachmentDAO();
    private final UserController userController = new UserController();

    public Attachment insert(Attachment attachment) throws Exception {
        try {
            attachmentDAO.insertAttachment(attachment);

        } catch (Exception e) {
            throw new Exception("Não foi possivel cadastrar anexo");
        }
        return attachment;
    }

    public List<Attachment> searchAnexosByTicket(Integer id) throws Exception {
        try {
            List<Attachment> anexos = attachmentDAO.listAnexoByTicket(id);

            if (!anexos.isEmpty()) {
                for (int i = 0; i < anexos.size(); i++) {
                    if (anexos.get(i).getUser().getId() != null) {
                        try {
                            User usuario = userController.getUserById(anexos.get(i).getUser().getId());
                            anexos.get(i).setUser(usuario);
                        } catch (Exception e) {
                            System.out.println("[NAO LOCALIZOU O USUARIO ANEXO] - " + e.getMessage());
                        }
                    }
                }
            }
            return anexos;

        } catch (Exception e) {
            throw new Exception("Não foi possível localizar o anexo");
        }
    }

    public Attachment delete(Integer idAnexo) throws Exception {

        Attachment selectAttachment = this.attachmentDAO.searchById(idAnexo);
        selectAttachment = this.attachmentDAO.searchById(idAnexo);
        return this.attachmentDAO.delete(selectAttachment);

    }
}
