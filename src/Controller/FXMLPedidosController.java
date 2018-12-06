/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.PedidosResource;
import Main.AjudaFX;
import Main.InicioFX;
import Main.PedidoEditarFX;
import Main.ProdutoSelectFX;
import Model.Pedido;
import TableModel.TableModelPedidos;
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
public class FXMLPedidosController implements Initializable {

    @FXML private TextField textfield_pesquisar;
    @FXML private Button button_pesquisar;
    @FXML private TableView<TableModelPedidos> table_geral;
    @FXML private TableColumn<TableModelPedidos, String> column_data;
    @FXML private TableColumn<TableModelPedidos, String> column_produto;
    @FXML private TableColumn<TableModelPedidos, String> column_quantidade;
    @FXML private TableColumn<TableModelPedidos, String> column_fornecedor;
    @FXML private TableColumn<TableModelPedidos, ImageView> column_recebimento;
    @FXML private Button button_receber;
    @FXML private Button button_voltar;
    @FXML private Button button_enviar;
    @FXML private Button button_ajuda;
    @FXML private Button button_novo;
    @FXML private Button button_editar;
    private List<Pedido> pedidolist;
    private List<TableModelPedidos> pedidolistTable = new ArrayList<TableModelPedidos>();
    private Pedido pedidoSelect;
    private TableModelPedidos pedidoTable;

    @FXML
    void button_pesquisar(ActionEvent event) throws IOException {
        if(!textfield_pesquisar.getText().equals("")){
            PedidosResource pedidoresource = new PedidosResource();
            Gson json = new Gson();
            this.pedidolist.clear();
            this.pedidolist = json.fromJson(pedidoresource.findByProduto(textfield_pesquisar.getText()), new TypeToken<List<Pedido>>(){}.getType());
            if(this.pedidolist.size() != 0){
                listPedidosTOlistTable();
                populaTabelaPedido();
            } else {
                invocaErro("Pedido inexistente", "Não existe pedido cadastrado para este produto!");
                textfield_pesquisar.setText("");
            }
            
        } else {
            buscaPedidosAll();
            listPedidosTOlistTable();
            populaTabelaPedido();
        }
    }
    
    
    @FXML
    void button_novo(ActionEvent event) throws Exception {
        ProdutoSelectFX produtoselect = new ProdutoSelectFX();
        produtoselect.start(new Stage());
        fechaStage();
    }
    
    @FXML
    void button_editar(ActionEvent event) throws IOException, Exception {
        if (table_geral.getSelectionModel().getSelectedItem() != null){
            if(table_geral.getSelectionModel().getSelectedItem().isEnviado()){
                invocaErro("Erro", "O pedido não pode ser editado pois já foi enviado ao Fornecedor!");
            } else if ( table_geral.getSelectionModel().getSelectedItem().isRecebido()){
                invocaErro("Erro", "O pedido não pode ser editado pois já foi recebido!");
            }else {
                PedidosResource pedidoResource = new PedidosResource();
                Gson json = new Gson();
                this.pedidoTable = table_geral.getSelectionModel().getSelectedItem();
                this.pedidoSelect = json.fromJson(pedidoResource.find(""+this.pedidoTable.getId()), Pedido.class);
                Static.PedidoEdit.ProdutoStatic.setPedido(this.pedidoSelect);
                PedidoEditarFX pedidoEditar = new PedidoEditarFX();
                pedidoEditar.start(new Stage());
                fechaStage();
            }
        } else { 
            invocaErro("Erro", "Selecione um pedido!");
        }
    }

    @FXML
    void button_receber(ActionEvent event) throws IOException {
        if (table_geral.getSelectionModel().getSelectedItem() != null){
            this.pedidoTable = table_geral.getSelectionModel().getSelectedItem();
            if (this.pedidoTable.isEnviado()){
                PedidosResource pedidoResource = new PedidosResource();
                Gson json = new Gson();
                this.pedidoTable = table_geral.getSelectionModel().getSelectedItem();

                this.pedidoSelect = json.fromJson(pedidoResource.find(""+this.pedidoTable.getId()), Pedido.class);

                this.pedidoSelect.setDataEnvio(null);
                this.pedidoSelect.setDataRecebido(null);
                this.pedidoSelect.getP().setDataCadastro(null);
                this.pedidoSelect.setUsuario(Static.UsuarioLogado.UsuarioStatic.getUsuario());
                pedidoResource.receberPedido(new Gson().toJson(this.pedidoSelect));
                
                invocaConfirmacao("Pedido recebido!", "");
                
                
                try {
                    buscaPedidosAll();
                    listPedidosTOlistTable();
                    populaTabelaPedido();
                } catch (IOException ex) {
                    invocaErro("Erro", "Não foi possivel consultar os pedidos!");
                }
                
            } else {
                invocaErro("Erro ao receber!", "O pedido ainda não foi enviado ao fornecedor para poder recebe-lo!");
            }
            
            
        } else { 
            invocaErro("Erro", "Selecione um pedido!");
        }
        
        
    }

