/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.FornecedorResource;
import Main.FornecedorNovoFX;
import Main.InicioFX;
import Model.Fornecedor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLFornecedorController implements Initializable {

    @FXML private Button button_pesquisar;
    @FXML private TextField textfield_pesquisa;
    @FXML private TableView<Fornecedor> table_fornecedor;
    @FXML private TableColumn<Fornecedor, String> column_cnpj;
    @FXML private TableColumn<Fornecedor, String> column_nome;
    @FXML private TableColumn<Fornecedor, String> column_telefone;
    @FXML private Button button_novo;
    @FXML private Button button_editar;
    @FXML private Button button_voltar;
    private List<Fornecedor> listaFornecedor;
    private Fornecedor fornecedor;

    @FXML
    void button_editar(ActionEvent event) throws Exception {
        if (table_fornecedor.getSelectionModel().getSelectedItem() != null){
            Static.FornecedorEdit.FornecedorStatic.setFornecedor(table_fornecedor.getSelectionModel().getSelectedItem());
            FornecedorNovoFX forn = new FornecedorNovoFX();
            forn.start(new Stage());
            fechaStage();
        } else { 
            invocaErro("Erro", "Selecione um fornecedor!");
        }
        
    }

    @FXML
    void button_novo(ActionEvent event) throws Exception {
        FornecedorNovoFX forn = new FornecedorNovoFX();
        forn.start(new Stage());
        fechaStage();
    }



    @FXML
    void button_pesquisar(ActionEvent event) throws IOException {
        if(!textfield_pesquisa.getText().equals("")){
            FornecedorResource fornResource = new FornecedorResource();
            Gson json = new Gson();
            this.listaFornecedor.clear();
            this.listaFornecedor = json.fromJson(fornResource.findByPartName(textfield_pesquisa.getText()), new TypeToken<List<Fornecedor>>(){}.getType());
            if (this.listaFornecedor.size() != 0){
                populaTabel();
            } else {
                invocaErro("Fornecedor inexistente", "Não existe fornecedor cadastrado com esse nome!");
            }
            
        } else {
            buscaFornecedorAll();
            populaTabel();
        }
    }

    @FXML
    void button_voltar(ActionEvent event) throws Exception {
        InicioFX inicio = new InicioFX();
        inicio.start(new Stage());
        fechaStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            buscaFornecedorAll();
            populaTabel();
        } catch (IOException ex) {
            invocaErro("Erro", "Um erro inesperado aconteceu!");
        }
        
    }   
    
    public void fechaStage(){
        Stage stage = (Stage) button_voltar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    
    
    
    public void populaTabel(){
        if(this.listaFornecedor.size() != 0){
        
            try {
                this.column_cnpj = new TableColumn<>("CNPJ");
                this.column_nome = new TableColumn<>("Nome");
                this.column_telefone = new TableColumn<>("Telefone");

                this.column_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
                this.column_nome.setCellValueFactory(new PropertyValueFactory<>("razaoSocial"));
                this.column_telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

                ObservableList<Fornecedor> listaDeFornecedores = FXCollections.observableList(this.listaFornecedor);

                table_fornecedor.getColumns().clear();
                table_fornecedor.getColumns().addAll(this.column_cnpj, this.column_nome, this.column_telefone);
                table_fornecedor.getItems().setAll(listaDeFornecedores);

            } catch (NullPointerException e) {
                System.out.println(e);
            }
            
        } else {
           invocaConfirmacao(":(", "Não há fornecedores cadastrados");
        
        }
       
    }
    
    public void buscaFornecedorAll() throws IOException{
        FornecedorResource fornecedorResource = new FornecedorResource();
        Gson json = new Gson();
        this.listaFornecedor = json.fromJson(fornecedorResource.findAll(), new TypeToken<List<Fornecedor>>(){}.getType());
        System.out.println(listaFornecedor.size());
    }
    
    
    public void invocaErro(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.showAndWait();
    }
    
    public void invocaConfirmacao(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.showAndWait();
    }
    
}
