/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import ConsumerRest.UsuarioResource;
import Main.ConexaoServidorFX;
import Main.InicioFX;
import Model.ConfiguracaoServidor;
import Model.Permissoes;
import Model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import Model.Usuario;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;



/**
 *
 * @author aldir
 */
public class FXMLLoginController implements Initializable {
    
//    private Usuario usuario;
    @FXML private TextField textfield_usuario;
    @FXML private PasswordField textfield_password;
    @FXML private Button button_entrar;
    @FXML private Button button_Sair;
    private List<Usuario> listaUsuarios;
    private Usuario usuario;

    @FXML 
     void button_entrar(ActionEvent event) throws SQLException, IOException, Exception {
        iniciaLogin();
    }
    
    @FXML
    void button_Sair(ActionEvent event) {
        fecharSistema();
    }
    
    @FXML 
    void pressionouEnter(KeyEvent evt) throws SQLException, IOException, Exception {
        if (evt.getCode().equals(KeyCode.ENTER)){
            iniciaLogin();
        } else if (evt.getCode().equals(KeyCode.ESCAPE)){
            fecharSistema();
        }
        
    }
    
    public void iniciaLogin() throws SQLException, IOException, Exception{
        try {
            verificaConexaoServidor();
            
            if (autenticaUsuario()){
                Static.UsuarioLogado.UsuarioStatic.setUsuario(usuario);
                InicioFX inicio = new InicioFX();
                inicio.start(new Stage());
                fechaStage();
            } else {
                invocaErro("Erro", "Credenciais incorretas!!");
            }
            
        } catch (Exception e){
            try {
                ConexaoServidorFX conexao = new ConexaoServidorFX();
                conexao.start(new Stage());
                fechaStage();
            } catch (Exception ex) {
                invocaErro("Erro inesperado", "Um erro inesperado aconteceu!");
            }
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        if(ev1("teste", "teste conf.")){
//            System.out.println("button OK");
//        } else {
//            System.out.println("button Cancel");
//        }
    }    
    
    public void fechaStage(){
        Stage stage = (Stage) button_Sair.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    public void fecharSistema(){
        System.exit(0);
    }
    
    
    public void invocaErro(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.show();
    }
    
    public void verificaConexaoServidor() throws IOException{
        UsuarioResource usuario = new UsuarioResource();
        Gson json = new Gson();
//        Permissoes permissoes = new Permissoes(true, true, true, true, true, true);
//        Usuario usuarioadmin = new Usuario("admin", "admin", true, permissoes);
//        usuario.create(json.toJson(usuarioadmin));
        this.listaUsuarios = json.fromJson(usuario.findAll(), new TypeToken<List<Usuario>>(){}.getType());
    }
    
    
    public boolean autenticaUsuario() throws IOException{
        UsuarioResource usuario = new UsuarioResource();
        Gson json = new Gson();
        
        this.usuario = json.fromJson(usuario.findByName(textfield_usuario.getText()), Usuario.class); //verificar para buscar pelo nome
//        System.out.println("usuario: " + this.usuario.getNome());
//        System.out.println("senha: " + this.usuario.getSenha());
//        System.out.println("ativo: " + this.usuario.isAtivo());
        if (this.usuario.getNome().equals(textfield_usuario.getText()) && 
            this.usuario.getSenha().equals(textfield_password.getText())){
            if(this.usuario.isAtivo()){
                return true;
            }
        }
        return false;
    }
    
    
//    public boolean ev1(String titulo, String descricao){
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setHeaderText(titulo);
//        alert.setContentText(descricao);
//        alert.showAndWait();
//        ButtonType b = alert.getResult();
//        if(b == ButtonType.OK){
//            return true;
//        } 
//        return false;
//    }
    
    
}
