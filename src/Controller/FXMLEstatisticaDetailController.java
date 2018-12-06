/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ConsumerRest.PedidosResource;
import Main.EstatisticaFX;
import Model.Pedido;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aldir
 */
public class FXMLEstatisticaDetailController implements Initializable {

    @FXML private Label label_produtoNome;
    @FXML private Label label_ean;
    @FXML private Label label_unidade;
    @FXML private ImageView imageview_ABC;
    @FXML private Label label_saldo;
    @FXML private Label label_estoqueIdeal;
    @FXML private Label label_pontoDeRessuprimento;
    @FXML private Label label_estoqueDeSeguranca;
    @FXML private Label label_mediaConsumo;
    @FXML private Label label_nomeFornecedor;
    @FXML private Label label_cnpj;
    @FXML private Label label_telefone;
    @FXML private Label label_email;
    @FXML private Label label_prazoEntrega;
    @FXML private Label label_previsaoRessuprimento;
    @FXML private Label label_nivelImportancia;
    @FXML private BarChart<String, Number> grafico_bar;
    @FXML private CategoryAxis categoryAxis;
    @FXML private NumberAxis numberAxis;
    @FXML private Button button_voltar;
    
    private int saldo;
    private float mediaConsumo;
    private int pontoDeRessuprimento;
    private int estoqueDeSeguranca;
    private int estoqueIdeal;
    private List<Pedido> listPedido;

    

    @FXML
    void button_voltar(ActionEvent event) throws Exception {
        Static.ProdutoEdit.ProdutoStatic.setNull();
        EstatisticaFX estatistica = new EstatisticaFX();
        estatistica.start(new Stage());
        fechaStage();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        popularCampos();
    }    
    
    public void fechaStage(){
        Stage stage = (Stage) button_voltar.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
    }
    
    public void popularCampos(){
        if (Static.ProdutoEdit.ProdutoStatic.getProduto().getNome().length()>40){
            label_produtoNome.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getNome().substring(0, 40).concat("..."));
        } else {
            label_produtoNome.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getNome());
        }
        
        label_ean.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getEan());
        label_unidade.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getUnidade());
        label_nomeFornecedor.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getFornecedor().getNome());
        label_cnpj.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getFornecedor().getCnpj());
        label_telefone.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getFornecedor().getTelefone());
        label_email.setText(Static.ProdutoEdit.ProdutoStatic.getProduto().getFornecedor().getEmail());
        label_prazoEntrega.setText(""+Static.ProdutoEdit.ProdutoStatic.getProduto().getFornecedor().getPrazoDeEntrega());
        
        if (Static.ProdutoEdit.ProdutoStatic.getProduto().getCurvaABC() != null){
            if(Static.ProdutoEdit.ProdutoStatic.getProduto().getCurvaABC().equals("A")){
                imageview_ABC.setImage(new Image("/icon/A.png"));
                label_nivelImportancia.setText("Alta");
            } else if (Static.ProdutoEdit.ProdutoStatic.getProduto().getCurvaABC().equals("B")){
                imageview_ABC.setImage(new Image("/icon/B.png"));
                label_nivelImportancia.setText("Média");
            } else if (Static.ProdutoEdit.ProdutoStatic.getProduto().getCurvaABC().equals("C")){
                imageview_ABC.setImage(new Image("/icon/C.png"));
                label_nivelImportancia.setText("Baixa");
            }
        } else {
            imageview_ABC.setImage(new Image("/icon/N.png"));
            label_nivelImportancia.setText("Não classificado");
        } 
        
        
        this.saldo = Static.ProdutoEdit.ProdutoStatic.getProduto().getSaldo();
        this.mediaConsumo = Static.ProdutoEdit.ProdutoStatic.getProduto().getMediaConsumo();
        this.pontoDeRessuprimento = Static.ProdutoEdit.ProdutoStatic.getProduto().getPontoDeRessuprimento();
        this.estoqueDeSeguranca = Static.ProdutoEdit.ProdutoStatic.getProduto().getEstoqueDeSeguranca();
        this.estoqueIdeal = Static.ProdutoEdit.ProdutoStatic.getProduto().getEstoqueIdeal();
       
        
        float f = saldo/mediaConsumo;
        int saldoDiasArredondado = Math.round(f);
        System.out.println("testando float: "+ saldoDiasArredondado);

//        System.out.println(new Gson().toJson(Static.ProdutoEdit.ProdutoStatic.getProduto()));
        if (mediaConsumo != 0){
            label_saldo.setText(saldo + " ("+ saldoDiasArredondado +" Dias)");
            label_estoqueIdeal.setText((int)mediaConsumo*estoqueIdeal + " ("+ estoqueIdeal +" Dias)");
            label_pontoDeRessuprimento.setText((int)mediaConsumo*pontoDeRessuprimento+" ("+ pontoDeRessuprimento +" Dias)");
            label_estoqueDeSeguranca.setText((int)mediaConsumo*estoqueDeSeguranca +" ("+ estoqueDeSeguranca +" Dias)");
            label_mediaConsumo.setText(""+mediaConsumo);
            if((saldo/mediaConsumo) - pontoDeRessuprimento < 0){
                label_previsaoRessuprimento.setText("0 Dia(s)");
            } else {
                label_previsaoRessuprimento.setText((int)(saldo/mediaConsumo) - pontoDeRessuprimento +" Dia(s)");
            }
            
            
            atualizaGrafico(saldo, (int)mediaConsumo*estoqueIdeal, (int)mediaConsumo*pontoDeRessuprimento);
            
        } else {
            label_saldo.setText(""+saldo);
            label_estoqueIdeal.setText("Nao calculado");
            label_pontoDeRessuprimento.setText("Não calculado");
            label_estoqueDeSeguranca.setText("Não calculado");
            label_mediaConsumo.setText(""+mediaConsumo);
            label_previsaoRessuprimento.setText("Não calculado");
        }

        
    }
    
    public void invocaConfirmacao(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.show();
    }
 
    public void invocaErro(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.show();
    }
    
    
    public boolean solititaConfirmacao(String titulo, String descricao){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(titulo);
        alert.setContentText(descricao);
        alert.showAndWait();
        ButtonType b = alert.getResult();
        if(b == ButtonType.OK){
            return true;
        } 
        return false;
    }
    
    public void buscaPedidosDoProduto() throws IOException{
        PedidosResource pedidoResource = new PedidosResource();
        Gson json = new Gson();
        this.listPedido = json.fromJson(pedidoResource.findByProduto(Static.ProdutoEdit.ProdutoStatic.getProduto().getNome()), new TypeToken<List<Pedido>>(){}.getType());
    }
    
    public void atualizaGrafico(int estoqueAtual, int estoqueIdeal, int pontoDeRessuprimento){
        
        grafico_bar.setTitle("Nível de estoque");
        categoryAxis.setLabel("");       
        numberAxis.setLabel("Quantidade");
        
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Estoque Atual");
        series1.getData().add(new XYChart.Data("", (Number)estoqueAtual));
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Estoque Ideal");
        series2.getData().add(new XYChart.Data("", (Number)estoqueIdeal));
        
//        XYChart.Series series3 = new XYChart.Series();
//        series3.setName("Ponto de Ressuprimento");
//        series3.getData().add(new XYChart.Data("", (Number)pontoDeRessuprimento));
        System.out.println(""+grafico_bar.isLegendVisible());
        grafico_bar.getData().addAll(series1, series2);
    }

}