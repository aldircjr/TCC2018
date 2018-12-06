/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author aldir
 */
public class Permissoes {
    private Integer ID;
    private boolean email;
    private boolean usuario;
    private boolean produto;
    private boolean fornecedor;
    private boolean configuracao;
    private boolean estatistica;

    public Permissoes(boolean email, boolean usuario, boolean produto, boolean fornecedor, boolean configuracao, boolean estatistica) {
        this.email = email;
        this.usuario = usuario;
        this.produto = produto;
        this.fornecedor = fornecedor;
        this.configuracao = configuracao;
        this.estatistica = estatistica;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public boolean isUsuario() {
        return usuario;
    }

    public void setUsuario(boolean usuario) {
        this.usuario = usuario;
    }

    public boolean isProduto() {
        return produto;
    }

    public void setProduto(boolean produto) {
        this.produto = produto;
    }

    public boolean isFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(boolean fornecedor) {
        this.fornecedor = fornecedor;
    }

    public boolean isConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(boolean configuracao) {
        this.configuracao = configuracao;
    }

    public boolean isEstatistica() {
        return estatistica;
    }

    public void setEstatistica(boolean estatistica) {
        this.estatistica = estatistica;
    }
    
    
}
