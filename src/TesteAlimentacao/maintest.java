/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TesteAlimentacao;

import ConsumerRest.UsuarioResource;
import Model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author aldir
 */
public class maintest {
    public static void main(String[] args) throws IOException, ParseException, InterruptedException, BiffException {
        UsuarioResource usuarioR = new UsuarioResource();
        Gson json = new Gson();
        Usuario usuario = json.fromJson(usuarioR.find("1"), Usuario.class);
        
        System.out.println("usuario: " + usuario.getNome());
        Workbook workbook = Workbook.getWorkbook(new File("saida.xls"));
        Sheet sheet = workbook.getSheet(0);
        int linhas = sheet.getRows();
        int coluna = 13;
        int pkproduto = 18;
        
        for(int i = 0; i < linhas; i++){
            
            Cell a1 = sheet.getCell(0, i);
            Cell a2 = sheet.getCell(coluna, i);
            
//            Cell a3 = sheet.getCell(2, i);
//            Cell a4 = sheet.getCell(3, i);
//            Cell a5 = sheet.getCell(4, i);
//            Cell a6 = sheet.getCell(5, i);
//            Cell a7 = sheet.getCell(6, i);
//            Cell a8 = sheet.getCell(7, i);
//            Cell a9 = sheet.getCell(8, i);
//            Cell a10 = sheet.getCell(9, i);
//            Cell a11 = sheet.getCell(10, i);
//            Cell a12 = sheet.getCell(11, i);
//            Cell a13 = sheet.getCell(12, i);
//            Cell a14 = sheet.getCell(13, i);
            
            
            
            if(Integer.parseInt(a2.getContents()) != 0){
                String dataaa = a1.getContents();
                StringBuilder data = new StringBuilder(dataaa);
                
                System.out.println("Linha: " + i);
                System.out.println("Data: " + data.insert(dataaa.length()-2, "20").toString());
                System.out.println("Quantidade: " + a2.getContents());
                
                System.out.println("-------------------------------------");
                
                System.out.println(data);
                AlimentacaoSaida alimentacao = new AlimentacaoSaida(data.toString(), pkproduto, Integer.parseInt(a2.getContents()), (float)0.0, usuario);
                alimentacao.buscaProduto();
                alimentacao.enviaMovimentacao();
                Thread.sleep(1000);
                
            }
            
            
//            System.out.println("preco: " + preco);
//            System.out.println("===============================");
            
            
        }

//          AlimentacaoEntrada alimentacao = new AlimentacaoEntrada("01/01/2018", 18, 5000 , (float)0.27, usuario);
//          alimentacao.buscaProduto();
//          alimentacao.enviaMovimentacao();

    }
    
    
}
