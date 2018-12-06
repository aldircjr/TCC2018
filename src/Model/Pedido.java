/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author aldir
 */
public class Pedido {
    private Integer ID;
    private Produto produto;
    private Configuracao config;
    private Date dataRecebido;
    private Date dataEnvio;
    private String corpo;
    private String assunto;
    private int quantidade;
    private boolean recebido;
    private boolean enviado;
    private Usuario usuario;

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Produto getP() {
        return produto;
    }

    public void setP(Produto p) {
        this.produto = p;
    }

    public Configuracao getConfig() {
        return config;
    }

    public void setConfig(Configuracao config) {
        this.config = config;
    }

    public Date getDataRecebido() {
        return dataRecebido;
    }

    public void setDataRecebido(Date dataRecebido) {
        this.dataRecebido = dataRecebido;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isRecebido() {
        return recebido;
    }

    public void setRecebido(boolean recebido) {
        this.recebido = recebido;
    }
    
    
    
    
}
