/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Model.Movimentacao;

/**
 *
 * @author aldir
 */
public class TableModelMovimentacao {
    private String data;
    private String usuario;
    private String produto;
    private String movimentacao;

    public TableModelMovimentacao(Movimentacao movimentacao) {
        if (movimentacao.getData() != null){
            this.data = movimentacao.getData().toString();
        }
        this.usuario = movimentacao.getUsuario().getNome();
        this.produto = movimentacao.getProduto().getNome();
        if(movimentacao.getTipo() == 1){
            this.movimentacao = "+" + movimentacao.getQuantidade();
        } else if (movimentacao.getTipo() == 0){
            this.movimentacao = "-" + movimentacao.getQuantidade();
        }
        
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(String movimentacao) {
        this.movimentacao = movimentacao;
    }
    
    
}
