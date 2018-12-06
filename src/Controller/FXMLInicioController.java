/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.PedidosResource;
import Main.ConfiguracaoFX;
import Main.EstatisticaFX;
import Main.FornecedorFX;
import Main.InicioFX;
import Main.LoginFX;
import Main.PedidosFX;
import Main.ProdutoFX;
import Main.UsuarioFX;
import Main.UsuarioNovoFX;
import Model.Pedido;
import Model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLInicioController implements Initializable {

    @FXML private Button buttonConfiguracao;
    @FXML private Button buttonUsuario;
    @FXML private Button buttonBloquear;
    @FXML private Button buttonSair;
    @FXML private Button buttonFornecedor;
    @FXML private Button buttonProduto;
    @FXML private Button buttonEstatistica;
    @FXML private Button buttonEmail;
    @FXML private ImageView image_pedido;
    private List<Pedido> pedidolist;

    @FXML
    void buttonBloquear(ActionEvent event) throws Exception {
        LoginFX login = new LoginFX();
        login.start(new Stage());
        fechaStage();
    }

    @FXML
    void buttonConfiguracao(ActionEvent event) throws Exception {
        ConfiguracaoFX config = new ConfiguracaoFX();
        config.start(new Stage());
        fechaStage();
    }

    @FXML
    void buttonEmail(ActionEvent event) throws Exception {
        PedidosFX pedidos = new PedidosFX();
        pedidos.start(new Stage());
        fechaStage();
    }

    @FXML
    void buttonEstatistica(ActionEvent event) throws Exception {
        EstatisticaFX estatistica = new EstatisticaFX();
        estatistica.start(new Stage());
        fechaStage();
    }

    @FXML
    void buttonFornecedor(ActionEvent event) throws Exception {
        FornecedorFX fornecedor = new FornecedorFX();
        fornecedor.start(new Stage());
        fechaStage();
    }

    @FXML
    void buttonProduto(ActionEvent event) throws Exception {
        ProdutoFX produto = new ProdutoFX();
        produto.start(new Stage());
        fechaStage();
    }

    @FXML
    void buttonSair(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void buttonUsuario(ActionEvent event) throws Exception {
        if (!Static.UsuarioLogado.UsuarioStatic.getUsuario().getPermissoes().isUsuario()){
            Static.UsuarioEdit.UsuarioStatic.setUsuario(Static.UsuarioLogado.UsuarioStatic.getUsuario());
            UsuarioNovoFX usuarionovo = new UsuarioNovoFX();
            usuarionovo.start(new Stage());
            fechaStage();
        } else {
            UsuarioFX usuario = new UsuarioFX();
            usuario.start(new Stage());
            fechaStage();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            verificaPermissoes();
        } catch (IOException ex) {
            invocaErro("Erro", "Um erro inesperado aconteceu");
        }
    }    
    
    public void fechaStage(){
        Stage stage = (Stage) buttonUsuario.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    public void verificaPermissoes() throws IOException{
        if(!Static.UsuarioLogado.UsuarioStatic.getUsuario().getPermissoes().isConfiguracao()){
            buttonConfiguracao.setDisable(true);
        }
        if(!Static.UsuarioLogado.UsuarioStatic.getUsuario().getPermissoes().isEmail()){
            buttonEmail.setDisable(true);
        } else {
            PedidosResource pedido = new PedidosResource();
            Gson json = new Gson();
            this.pedidolist = json.fromJson(pedido.findNaoEnviados(), new TypeToken<List<Pedido>>(){}.getType());
            if(pedidolist.size() != 0){
                image_pedido.setImage(new Image("/icon/pedidosAlert.png"));
            }
        }
        if(!Static.UsuarioLogado.UsuarioStatic.getUsuario().getPermissoes().isEstatistica()){
            buttonEstatistica.setDisable(true);
        }
        if(!Static.UsuarioLogado.UsuarioStatic.getUsuario().getPermissoes().isFornecedor()){
            buttonFornecedor.setDisable(true);
        }
        if(!Static.UsuarioLogado.UsuarioStatic.getUsuario().getPermissoes().isProduto()){
            buttonProduto.setDisable(true);
        }
    }
    
    public void invocaErro(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.showAndWait();
    }
    
}
