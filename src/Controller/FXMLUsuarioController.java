    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import ConsumerRest.UsuarioResource;
import Main.InicioFX;
import Main.UsuarioNovoFX;
import Model.Usuario;
import TableModel.TableModelUsuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.javafx.scene.control.skin.TableViewSkin;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLUsuarioController implements Initializable {

    
    @FXML private Button button_fechar;
    @FXML private TableView<TableModelUsuario> table_usuario;
    @FXML private TextField textfield_usuario;
    @FXML private Button button_novo;
    @FXML private Button button_editar;
    @FXML private Button button_bloquear;
    @FXML private Button button_pesquisar;
    @FXML private TableColumn<TableModelUsuario, String> column_nome;
    @FXML private TableColumn<TableModelUsuario, ImageView> column_status;
    private List<Usuario> listaUsuarios;
    private List<TableModelUsuario> listaUsuarioTable = new ArrayList<TableModelUsuario>();
    private TableModelUsuario usuarioTable;
    private Usuario usuario;

    @FXML
    void button_bloquar(ActionEvent event) throws IOException {
        if (table_usuario.getSelectionModel().getSelectedItem() != null){
            UsuarioResource usuarioResource = new UsuarioResource();
            Gson json = new Gson();
            this.usuarioTable = table_usuario.getSelectionModel().getSelectedItem();
            
            this.usuario = json.fromJson(usuarioResource.findByName(this.usuarioTable.getNome()), Usuario.class);
            
            this.usuario.setAtivo(!this.usuario.isAtivo());
            usuarioResource.edit(new Gson().toJson(this.usuario));
            buscaUsuariosAll();
            listUsuarioTOlistTable();
            populaTabelaUsuarios();
        } else { 
            invocaErro("Erro", "Selecione um usuario!");
        }
        
    }

    @FXML
    void button_editar(ActionEvent event) throws Exception {
        if (table_usuario.getSelectionModel().getSelectedItem() != null){
            UsuarioResource usuarioResource = new UsuarioResource();
            Gson json = new Gson();
            this.usuarioTable = table_usuario.getSelectionModel().getSelectedItem();
            
            this.usuario = json.fromJson(usuarioResource.findByName(this.usuarioTable.getNome()), Usuario.class);
            
            Static.UsuarioEdit.UsuarioStatic.setUsuario(this.usuario);
            UsuarioNovoFX usuarionovo = new UsuarioNovoFX();
            usuarionovo.start(new Stage());
            fechaStage();
        } else { 
            invocaErro("Erro", "Selecione um usuario!");
        }
    }

    @FXML
    void button_fechar(ActionEvent event) throws Exception {
        InicioFX inicio = new InicioFX();
        inicio.start(new Stage());
        fechaStage();
    }

    @FXML
    void button_novo(ActionEvent event) throws Exception {
        UsuarioNovoFX usuarioNovo = new UsuarioNovoFX();
        usuarioNovo.start(new Stage());
        fechaStage();
    }

    @FXML
    void button_pesquisar(ActionEvent event) throws IOException { //alterar para pesquisar por parte do nome!
        if(!textfield_usuario.getText().equals("")){
            UsuarioResource usuario = new UsuarioResource();
            Gson json = new Gson();
            this.listaUsuarios.clear();
            this.listaUsuarios = json.fromJson(usuario.findByPartName(textfield_usuario.getText()), new TypeToken<List<Usuario>>(){}.getType());
            if(this.listaUsuarios.size() != 0){
                listUsuarioTOlistTable();
                populaTabelaUsuarios();
            } else {
                invocaErro("Usuario inexistente", "Não existe usuario cadastrado com esse nome!");
                textfield_usuario.setText("");
            }
            
        } else {
            buscaUsuariosAll();
            listUsuarioTOlistTable();
            populaTabelaUsuarios();
        }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            buscaUsuariosAll();
            listUsuarioTOlistTable();
            populaTabelaUsuarios();
        } catch (IOException ex) {
            invocaErro("Erro", "Não foi possivel efetuar a operação!");
        }
        
    }    
    
    public void fechaStage(){
        Stage stage = (Stage) button_fechar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    public void buscaUsuariosAll() throws IOException{
        UsuarioResource usuario = new UsuarioResource();
        Gson json = new Gson();
        this.listaUsuarios = json.fromJson(usuario.findAll(), new TypeToken<List<Usuario>>(){}.getType());
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
    
    public void populaTabelaUsuarios() throws IOException{
        if (this.listaUsuarios.size() != 0){
        
            try {
                this.column_nome = new TableColumn<>("Nome");
                this.column_status = new TableColumn<>("Status");

                column_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                column_status.setCellValueFactory(new PropertyValueFactory<>("icon"));

                ObservableList<TableModelUsuario> listaDeUsuarios = FXCollections.observableList(this.listaUsuarioTable);

                column_status.setStyle( "-fx-alignment: CENTER;");  //para centralizar o icon
                
                table_usuario.getColumns().clear();
                table_usuario.getColumns().addAll(column_nome, column_status);
                table_usuario.getItems().setAll(listaDeUsuarios);
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        
            
        } else {
            invocaConfirmacao(":(", "Não há usuarios cadastrados");
        }
        
    }
    
    public void listUsuarioTOlistTable(){
        this.listaUsuarioTable.clear();
        for(int i = 0; i < this.listaUsuarios.size(); i++){
            TableModelUsuario usuariotable = new TableModelUsuario(this.listaUsuarios.get(i));
            this.listaUsuarioTable.add(usuariotable);
        }
    }
    
}
