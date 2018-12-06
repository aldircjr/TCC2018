/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.UsuarioResource;
import Main.InicioFX;
import Main.UsuarioFX;
import Model.Permissoes;
import Model.Usuario;
import com.google.gson.Gson;
import java.io.IOException;
//import Model.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLUsuarioNovoController implements Initializable {

    @FXML private TextField textfield_nome;
    @FXML private PasswordField passwordfield_senha;
    @FXML private PasswordField passwordfield_senha_confirmacao;
    @FXML private ImageView image_senha;
    @FXML private ImageView image_senha_confirmacao;
    @FXML private CheckBox checkbox_produto;
    @FXML private CheckBox checkbox_fornecedor;
    @FXML private CheckBox checkbox_usuario;
    @FXML private CheckBox checkbox_configuracao;
    @FXML private CheckBox checkbox_estatistica;
    @FXML private CheckBox checkbox_email;
    @FXML private Button button_salvar;
    @FXML private Button button_cancelar;
    @FXML private CheckBox checkbox_marcarTodos;


    @FXML
    void button_cancelar(ActionEvent event) throws Exception {
        Static.UsuarioEdit.UsuarioStatic.setNull();
        if(Static.UsuarioLogado.UsuarioStatic.getUsuario().getPermissoes().isUsuario()){
            UsuarioFX usuario = new UsuarioFX();
            usuario.start(new Stage());
            fechaStage();
        } else {
            InicioFX inicio = new InicioFX();
            inicio.start(new Stage());
            fechaStage();
        }
        
    }

    @FXML
    void button_salvar(ActionEvent event) throws IOException, Exception {
        if (passwordfield_senha.getText().equals(passwordfield_senha_confirmacao.getText())){
            Permissoes perm = new Permissoes(checkbox_email.isSelected(), checkbox_usuario.isSelected(), checkbox_produto.isSelected(), checkbox_fornecedor.isSelected(), checkbox_configuracao.isSelected(), checkbox_estatistica.isSelected());
            Usuario usuario = new Usuario(textfield_nome.getText(), passwordfield_senha.getText(), true, perm);
            
            
            UsuarioResource usuarioResurce = new UsuarioResource();
            if (Static.UsuarioEdit.UsuarioStatic.getUsuario() != null){
                usuario.setID(Static.UsuarioEdit.UsuarioStatic.getUsuario().getID());
                
                usuarioResurce.edit(new Gson().toJson(usuario));
                Static.UsuarioEdit.UsuarioStatic.setNull();
            } else {
                usuarioResurce.create(new Gson().toJson(usuario));
            }
            Static.UsuarioLogado.UsuarioStatic.atualizaUsuario();
            
            UsuarioFX usuariofx = new UsuarioFX();
            usuariofx.start(new Stage());
            fechaStage();
        } else {
            invocaErro("Erro", "As senhas não conferem!");
        }
        
       
    }
    
    @FXML
    void checkbox_marcarTodos(ActionEvent event) {
        if(checkbox_marcarTodos.isSelected()){
            checkbox_configuracao.setSelected(true);
            checkbox_email.setSelected(true);
            checkbox_estatistica.setSelected(true);
            checkbox_fornecedor.setSelected(true);
            checkbox_produto.setSelected(true);
            checkbox_usuario.setSelected(true);
        } else {
            checkbox_configuracao.setSelected(false);
            checkbox_email.setSelected(false);
            checkbox_estatistica.setSelected(false);
            checkbox_fornecedor.setSelected(false);
            checkbox_produto.setSelected(false);
            checkbox_usuario.setSelected(false);
        }
    }
    
    @FXML
    void keyreleased_senha(KeyEvent event) {
        if (passwordfield_senha.getText().length() > 5){
            image_senha.setImage(new Image("/Icon/confirmar.png"));
        } else {
            image_senha.setImage(new Image("/Icon/cancelar.png"));
        }
    }

    @FXML
    void keyreleased_senha_confirmacao(KeyEvent event) {
        if(passwordfield_senha_confirmacao.getText().length() > 5){
            if(passwordfield_senha.getText().equals(passwordfield_senha_confirmacao.getText())){
                image_senha_confirmacao.setImage(new Image("/Icon/confirmar.png"));
            } else {
                image_senha_confirmacao.setImage(new Image("/Icon/cancelar.png"));
            }
        } else {
            image_senha_confirmacao.setImage(new Image("/Icon/cancelar.png"));
        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        passwordfield_senha_confirmacao.setPromptText("Confirme a senha");
        iniciaUsuarioEdit();
        verificaSePodeAlterarPermissoes();
        verificaSePodeAlterarNomeSenhaUsuario();
        
        
        
    }    

     public void fechaStage(){
        Stage stage = (Stage) button_cancelar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    public void invocaErro(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.show();
    }
    
    public void invocaConfirmacao(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.show();
    }
    
    public void iniciaUsuarioEdit(){
    //se houver edit
        if(Static.UsuarioEdit.UsuarioStatic.getUsuario() != null){
            textfield_nome.setText(Static.UsuarioEdit.UsuarioStatic.getUsuario().getNome());
            passwordfield_senha.setText(Static.UsuarioEdit.UsuarioStatic.getUsuario().getSenha());
            passwordfield_senha_confirmacao.setText(Static.UsuarioEdit.UsuarioStatic.getUsuario().getSenha());
            checkbox_configuracao.setSelected(Static.UsuarioEdit.UsuarioStatic.getUsuario().getPermissoes().isConfiguracao());
            checkbox_email.setSelected(Static.UsuarioEdit.UsuarioStatic.getUsuario().getPermissoes().isEmail());
            checkbox_estatistica.setSelected(Static.UsuarioEdit.UsuarioStatic.getUsuario().getPermissoes().isEstatistica());
            checkbox_fornecedor.setSelected(Static.UsuarioEdit.UsuarioStatic.getUsuario().getPermissoes().isFornecedor());
            checkbox_produto.setSelected(Static.UsuarioEdit.UsuarioStatic.getUsuario().getPermissoes().isProduto());
            checkbox_usuario.setSelected(Static.UsuarioEdit.UsuarioStatic.getUsuario().getPermissoes().isUsuario());
        }
    }
    
    public void verificaSePodeAlterarPermissoes(){
       
        if (!Static.UsuarioLogado.UsuarioStatic.getUsuario().getPermissoes().isUsuario()){
            checkbox_configuracao.setDisable(true);
            checkbox_email.setDisable(true);
            checkbox_estatistica.setDisable(true);
            checkbox_fornecedor.setDisable(true);
            checkbox_marcarTodos.setDisable(true);
            checkbox_produto.setDisable(true);
            checkbox_usuario.setDisable(true);
        }
    }
    
    public void verificaSePodeAlterarNomeSenhaUsuario(){
        if(Static.UsuarioEdit.UsuarioStatic.getUsuario() != null){
            if (!Static.UsuarioLogado.UsuarioStatic.getUsuario().getNome().equals(Static.UsuarioEdit.UsuarioStatic.getUsuario().getNome())){
                textfield_nome.setDisable(true);
                passwordfield_senha.setDisable(true);
                passwordfield_senha_confirmacao.setDisable(true);
            }
        }
    }
}
