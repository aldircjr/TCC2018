/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.FornecedorResource;
import Main.FornecedorFX;
import Model.Formatter;
import Model.Fornecedor;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLFornecedorNovoController implements Initializable {

    @FXML private TextField textfield_cnpj;
    @FXML private TextField textfield_inscricao_estadual;
    @FXML private TextField textfield_razao_social;
    @FXML private TextField textfield_nome_fantasia;
    @FXML private TextField textfield_logradouro;
    @FXML private TextField textfield_bairro;
    @FXML private TextField textfield_cidade;
    @FXML private TextField textfield_cep;
    @FXML private TextField textfield_prazoDeEntrega;
    @FXML private ComboBox<String> combobox_uf;
    @FXML private TextField textfield_telefone;
    @FXML private TextField textfield_email;
    @FXML private Button button_salvar;
    @FXML private Button button_cancelar;

    @FXML
    void button_cancelar(ActionEvent event) throws Exception {
        Static.FornecedorEdit.FornecedorStatic.setNull();
        FornecedorFX fornecedor = new FornecedorFX();
        fornecedor.start(new Stage());
        fechaStage();
    }

    @FXML
    void button_salvar(ActionEvent event) throws IOException, Exception {
        if(verificaCamposVazios()){
            invocaErro("Campos Vazios", "O fornecedor não foi cadastrado! Existem campos vazios!");
        } else {
            Fornecedor fornecedor = new Fornecedor();
            
            
            fornecedor.setBairro(textfield_bairro.getText());
            fornecedor.setCep(textfield_cep.getText());
            fornecedor.setCidade(textfield_cidade.getText());
            fornecedor.setCnpj(textfield_cnpj.getText());
            fornecedor.setEmail(textfield_email.getText());
            fornecedor.setIe(textfield_inscricao_estadual.getText());
            fornecedor.setLogradouro(textfield_logradouro.getText());
            fornecedor.setNome(textfield_nome_fantasia.getText());
            fornecedor.setRazaoSocial(textfield_razao_social.getText());
            fornecedor.setTelefone(textfield_telefone.getText());
            fornecedor.setUf(combobox_uf.getValue());
            fornecedor.setPrazoDeEntrega(Integer.parseInt(textfield_prazoDeEntrega.getText()));
            
            FornecedorResource fornecedorResource = new FornecedorResource();
            
            
            if(Static.FornecedorEdit.FornecedorStatic.getFornecedor() != null){
                fornecedor.setId(Static.FornecedorEdit.FornecedorStatic.getFornecedor().getId());
                fornecedorResource.edit(new Gson().toJson(fornecedor));
                Static.FornecedorEdit.FornecedorStatic.setNull();
            } else {
                fornecedorResource.create(fornecedor, Fornecedor.class);
            }

            FornecedorFX forn = new FornecedorFX();
            forn.start(new Stage());
            fechaStage();
        }
    }

    @FXML
    void textfield_cnpj(KeyEvent event) {
        Formatter form = new Formatter();
        form.setMask("##.###.###/####-##");
        form.setCaracteresValidos("0123456789");
        form.setTf(textfield_cnpj);
        form.formatter();
    }

    @FXML
    void textfield_inscricao_estadual(KeyEvent event) {
        
    }

    @FXML
    void textfield_telefone(KeyEvent event) {
        Formatter form = new Formatter();
        form.setMask("(##)####-####");
        form.setCaracteresValidos("0123456789");
        form.setTf(textfield_telefone);
        form.formatter();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populaComboboxUF();
        verificaFornecedorEdit();
    }    
    
    public void populaComboboxUF(){
        combobox_uf.getItems().clear();
        combobox_uf.getItems().addAll("AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MG","MS",
                "MT","PA","PB","PE","PI","PR","RJ","RN","RO","RR","RS","SC","SE","SP","TO");
        combobox_uf.setPromptText("(Selecionar)");
    }
    
    private void fechaStage() {
        Stage stage = (Stage) button_cancelar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    public void invocaErro(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.show();
    }
    
    public boolean verificaCamposVazios(){
        
        
            
        if(textfield_bairro.getText().isEmpty() ||
                textfield_email.getText().isEmpty() ||
                textfield_inscricao_estadual.getText().isEmpty() ||
                textfield_logradouro.getText().isEmpty() ||
                textfield_nome_fantasia.getText().isEmpty() ||
                textfield_razao_social.getText().isEmpty() ||
                textfield_telefone.getText().isEmpty() ||
                textfield_cnpj.getText().isEmpty() ||
                textfield_cep.getText().isEmpty() ||
                textfield_prazoDeEntrega.getText().isEmpty()){
                
            return true;
        }
           
            
        if(textfield_prazoDeEntrega.getText().equals("0")) {    
            invocaErro("Prazo de Entrega", "Prazo de entrega deve ser maior que 0");
            return true;
        } 
        return false;
    }
    
    public void verificaFornecedorEdit(){
        if (Static.FornecedorEdit.FornecedorStatic.getFornecedor() != null){
            textfield_cnpj.setText(Static.FornecedorEdit.FornecedorStatic.getFornecedor().getCnpj());
            textfield_inscricao_estadual.setText(Static.FornecedorEdit.FornecedorStatic.getFornecedor().getIe());
            textfield_razao_social.setText(Static.FornecedorEdit.FornecedorStatic.getFornecedor().getRazaoSocial());
            textfield_nome_fantasia.setText(Static.FornecedorEdit.FornecedorStatic.getFornecedor().getNome());
            textfield_logradouro.setText(Static.FornecedorEdit.FornecedorStatic.getFornecedor().getLogradouro());
            textfield_bairro.setText(Static.FornecedorEdit.FornecedorStatic.getFornecedor().getBairro());
            textfield_cidade.setText(Static.FornecedorEdit.FornecedorStatic.getFornecedor().getCidade());
            textfield_cep.setText(Static.FornecedorEdit.FornecedorStatic.getFornecedor().getCep());
            textfield_telefone.setText(Static.FornecedorEdit.FornecedorStatic.getFornecedor().getTelefone());
            textfield_email.setText(Static.FornecedorEdit.FornecedorStatic.getFornecedor().getEmail());
            textfield_prazoDeEntrega.setText(""+Static.FornecedorEdit.FornecedorStatic.getFornecedor().getPrazoDeEntrega());
            combobox_uf.setValue(Static.FornecedorEdit.FornecedorStatic.getFornecedor().getUf());
            
        }
    }    
    
    
    
    public void invocaConfirmacao(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.showAndWait();
    }
    
}
