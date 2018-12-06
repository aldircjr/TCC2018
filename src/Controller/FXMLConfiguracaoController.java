/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Main.InicioFX;
import Model.Configuracao;
import Model.ConfiguracaoServidor;
import Model.Formatter;
import WebServices.ConfiguracaoResource;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLConfiguracaoController implements Initializable {

    @FXML private Button button_salvar;
    @FXML private Button button_cancelar;
    @FXML private TextField textfield_cnpj;
    @FXML private TextField textfield_razao_social;
    @FXML private TextField textfield_nome_fantasia;
    @FXML private TextField textfield_inscricao_estadual;
    @FXML private TextField textfield_logradouro;
    @FXML private TextField textfield_bairro;
    @FXML private TextField textfield_cidade;
    @FXML private TextField textfield_telefone;
    @FXML private TextField textfield_smtp;
    @FXML private TextField textfield_porta;
    @FXML private TextField textfield_email;
    @FXML private PasswordField textfield_senha;
    @FXML private TextField textfield_ip_servidor;
    @FXML private TextField textfield_porta_servidor;
    @FXML private ComboBox<String> combobox_uf;
    private Model.ConfiguracaoServidor config;
    private Configuracao configuracaoEmpresaEmail;
    
    
    @FXML
    void button_cancelar(ActionEvent event) throws Exception {
        InicioFX inicio = new InicioFX();
        inicio.start(new Stage());
        fechaStage();
    }

    @FXML
    void button_salvar(ActionEvent event) throws IOException, Exception {
        ConfiguracaoResource configResource = new ConfiguracaoResource();
        Gson json = new Gson();
        Configuracao confiEmpresaEmail = new Configuracao();
        confiEmpresaEmail.setCnpj(textfield_cnpj.getText());
        confiEmpresaEmail.setIe(textfield_inscricao_estadual.getText());
        confiEmpresaEmail.setRazaoSocial(textfield_razao_social.getText());
        confiEmpresaEmail.setNomeFantasia(textfield_nome_fantasia.getText());
        confiEmpresaEmail.setLogradouro(textfield_logradouro.getText());
        confiEmpresaEmail.setBairro(textfield_bairro.getText());
        confiEmpresaEmail.setCidade(textfield_cidade.getText());
        confiEmpresaEmail.setUf(combobox_uf.getValue());
        confiEmpresaEmail.setTelefone(textfield_telefone.getText());
        confiEmpresaEmail.setSmtp(textfield_smtp.getText());
        confiEmpresaEmail.setPorta(textfield_porta.getText());
        confiEmpresaEmail.setEmail(textfield_email.getText());
        confiEmpresaEmail.setSenha(textfield_senha.getText());
        
        if(configResource.countREST().equals("1")){
            confiEmpresaEmail.setID(1);
            configResource.edit(json.toJson(confiEmpresaEmail));
        } else if(configResource.countREST().equals("0")) {
            configResource.create(json.toJson(confiEmpresaEmail));
        }
        
        
        config.gravacaoConfiguracaoServidor(textfield_ip_servidor.getText(), textfield_porta_servidor.getText());
        
        InicioFX inicio = new InicioFX();
        inicio.start(new Stage());
        fechaStage();
        
    }
    
    
      @FXML
    void cnpj_key_released(KeyEvent event) {
        Formatter form = new Formatter();
        form.setMask("##.###.###/####-##");
        form.setCaracteresValidos("0123456789");
        form.setTf(textfield_cnpj);
        form.formatter();
    }

    @FXML
    void telefone_key_released(KeyEvent event) {
        Formatter form = new Formatter();
        form.setMask("(##)####-####");
        form.setCaracteresValidos("0123456789");
        form.setTf(textfield_telefone);
        form.formatter();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.config = new ConfiguracaoServidor();
        try {
            config.verificaConfiguracaoServidor();
            config.leituraConfiguracaoServidor();
            textfield_ip_servidor.setText(config.getIpServidor());
            textfield_porta_servidor.setText(config.getPortaServidor());
        } catch (IOException ex) {
            invocaErro("ERRO!", "Não foi possivel localizar o arquivo de configuração do Servidor");
        }
        populaComboboxUF();
        
        buscaConfiguracaoEmpresaEmail();

    }    
    
    public void buscaConfiguracaoEmpresaEmail(){
        try {
            ConfiguracaoResource configResource = new ConfiguracaoResource();
            if(configResource.countREST().equals("1")){
                Gson json = new Gson();
                this.configuracaoEmpresaEmail = json.fromJson(configResource.find("1"), Configuracao.class);
                populaConfiguracaoEmpresaEmail();
            } else {
                System.out.println("nao tem configuracao cadastrada ainda!");
            }
        } catch (IOException ex) {
            invocaErro("Erro", "Não foi possivel buscar as configurações da empresa e de email!");
        }
    }
    
    public void populaConfiguracaoEmpresaEmail(){
        textfield_cnpj.setText(this.configuracaoEmpresaEmail.getCnpj());
        textfield_inscricao_estadual.setText(this.configuracaoEmpresaEmail.getIe());
        textfield_razao_social.setText(this.configuracaoEmpresaEmail.getRazaoSocial());
        textfield_nome_fantasia.setText(this.configuracaoEmpresaEmail.getNomeFantasia());
        textfield_logradouro.setText(this.configuracaoEmpresaEmail.getLogradouro());
        textfield_bairro.setText(this.configuracaoEmpresaEmail.getBairro());
        textfield_cidade.setText(this.configuracaoEmpresaEmail.getCidade());
        textfield_telefone.setText(this.configuracaoEmpresaEmail.getTelefone());
        combobox_uf.setValue(this.configuracaoEmpresaEmail.getUf());
        textfield_smtp.setText(this.configuracaoEmpresaEmail.getSmtp());
        textfield_porta.setText(this.configuracaoEmpresaEmail.getPorta());
        textfield_email.setText(this.configuracaoEmpresaEmail.getEmail());
        textfield_senha.setText(this.configuracaoEmpresaEmail.getSenha());
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
    
    public void populaComboboxUF(){
        combobox_uf.getItems().clear();
        combobox_uf.getItems().addAll("AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MG","MS",
                "MT","PA","PB","PE","PI","PR","RJ","RN","RO","RR","RS","SC","SE","SP","TO");
        combobox_uf.setPromptText("(Selecionar)");
    }
    
    
    
    
    
}
