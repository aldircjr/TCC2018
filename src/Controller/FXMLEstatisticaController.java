/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.ProdutoResource;
import Main.EstatisticaDetailFX;
import Main.InicioFX;
import Model.Produto;
import TableModel.TableModelEstatistica;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.ProgressBar;
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
public class FXMLEstatisticaController implements Initializable {
    
    @FXML private TextField textfield_pesquisar;
    @FXML private Button button_pesquisar;
    @FXML private TableView<TableModelEstatistica> table_geral;
    @FXML private TableColumn<TableModelEstatistica, String> column_produto;
    @FXML private TableColumn<TableModelEstatistica, String> column_saldo_estoque;
    @FXML private TableColumn<TableModelEstatistica, ImageView> column_curvaABC;
    @FXML private TableColumn<TableModelEstatistica, ProgressBar> column_nivel_de_estoque;
    @FXML private Button button_detalhar;
    @FXML private Button button_voltar;
    private List<Produto> produtoList;
    private Produto produto;
    private List<TableModelEstatistica> produtoListTable = new ArrayList<TableModelEstatistica>();

    @FXML
    void button_detalhar(ActionEvent event) throws IOException, Exception {
        if (table_geral.getSelectionModel().getSelectedItem() != null){
            ProdutoResource produtoResource = new ProdutoResource();
            Gson json = new Gson();
            this.produto = json.fromJson(produtoResource.findByName(table_geral.getSelectionModel().getSelectedItem().getNome()), Produto.class);
            Static.ProdutoEdit.ProdutoStatic.setProduto(this.produto);
            
            EstatisticaDetailFX estatistica = new EstatisticaDetailFX();
            estatistica.start(new Stage());
            fechaStage();
            
            System.out.println("iniciou tela de detalhe :D");
            
        } else { 
            invocaErro("Erro", "Selecione um produto!");
        }
    }

    @FXML
    void button_pesquisar(ActionEvent event) throws IOException {

        
        if(!textfield_pesquisar.getText().equals("")){
            ProdutoResource produtoResource = new ProdutoResource();
            Gson json = new Gson();
            this.produtoList.clear();
            this.produtoList = json.fromJson(produtoResource.findByPartName(textfield_pesquisar.getText()), new TypeToken<List<Produto>>(){}.getType());
            
            
            if(this.produtoList.size() != 0){
                listProdutoToListTable();
                populaTabela();
            } else {
                invocaErro("Produto inexistente", "Não existe produto cadastrado com esse nome!");
                textfield_pesquisar.setText("");
            }
            
        } else {
            buscaProdutosAll();
            listProdutoToListTable();
            populaTabela();
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
            buscaProdutosAll();
            listProdutoToListTable();
            populaTabela();
        } catch (IOException ex) {
            invocaErro("Erro", "Um erro inesperado aconteceu!");
        }
    }    

    
    public void fechaStage(){
        Stage stage = (Stage) button_voltar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    public void buscaProdutosAll() throws IOException{
        ProdutoResource produtoResource = new ProdutoResource();
        Gson json = new Gson();
        this.produtoList = json.fromJson(produtoResource.findAllABC(), new TypeToken<List<Produto>>(){}.getType());
        
    }
    
    public void listProdutoToListTable(){
        this.produtoListTable.clear();
        for(int i = 0; i < this.produtoList.size(); i++){
            TableModelEstatistica prodTable = new TableModelEstatistica(this.produtoList.get(i));
            this.produtoListTable.add(prodTable);
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

    public void populaTabela() throws IOException{
        
        if (this.produtoList.size() != 0){
        
            try {
                this.column_produto = new TableColumn<>("Nome");
                this.column_curvaABC = new TableColumn<>("Curva ABC");
                this.column_saldo_estoque = new TableColumn<>("Estoque Disponivel");
                this.column_nivel_de_estoque = new TableColumn<>("Nível de Estoque");

                column_produto.setCellValueFactory(new PropertyValueFactory<>("nome"));
                column_curvaABC.setCellValueFactory(new PropertyValueFactory<>("icon"));
                column_saldo_estoque.setCellValueFactory(new PropertyValueFactory<>("saldo"));
                column_nivel_de_estoque.setCellValueFactory(new PropertyValueFactory<>("nivelDeEstoque"));

                ObservableList<TableModelEstatistica> listaDeUsuarios = FXCollections.observableList(this.produtoListTable);

                column_curvaABC.setStyle( "-fx-alignment: CENTER;");  //para centralizar o icon
                column_nivel_de_estoque.setMinWidth(150);
                column_nivel_de_estoque.setMaxWidth(150);
                
                column_curvaABC.setMaxWidth(75);
                column_curvaABC.setMinWidth(75);
                
                column_saldo_estoque.setMaxWidth(125);
                column_saldo_estoque.setMinWidth(125);
                
                table_geral.getColumns().clear();
                table_geral.getColumns().addAll(column_produto, column_saldo_estoque, column_nivel_de_estoque, column_curvaABC);
                table_geral.getItems().setAll(listaDeUsuarios);
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        
            
        } else {
            invocaConfirmacao(":(", "Não há produtos cadastrados");
        }
        
    }
    
}
