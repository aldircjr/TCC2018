/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.PedidosResource;
import Main.PedidosFX;
import Model.Configuracao;
import Model.Pedido;
import WebServices.ConfiguracaoResource;
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
public class FXMLPedidoNovoController implements Initializable {

    @FXML private Button button_voltar;
    @FXML private Button button_salvar;
    @FXML private Label label_produto;
    @FXML private Label label_fornecedor;
    @FXML private TextField textfield_quantidade;
    @FXML private TextField textfield_assunto;
    @FXML private TextArea textarea_mensagem;
    Configuracao config;

    @FXML
    void button_salvar(ActionEvent event) throws IOException, Exception {
        if(!textarea_mensagem.getText().equals("") || !textfield_assunto.getText().equals("") || !textfield_quantidade.getText().equals("")){
            PedidosResource pedidoResource = new PedidosResource();
            Gson json = new Gson();
            
            Pedido pedido = new Pedido();
            pedido.setAssunto(textfield_assunto.getText());
            pedido.setConfig(this.config);
            pedido.setCorpo(textarea_mensagem.getText());
            pedido.setDataEnvio(null);
            pedido.setDataRecebido(null);
            Static.ProdutoEdit.ProdutoStatic.getProduto().setDataCadastro(null);
            pedido.setP(Static.ProdutoEdit.ProdutoStatic.getProduto());
            pedido.setQuantidade(Integer.parseInt(textfield_quantidade.getText()));
            
            pedidoResource.create(json.toJson(pedido));
            Static.ProdutoEdit.ProdutoStatic.setNull();
            
            PedidosFX pedidosfx = new PedidosFX();
            pedidosfx.start(new Stage());
            fechaStage();
            
            
        } else {
            invocaErro("Erro", "Não deve existir campos vazios!");
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
        populaPedidoNovo();
        buscaConfiguracao();
    }    
    
    private void fechaStage() {
        Stage stage = (Stage) button_voltar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    public void populaPedidoNovo(){
        label_produto.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getNome());
        label_fornecedor.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getFornecedor().getNome());
//        textfield_quantidade.setText(""+Static.PedidoEdit.ProdutoStatic.getPedido().getQuantidade());
//        textfield_assunto.setText(Static.PedidoEdit.ProdutoStatic.getPedido().getAssunto());
//        textarea_mensagem.setText(Static.PedidoEdit.ProdutoStatic.getPedido().getCorpo());
    }
    
    public void buscaConfiguracao(){
        try {
            ConfiguracaoResource configResource = new ConfiguracaoResource();
            if(configResource.countREST().equals("1")){
                Gson json = new Gson();
                this.config = json.fromJson(configResource.find("1"), Configuracao.class);
            } else {
                System.out.println("nao tem configuracao cadastrada ainda!");
            }
        } catch (IOException ex) {
            invocaErro("Erro", "Não foi possivel buscar as configurações da empresa e de email!");
        }
    }
    
    public void invocaErro(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.showAndWait();
    }
    
}
