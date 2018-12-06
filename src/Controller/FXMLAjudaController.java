/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Main.PedidosFX;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLAjudaController implements Initializable {

    
    
    @FXML
    private Button button_fechar;

    @FXML
    void button_fechar(ActionEvent event) throws Exception {
        PedidosFX pedidos = new PedidosFX();
        pedidos.start(new Stage());
        fechaStage();

    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void fechaStage() {
        Stage stage = (Stage) button_fechar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
}
