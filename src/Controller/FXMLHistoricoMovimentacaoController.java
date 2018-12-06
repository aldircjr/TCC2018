/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.MovimentacaoResource;
import Main.ProdutoFX;
import Model.Movimentacao;
import TableModel.TableModelMovimentacao;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
public class FXMLHistoricoMovimentacaoController implements Initializable {

    @FXML private TableView<TableModelMovimentacao> table_historico;
    @FXML private TableColumn<TableModelMovimentacao, String> colunm_data;
    @FXML private TableColumn<TableModelMovimentacao, String> colunm_usuario;
    @FXML private TableColumn<TableModelMovimentacao, String> colunm_movimentacao;
    @FXML private Label label_produto;
    @FXML private Button button_fechar;
    private List<Movimentacao> listaMovimentacao;
    private List<TableModelMovimentacao> listaMovimentacaoTable = new ArrayList<TableModelMovimentacao>();

    @FXML
    void button_fechar(ActionEvent event) throws Exception {
        Static.ProdutoEdit.ProdutoStatic.setNull();
        ProdutoFX produtoFX = new ProdutoFX();
        produtoFX.start(new Stage());
        fechaStage();
    }
    
    
     public void fechaStage(){
        Stage stage = (Stage) button_fechar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            label_produto.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getNome());
            buscaAll();
            listUsuarioTOlistTable();
            populaTabela();
        } catch (IOException ex) {
            invocaErro("Erro", "Um erro inesperado aconteceu!");
        }
        
    }    
    
    public void buscaAll() throws IOException{ //alterar para pesquisar pelo produto que está no static
        MovimentacaoResource movimentacaoResource = new MovimentacaoResource();
        Gson json = new Gson();
        this.listaMovimentacao = json.fromJson(movimentacaoResource.findByFornecedor(Static.ProdutoEdit.ProdutoStatic.getProduto().getNome()), new TypeToken<List<Movimentacao>>(){}.getType());
    }
    
    public void populaTabela(){
    
    if (this.listaMovimentacao.size() != 0){
        
            try {
                this.colunm_data = new TableColumn<>("Data");
                this.colunm_usuario = new TableColumn<>("Usuario");
                this.colunm_movimentacao = new TableColumn<>("Movimentação");
                
                this.colunm_data.setCellValueFactory(new PropertyValueFactory<>("data"));
                this.colunm_usuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
                this.colunm_movimentacao.setCellValueFactory(new PropertyValueFactory<>("movimentacao"));

                ObservableList<TableModelMovimentacao> listaDeProdutos = FXCollections.observableList(this.listaMovimentacaoTable);

                table_historico.getColumns().clear();
                table_historico.getColumns().addAll(colunm_data, colunm_usuario, colunm_movimentacao);
                table_historico.getItems().setAll(listaDeProdutos);
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        } else {
//            invocaConfirmacao(":(", "Não há produtos cadastrados");
        }
    
    }
    
    
    public void listUsuarioTOlistTable(){
        this.listaMovimentacaoTable.clear();
        for(int i = 0; i < this.listaMovimentacao.size(); i++){
            TableModelMovimentacao movimentacaoTable = new TableModelMovimentacao(this.listaMovimentacao.get(i));
            this.listaMovimentacaoTable.add(movimentacaoTable);
        }
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
    
}
