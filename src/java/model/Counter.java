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
public class Counter {
    String qtdPendentes;
    String qtdTotal;
    String qtdConcluidos;
    String qtdApropriados;

    public Counter() {
    }

    public String getQtdPendentes() {
        return qtdPendentes;
    }

    public void setQtdPendentes(String qtdPendentes) {
        this.qtdPendentes = qtdPendentes;
    }

    public String getQtdTotal() {
        return qtdTotal;
    }

    public void setQtdTotal(String qtdTotal) {
        this.qtdTotal = qtdTotal;
    }

    public String getQtdConcluidos() {
        return qtdConcluidos;
    }

    public void setQtdConcluidos(String qtdConcluidos) {
        this.qtdConcluidos = qtdConcluidos;
    }

    public String getQtdApropriados() {
        return qtdApropriados;
    }

    public void setQtdApropriados(String qtdApropriados) {
        this.qtdApropriados = qtdApropriados;
    }
       
    
}
