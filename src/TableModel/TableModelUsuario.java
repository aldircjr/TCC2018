/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Model.Usuario;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author aldir
 */
public class TableModelUsuario {
    private String nome;
    private ImageView icon = new ImageView();

    public TableModelUsuario(Usuario usuario) {
        this.nome = usuario.getNome();
        
        if(usuario.isAtivo()){
            this.icon.setImage(new Image("/icon/confirmar.png"));
        } else {
            this.icon.setImage(new Image("/icon/bloquear.png"));
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

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }
    
    
    
    
}
