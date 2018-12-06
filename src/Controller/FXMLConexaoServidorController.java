/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Main.LoginFX;
import Model.ConfiguracaoServidor;
import Model.UrlServidor;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLConexaoServidorController implements Initializable {

    @FXML private TextField textfield_ip;
    @FXML private TextField textfield_porta;
    @FXML private Button button_confirmar;
    
    
    @FXML 
    void button_confirmar(ActionEvent event) throws Exception {
        ConfiguracaoServidor config = new ConfiguracaoServidor();
        config.gravacaoConfiguracaoServidor(textfield_ip.getText(), textfield_porta.getText());
        
        LoginFX login = new LoginFX();
        login.start(new Stage());
        fechaStage();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            iniciaVariaveis();
        } catch (IOException ex) {
            invocaErro("Erro", "Erro ao tentar ler o arquivo de configuração do servidor!");
        }
    }    

    
    public void invocaErro(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.show();
    }

    public void fechaStage(){
        Stage stage = (Stage) button_confirmar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }

    public void iniciaVariaveis() throws IOException{
        ConfiguracaoServidor config = new ConfiguracaoServidor();
        config.leituraConfiguracaoServidor();
        textfield_ip.setText(config.getIpServidor());
        textfield_porta.setText(config.getPortaServidor());
        
    }
    
    
}
