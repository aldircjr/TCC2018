/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TesteAlimentacao;

import ConsumerRest.MovimentacaoResource;
import ConsumerRest.ProdutoResource;
import Model.Movimentacao;
import Model.Permissoes;
import Model.Produto;
import Model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.security.Permission;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author aldir
 */
public class AlimentacaoEntrada {
    private String data;
    private int idProduto;
    private int quantidade;
    private float preco;
    private Produto produto;
    private Usuario usuario;
    private Permissoes perm;
    private int tipo;

    public AlimentacaoEntrada(String data, int id, int quantidade, float preco, Usuario usuario) {
        this.data = data;
        this.idProduto = id;
        this.quantidade = quantidade;
        this.preco = preco;
        this.usuario = usuario;
        this.tipo = 1;
    }
    
    public void buscaProduto() throws IOException{
        ProdutoResource produtoResource = new ProdutoResource();
        Gson json = new Gson();
        this.produto = json.fromJson(produtoResource.find(""+this.idProduto), Produto.class);
    }
    
    public void enviaMovimentacao() throws IOException, ParseException{
        MovimentacaoResource movimentacaoResource = new MovimentacaoResource();
        Movimentacao movimentacao = new Movimentacao(this.quantidade, this.tipo, this.produto, this.usuario, this.preco);
        
        DataModel datamodel = new DataModel(this.data);
        movimentacaoResource.createData(new Gson().toJson(datamodel));
        movimentacao.setData(null);
        movimentacao.getProduto().setDataCadastro(null);
        movimentacaoResource.create(new Gson().toJson(movimentacao));
        
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Permissoes getPerm() {
        return perm;
    }

    public void setPerm(Permissoes perm) {
        this.perm = perm;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    

    
}
