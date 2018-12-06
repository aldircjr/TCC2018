/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Model.Produto;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author aldir
 */
public class TableModelEstatistica {
    private String nome;
    private String saldo;
    private ImageView icon = new ImageView();
    private ProgressBar nivelDeEstoque = new ProgressBar();

    public TableModelEstatistica(Produto produto) {
        this.nome = produto.getNome();
        this.saldo = ""+produto.getSaldo();
       
        this.nivelDeEstoque.setMinSize(140, 20);
        this.nivelDeEstoque.setMaxSize(140, 20);
        
        if(produto.getDiasEstoqueDisponivel() != -1) { //verificar se ja foi consumido, caso nunca consumido =-1
            System.out.println("esto dosp." + produto.getSaldo()/produto.getMediaConsumo());
            System.out.println("ideal." + produto.getEstoqueIdeal());
            Float resultado = (float)produto.getSaldo()/(float)produto.getMediaConsumo()/(float)produto.getEstoqueIdeal();
            
            if(resultado>1){
                this.nivelDeEstoque.setProgress(100.0);
            } else {
                this.nivelDeEstoque.setProgress(resultado); //terminar aqui!
            }
            
            
            
            if ((produto.getSaldo() / produto.getMediaConsumo()) < produto.getEstoqueDeSeguranca()) {
                this.nivelDeEstoque.setStyle("-fx-accent: red;");
            } else if ((produto.getSaldo() / produto.getMediaConsumo()) > produto.getEstoqueDeSeguranca() && (produto.getSaldo() / produto.getMediaConsumo()) <= produto.getPontoDeRessuprimento()) {
                this.nivelDeEstoque.setStyle("-fx-accent: yellow;");
            } else {
                this.nivelDeEstoque.setStyle("-fx-accent: green;");
            } 
            if ((produto.getSaldo()/produto.getMediaConsumo()) > produto.getEstoqueIdeal()){
                this.nivelDeEstoque.setStyle("-fx-accent: blue;");
            }
        }
        
        
        

        if (produto.getCurvaABC() != null){
            if(produto.getCurvaABC().equals("A")){
                this.icon.setImage(new Image("/icon/A.png"));
            } else if (produto.getCurvaABC().equals("B")){
                this.icon.setImage(new Image("/icon/B.png"));
            } else if (produto.getCurvaABC().equals("C")){
                this.icon.setImage(new Image("/icon/C.png"));
            }
        } else {
            this.icon.setImage(new Image("/icon/N.png"));
        }
        
        
        this.icon.setFitHeight(20);
        this.icon.setFitWidth(20);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public ProgressBar getNivelDeEstoque() {
        return nivelDeEstoque;
    }

    public void setNivelDeEstoque(ProgressBar nivelDeEstoque) {
        this.nivelDeEstoque = nivelDeEstoque;
    }
    
    
    
    

}