    @FXML
    void button_ajuda(ActionEvent event) throws Exception {
        AjudaFX ajuda = new AjudaFX();
        ajuda.start(new Stage());
        fechaStage();
    }

    @FXML
    void button_enviar(ActionEvent event) throws IOException {
 
        if (table_geral.getSelectionModel().getSelectedItem() != null){
            this.pedidoTable = table_geral.getSelectionModel().getSelectedItem();
            if (!this.pedidoTable.isEnviado() && !this.pedidoTable.isRecebido()){
                PedidosResource pedidoResource = new PedidosResource();
                Gson json = new Gson();
                this.pedidoTable = table_geral.getSelectionModel().getSelectedItem();

                this.pedidoSelect = json.fromJson(pedidoResource.find(""+this.pedidoTable.getId()), Pedido.class);

                this.pedidoSelect.setDataEnvio(null);
                this.pedidoSelect.setDataRecebido(null);
                this.pedidoSelect.getP().setDataCadastro(null);
                pedidoResource.send(new Gson().toJson(this.pedidoSelect));
                
                try {
                    buscaPedidosAll();
                    listPedidosTOlistTable();
                    populaTabelaPedido();
                } catch (IOException ex) {
                    invocaErro("Erro", "Não foi possivel consultar os pedidos!");
                }
                
                
            } else if (this.pedidoTable.isEnviado() && !this.pedidoTable.isRecebido()){
                invocaConfirmacao("Erro ao enviar!", "O pedido já foi enviado ao fornecedor!");
            }
            
            
        } else { 
            invocaErro("Erro", "Selecione um pedido!");
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
            buscaPedidosAll();
            listPedidosTOlistTable();
            populaTabelaPedido();
        } catch (IOException ex) {
            invocaErro("Erro", "Não foi possivel consultar os pedidos!");
        }
    }    
    
    
    private void fechaStage() {
        Stage stage = (Stage) button_voltar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    public void buscaPedidosAll() throws IOException{
        PedidosResource pedido = new PedidosResource();
        Gson json = new Gson();
        this.pedidolist = json.fromJson(pedido.findAll(), new TypeToken<List<Pedido>>(){}.getType());
    }
    
    public void listPedidosTOlistTable(){
        this.pedidolistTable.clear();
        for(int i = 0; i < this.pedidolist.size(); i++){
            TableModelPedidos pedidosTable = new TableModelPedidos(this.pedidolist.get(i));
            this.pedidolistTable.add(pedidosTable);
        }
    }
    
    public void populaTabelaPedido() throws IOException{
        if (this.pedidolist.size() != 0){
        
            try {
                this.column_data = new TableColumn<>("Data de Envio");
                this.column_produto = new TableColumn<>("Produto");
                this.column_quantidade = new TableColumn<>("Quantidade");
                this.column_fornecedor = new TableColumn<>("Fornecedor");
                this.column_recebimento = new TableColumn<>("Status Pedido");

                column_data.setCellValueFactory(new PropertyValueFactory<>("date"));
                column_produto.setCellValueFactory(new PropertyValueFactory<>("produtoNome"));
                column_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
                column_fornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedorNome"));
                column_recebimento.setCellValueFactory(new PropertyValueFactory<>("icon"));

                ObservableList<TableModelPedidos> listaDeUsuarios = FXCollections.observableList(this.pedidolistTable);

                column_recebimento.setStyle( "-fx-alignment: CENTER;");  //para centralizar o icon
                
                table_geral.getColumns().clear();
                table_geral.getColumns().addAll(column_data, column_produto, column_quantidade, column_fornecedor, column_recebimento);
                table_geral.getItems().setAll(listaDeUsuarios);
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        } else {
            invocaConfirmacao(":(", "Não há pedidos cadastrados");
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
