/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Model.Pedido;
import Model.Produto;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author aldir
 */
public class TableModelPedidos {
    private String date;
    private String produtoNome;
    private String quantidade;
    private String fornecedorNome;
    private ImageView icon = new ImageView();
    private boolean recebido;
    private boolean enviado;
    private int id;
    

    public TableModelPedidos(Pedido pedido) {
        if (pedido.getDataEnvio() != null){
            this.date = pedido.getDataEnvio().toString();
        } else {
            this.date = "Não enviado";
        }
        this.id = pedido.getID();
        this.produtoNome = pedido.getP().getNome();
        this.quantidade = ""+pedido.getQuantidade();
        this.fornecedorNome = pedido.getP().getFornecedor().getNome();
        this.enviado = pedido.isEnviado();
        this.recebido = pedido.isRecebido();
        
        if(pedido.isRecebido() && pedido.isEnviado()){ //refazer a parte de verificar se o pedido esta saldo, enviado ou recebido
            this.icon.setImage(new Image("/icon/confirmar.png"));
        } else if (!pedido.isRecebido() && pedido.isEnviado()){
            this.icon.setImage(new Image("/icon/enviado.png"));
        } else if (!pedido.isRecebido() && !pedido.isEnviado()){
            this.icon.setImage(new Image("/icon/salvar.png"));
        }
        this.icon.setFitHeight(20);
        this.icon.setFitWidth(20);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getFornecedorNome() {
        return fornecedorNome;
    }

    public void setFornecedorNome(String fornecedorNome) {
        this.fornecedorNome = fornecedorNome;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public boolean isRecebido() {
        return recebido;
    }

    public void setRecebido(boolean recebido) {
        this.recebido = recebido;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    
    
}
