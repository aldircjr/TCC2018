/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.MovimentacaoResource;
import Main.ProdutoFX;
import Model.Formatter;
import Model.Movimentacao;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLEntradaSaidaController implements Initializable {

    @FXML private ComboBox<String> combobox_tipo_de_movimentação;
    @FXML private Button button_salvar;
    @FXML private Button button_cancelar;
    @FXML private TextField textfield_quantidade;
    @FXML private Label label_sado;
    @FXML private Label label_produto;
    @FXML private TextField textfield_valor;
    @FXML private Label label_valor;

    @FXML
    void button_cancelar(ActionEvent event) throws Exception {
        Static.ProdutoEdit.ProdutoStatic.setNull();
        ProdutoFX produto = new ProdutoFX();
        produto.start(new Stage());
        fechaStage();
    }

    @FXML
    void button_salvar(ActionEvent event) throws IOException, Exception {
        MovimentacaoResource movimentacaoResource = new MovimentacaoResource();
        int tipo = 2;
        
            try{
                    if (!textfield_quantidade.equals("")){
                        tipo = 0;
                        Movimentacao movimentacao = new Movimentacao(Integer.parseInt(textfield_quantidade.getText()), tipo, Static.ProdutoEdit.ProdutoStatic.getProduto(), Static.UsuarioLogado.UsuarioStatic.getUsuario(), (float) 0.0);
                        movimentacao.getProduto().setDataCadastro(null);
                        movimentacaoResource.create(new Gson().toJson(movimentacao));
                        Static.ProdutoEdit.ProdutoStatic.setNull();
                    } else {
                        invocaErro("Erro", "Preencha todos os campos!");
                    }
                ProdutoFX produto = new ProdutoFX();
                produto.start(new Stage());
                fechaStage();
            }catch(NullPointerException e){
                invocaErro("Erro", "Selecione o tipo de movimentação!");
            }catch(RuntimeException ex){
                invocaErro("Erro", "Preencha todos os campos!");
            }
       
    }
    
    @FXML
    void combobox_onAction(ActionEvent event) {
//        if(combobox_tipo_de_movimentação.getValue().equals("Entrada")){
//            textfield_valor.setDisable(false);
//            label_valor.setDisable(false);
//        } else {
//            textfield_valor.setText("");
//            textfield_valor.setDisable(true);
//            label_valor.setDisable(true);
//        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populaCombobox();
        populaLabels();
    }  
    
    
    @FXML
    void textfield_quantidade_release(KeyEvent event) {
//        Formatter form = new Formatter();
//        form.setCaracteresValidos("0123456789");
//        form.setTf(textfield_quantidade);
//        form.formatter();
    }

    @FXML
    void textfield_valor_release(KeyEvent event) {
//        Formatter form = new Formatter();
//        form.setCaracteresValidos("0123456789.");
//        form.setTf(textfield_valor);
//        form.formatter();
    }
    
    public void populaCombobox(){
        combobox_tipo_de_movimentação.setPromptText("(Selecione)");
        combobox_tipo_de_movimentação.getItems().addAll("Saida");
    }
    
    public void fechaStage(){
        Stage stage = (Stage) button_cancelar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
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
    
    public void populaLabels(){
        label_produto.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getNome());
        label_sado.setText(""+Static.ProdutoEdit.ProdutoStatic.getProduto().getSaldo());
    }
    
    public boolean verificaCamposVazios(){
        if(!textfield_quantidade.equals("")){
            return true;
        }
        return false;
    }
    
}
