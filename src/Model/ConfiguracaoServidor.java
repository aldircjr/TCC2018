/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author aldir
 */
public class ConfiguracaoServidor {
    private String arquivo = "arquivo.txt";
    private String ipServidor = "";
    private String portaServidor = "";
    


    public String getIpServidor() {
        return ipServidor;
    }

    public String getPortaServidor() {
        return portaServidor;
    }
    
    
    
    
    public void leituraConfiguracaoServidor() throws IOException{
        
        BufferedReader br = new BufferedReader(new FileReader("arquivo.txt"));
        System.out.println();
        while (br.ready()) {
            this.ipServidor = br.readLine();
            this.portaServidor = br.readLine();
        }
        br.close();
    }
    
    
    
    public void verificaConfiguracaoServidor() throws IOException{
        File file = new File (this.arquivo);
        if (!file.exists()){
            FileWriter arq = new FileWriter(this.arquivo);
            //System.out.println("criou arquivo.txt");
            arq.close();
        } 
    }
    
    
     public void gravacaoConfiguracaoServidor (String ip, String porta) throws IOException{
        FileWriter arq = new FileWriter(this.arquivo, false);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf(ip+"\n");
        gravarArq.printf(porta);
        arq.close();
    }
    
    
}
