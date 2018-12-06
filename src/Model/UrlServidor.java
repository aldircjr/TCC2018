/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import javafx.scene.control.Alert;

/**
 *
 * @author aldir
 */
public class UrlServidor {
    private String BASE_URL_1 = "http://";
    private String BASE_URL_IP;
    private String BASE_URL_PORTA;
    private String BASE_URL_2 = "/TestesTCC/banco"; 
    
    public void iniciaUrl() throws IOException{
        ConfiguracaoServidor conf = new ConfiguracaoServidor();
        conf.leituraConfiguracaoServidor();
        this.BASE_URL_IP = conf.getIpServidor();
        this.BASE_URL_PORTA = conf.getPortaServidor();
        
        
    }

    public String getUrl() throws IOException {
        try {
            iniciaUrl();
        } catch(Exception e){
            invocaErro("Erro", "Não foi possivel estabelecer conexão com o servidor! \nVerifique o IP em Configurações -> Servidor!");
        }

        return BASE_URL_1+BASE_URL_IP+":"+BASE_URL_PORTA+BASE_URL_2;
        
    }
    
    
    public void invocaErro(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.show();
    }
    
}
