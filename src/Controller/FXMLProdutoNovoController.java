/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.FornecedorResource;
import ConsumerRest.ProdutoResource;
import Main.ProdutoFX;
import Model.Fornecedor;
import Model.Produto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLProdutoNovoController implements Initializable {

    @FXML private TextField textfield_codigo_de_barras;
    @FXML private TextField textfield_nome;
    @FXML private ComboBox<String> combobox_unidade;
    @FXML private TextField textfield_dias_estoque;
    @FXML private Button button_cancelar;
    @FXML private Button button_salvar;
    @FXML private ComboBox<String> combobox_fornecedor;
    private List<Fornecedor> listaFornecedor;
    private Fornecedor fornecedor;

    @FXML
    void button_cancelar(ActionEvent event) throws Exception {
        Static.ProdutoEdit.ProdutoStatic.setNull();
        ProdutoFX produto = new ProdutoFX();
        produto.start(new Stage());
        fechaStage();
    }

    @FXML
    void button_salvar(ActionEvent event) throws IOException, Exception {
        buscaFornecedorByName();
       
        Produto produto = new Produto(textfield_codigo_de_barras.getText(), textfield_nome.getText(), combobox_unidade.getValue(), Integer.parseInt(textfield_dias_estoque.getText()), this.fornecedor);
        ProdutoResource produtoResource = new ProdutoResource();
        
        
        if (Static.ProdutoEdit.ProdutoStatic.getProduto() != null){
            produto.setId(Static.ProdutoEdit.ProdutoStatic.getProduto().getId());
            produto.setDiasEstoqueDisponivel(Static.ProdutoEdit.ProdutoStatic.getProduto().getDiasEstoqueDisponivel());
            produto.setCurvaABC(Static.ProdutoEdit.ProdutoStatic.getProduto().getCurvaABC());
            produto.setSaldo(Static.ProdutoEdit.ProdutoStatic.getProduto().getSaldo());
            produto.setMediaConsumo(Static.ProdutoEdit.ProdutoStatic.getProduto().getMediaConsumo());
            
            produtoResource.edit(new Gson().toJson(produto));
            Static.ProdutoEdit.ProdutoStatic.setNull();
        } else {
            System.out.println(new Gson().toJson(produto));
            produtoResource.create(new Gson().toJson(produto));
        }
        
        ProdutoFX produtoFX = new ProdutoFX();
        produtoFX.start(new Stage());
        fechaStage();
    
    }
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populaComboboxUnidade();
        try {
            populaComboboxFornecedores();
        } catch (IOException ex) {
            invocaErro("Erro", "Não foi possivel buscar todos os fornecedores");
        }
        iniciaProdutoEdit();
    }    
    
    public void populaComboboxUnidade(){
        combobox_unidade.getItems().clear();
        combobox_unidade.getItems().addAll("Unidade", "Kilograma");
        combobox_unidade.setPromptText("(Selecionar)");
    }
    
    public void populaComboboxFornecedores() throws IOException{
        buscaFornecedorAll();
        combobox_fornecedor.getItems().clear();
        for(int i = 0; i < this.listaFornecedor.size(); i++){
            combobox_fornecedor.getItems().add(this.listaFornecedor.get(i).getNome());
        }
        combobox_fornecedor.setPromptText("(Selecionar)");
        
    }
    
    private void fechaStage() {
        Stage stage = (Stage) button_cancelar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    public void iniciaProdutoEdit(){
    
        if(Static.ProdutoEdit.ProdutoStatic.getProduto() != null){
            textfield_codigo_de_barras.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getEan());
            textfield_nome.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getNome());
            combobox_unidade.setValue(Static.ProdutoEdit.ProdutoStatic.getProduto().getUnidade());
            textfield_dias_estoque.setText(""+Static.ProdutoEdit.ProdutoStatic.getProduto().getDiasEstoqueDesejavel());
            combobox_fornecedor.setValue(Static.ProdutoEdit.ProdutoStatic.getProduto().getFornecedor().getNome());
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
        alert.show();
    }
    
    public void invocaConfirmacao(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.show();
    }
    
    public void buscaFornecedorByName() throws IOException{
        
        FornecedorResource fornecedorResource = new FornecedorResource();
        Gson json = new Gson();
        this.fornecedor = json.fromJson(fornecedorResource.findByName(combobox_fornecedor.getValue()), Fornecedor.class);
        System.out.println(this.fornecedor.getCnpj());
    }
}
