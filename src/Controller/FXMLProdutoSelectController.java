/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.ProdutoResource;
import Main.PedidoNovoFX;
import Main.PedidosFX;
import Model.Produto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLProdutoSelectController implements Initializable {

    @FXML private TextField textfield_pesquisar;
    @FXML private Button button_pesquisar;
    @FXML private TableView<Produto> tableview;
    @FXML private TableColumn<Produto, String> column_codigoBarras;
    @FXML private TableColumn<Produto, String> column_produto;
    @FXML private Button button_selecionar;
    @FXML private Button button_cancelar;
    private List<Produto> produtoList;
    private Produto produto;

    @FXML
    void button_cancelar(ActionEvent event) throws Exception {
        PedidosFX pedidos = new PedidosFX();
        pedidos.start(new Stage());
        fechaStage();
    }

    @FXML
    void button_pesquisar(ActionEvent event) throws IOException {
        if(!textfield_pesquisar.getText().equals("")){
            ProdutoResource produtoResource = new ProdutoResource();
            Gson json = new Gson();
            this.produtoList.clear();
            this.produtoList = json.fromJson(produtoResource.findByPartName(textfield_pesquisar.getText()), new TypeToken<List<Produto>>(){}.getType());
            if(this.produtoList.size() != 0){
                populaTabel();
            } else {
                invocaErro("Produto inexistente", "Não existe produto cadastrado com esse nome!");
                textfield_pesquisar.setText("");
            }
            
        } else {
            buscaProdutosAll();
            populaTabel();
        }
    }

    @FXML
    void button_selecionar(ActionEvent event) throws Exception {
        if (tableview.getSelectionModel().getSelectedItem() != null){
            this.produto = tableview.getSelectionModel().getSelectedItem();
            Static.ProdutoEdit.ProdutoStatic.setProduto(this.produto);
            
            PedidoNovoFX pedidonovo = new PedidoNovoFX();
            pedidonovo.start(new Stage());
            fechaStage();
        } else { 
            invocaErro("Erro", "Selecione um produto!");
        }
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
    
    
    public void invocaErro(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.showAndWait();
    }
    
    private void fechaStage() {
        Stage stage = (Stage) button_cancelar.getScene().getWindow(); //Obtendo a janela atual
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
                this.column_codigoBarras = new TableColumn<>("Codigo de Barras");
                this.column_produto = new TableColumn<>("Nome");
                
                this.column_codigoBarras.setCellValueFactory(new PropertyValueFactory<>("ean"));
                this.column_produto.setCellValueFactory(new PropertyValueFactory<>("nome"));

                ObservableList<Produto> listaDeProdutos = FXCollections.observableList(this.produtoList);

                tableview.getColumns().clear();
                tableview.getColumns().addAll(column_codigoBarras, column_produto);
                tableview.getItems().setAll(listaDeProdutos);
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        } else {
            invocaConfirmacao(":(", "Não há produtos cadastrados");
        }
        
    }
    
    public void invocaConfirmacao(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.showAndWait();
    }
    
}
