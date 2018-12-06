/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.PedidosResource;
import Main.PedidosFX;
import Model.Pedido;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLPedidoEditarController implements Initializable {

   
    @FXML private Button button_voltar;
    @FXML private Button button_salvar;
    @FXML private Label label_produto;
    @FXML private Label label_fornecedor;
    @FXML private TextField textfield_quantidade;
    @FXML private TextField textfield_assunto;
    @FXML private TextArea textarea_mensagem;
    
    @FXML
    void button_salvar(ActionEvent event) throws IOException {
        try{
            PedidosResource pedidoResource = new PedidosResource();
            Gson json = new Gson();

            Static.PedidoEdit.ProdutoStatic.getPedido().setQuantidade(Integer.parseInt(textfield_quantidade.getText()));
            Static.PedidoEdit.ProdutoStatic.getPedido().setAssunto(textfield_assunto.getText());
            Static.PedidoEdit.ProdutoStatic.getPedido().setCorpo(textarea_mensagem.getText());

            Static.PedidoEdit.ProdutoStatic.getPedido().setDataEnvio(null);
            Static.PedidoEdit.ProdutoStatic.getPedido().setDataRecebido(null);
            Static.PedidoEdit.ProdutoStatic.getPedido().getP().setDataCadastro(null);

            pedidoResource.send(new Gson().toJson(Static.PedidoEdit.ProdutoStatic.getPedido()));
            
            Static.PedidoEdit.ProdutoStatic.setNull();
             
            PedidosFX pedidos = new PedidosFX();
            pedidos.start(new Stage());
            fechaStage();
        
        } catch (Exception e){
            invocaErro("Erro", "Um Erro inesperado aconteceu!");
        }
    }

    @FXML
    void button_voltar(ActionEvent event) throws Exception {
        Static.PedidoEdit.ProdutoStatic.setNull();
        PedidosFX pedidos = new PedidosFX();
        pedidos.start(new Stage());
        fechaStage();
    }

    @FXML
    void textfield_quantidade_keyrelease(KeyEvent event) {
        textfield_quantidade.setText(textfield_quantidade.getText().replaceAll("[^0-9]", ""));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populaPedidoEdit();
    }    
    
    private void fechaStage() {
        Stage stage = (Stage) button_voltar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    public void populaPedidoEdit(){
        label_produto.setText(Static.PedidoEdit.ProdutoStatic.getPedido().getP().getNome());
        label_fornecedor.setText(Static.PedidoEdit.ProdutoStatic.getPedido().getP().getFornecedor().getNome());
        textfield_quantidade.setText(""+Static.PedidoEdit.ProdutoStatic.getPedido().getQuantidade());
        textfield_assunto.setText(Static.PedidoEdit.ProdutoStatic.getPedido().getAssunto());
        textarea_mensagem.setText(Static.PedidoEdit.ProdutoStatic.getPedido().getCorpo());
    }
    
    public void invocaErro(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.showAndWait();
    }
    
}
