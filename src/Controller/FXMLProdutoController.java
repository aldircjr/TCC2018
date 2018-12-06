/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.ProdutoResource;
import Main.EntradaSaidaFX;
import Main.HistoricoFX;
import Main.InicioFX;
import Main.ProdutoNovoFX;
import Model.Fornecedor;
import Model.Produto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLProdutoController implements Initializable {

    @FXML private TextField textfield_pesquisa;
    @FXML private TableView<Produto> table_geral;
    @FXML private TableColumn<Produto, String> column_ean;
    @FXML private TableColumn<Produto, String> column_nome;
    @FXML private TableColumn<Produto, String> column_saldo;
    @FXML private Button button_novo;
    @FXML private Button button_editar;
    @FXML private Button button_bloquear;
    @FXML private Button button_entrada;
    @FXML private Button button_historico;
    @FXML private Button button_voltar;
    @FXML private Button button_pesquisa;
    private List<Produto> produtoList;
    private Produto produto;


    @FXML
    void button_editar(ActionEvent event) throws Exception {
        if (table_geral.getSelectionModel().getSelectedItem() != null){
            Static.ProdutoEdit.ProdutoStatic.setProduto(table_geral.getSelectionModel().getSelectedItem());
            ProdutoNovoFX produtonovo = new ProdutoNovoFX();
            produtonovo.start(new Stage());
            fechaStage();
        } else { 
            invocaErro("Erro", "Selecione um usuario!");
        }
    }

    @FXML
    void button_entrada(ActionEvent event) throws Exception {

        if (table_geral.getSelectionModel().getSelectedItem() != null){
            this.produto = table_geral.getSelectionModel().getSelectedItem();
            Static.ProdutoEdit.ProdutoStatic.setProduto(this.produto);
            EntradaSaidaFX entradaSaida = new EntradaSaidaFX();
            entradaSaida.start(new Stage());
            fechaStage();
        } else { 
            invocaErro("Erro", "Selecione um produto!");
        }
    }
    

    @FXML
    void button_historico(ActionEvent event) throws Exception {
        if (table_geral.getSelectionModel().getSelectedItem() != null){
            this.produto = table_geral.getSelectionModel().getSelectedItem();
            Static.ProdutoEdit.ProdutoStatic.setProduto(this.produto);
            
            HistoricoFX historico = new HistoricoFX();
            historico.start(new Stage());
            fechaStage();
        } else { 
            invocaErro("Erro", "Selecione um produto!");
        }
        
        
        
        
    }

    @FXML
    void button_novo(ActionEvent event) throws Exception {
        ProdutoNovoFX produtonovo = new ProdutoNovoFX();
        produtonovo.start(new Stage());
        fechaStage();
    }

    @FXML
    void button_pesquisa(ActionEvent event) throws IOException {

        if(!textfield_pesquisa.getText().equals("")){
            ProdutoResource produtoResource = new ProdutoResource();
            Gson json = new Gson();
            this.produtoList.clear();
            this.produtoList = json.fromJson(produtoResource.findByPartName(textfield_pesquisa.getText()), new TypeToken<List<Produto>>(){}.getType());
            if(this.produtoList.size() != 0){
                populaTabel();
            } else {
                invocaErro("Produto inexistente", "Não existe produto cadastrado com esse nome!");
                textfield_pesquisa.setText("");
            }
            
        } else {
            buscaProdutosAll();
            populaTabel();
        }
    }

    

    @FXML
    void button_voltar(ActionEvent event) throws Exception {
        Static.ProdutoEdit.ProdutoStatic.setNull();
        InicioFX inicio = new InicioFX();
        inicio.start(new Stage());
        fechaStage();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            buscaProdutosAll();
            populaTabel();
        } catch (IOException ex) {
            invocaErro("Erro", "Não foi possivel efetuar a operação!");
        }
       
    }    
    
    private void fechaStage() {
        Stage stage = (Stage) button_voltar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    public void buscaProdutosAll() throws IOException{
        ProdutoResource produtoResource = new ProdutoResource();
        Gson json = new Gson();
        this.produtoList = json.fromJson(produtoResource.findAll(), new TypeToken<List<Produto>>(){}.getType());
        
    }
    
    public void populaTabel(){
    
         if (this.produtoList.size() != 0){
        
            try {
                this.column_ean = new TableColumn<>("Codigo de Barras");
                this.column_nome = new TableColumn<>("Nome");
                this.column_saldo = new TableColumn<>("Saldo");
                
                this.column_ean.setCellValueFactory(new PropertyValueFactory<>("ean"));
                this.column_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                this.column_saldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));

                ObservableList<Produto> listaDeProdutos = FXCollections.observableList(this.produtoList);

                
                table_geral.getColumns().clear();
                table_geral.getColumns().addAll(column_ean, column_nome, column_saldo);
                table_geral.getItems().setAll(listaDeProdutos);
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        } else {
            invocaConfirmacao(":(", "Não há produtos cadastrados");
        }
        
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
