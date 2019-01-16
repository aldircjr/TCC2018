/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author aldir
 */
public class Produto {
    private Integer id;
    private String ean;
    private String nome;
    private String unidade;
    private int diasEstoqueDesejavel;
    private int diasEstoqueDisponivel;
    private int saldo;
    private String curvaABC;
    private int estoqueDeSeguranca; 
    private int pontoDeRessuprimento;
    private int estoqueIdeal;
    private Fornecedor fornecedor;
    private float valorMovimentado;
    private float porcMovimentacao;
    private Date dataCadastro;
    private float mediaConsumo;
    private int diasParaCalcularEstoque;

    public Produto(String ean, String nome, String unidade, int diasEstoqueDesejavel, Fornecedor fornecedor, int diasParaCalcular) {
        this.ean = ean;
        this.nome = nome;
        this.unidade = unidade;
        this.diasEstoqueDesejavel = diasEstoqueDesejavel;
        this.fornecedor = fornecedor;
        this.diasParaCalcularEstoque = diasParaCalcular;
    }

    public float getValorMovimentado() {
        return valorMovimentado;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public float getMediaConsumo() {
        return mediaConsumo;
    }

    public void setMediaConsumo(float mediaConsumo) {
        this.mediaConsumo = mediaConsumo;
    }

    public int getDiasParaCalcularEstoque() {
        return diasParaCalcularEstoque;
    }

    public void setDiasParaCalcularEstoque(int diasParaCalcularEstoque) {
        this.diasParaCalcularEstoque = diasParaCalcularEstoque;
    }

    
    
    
    public int getEstoqueDeSeguranca() {
        return estoqueDeSeguranca;
    }

    public void setEstoqueDeSeguranca(int estoqueDeSeguranca) {
        this.estoqueDeSeguranca = estoqueDeSeguranca;
    }

    public int getPontoDeRessuprimento() {
        return pontoDeRessuprimento;
    }

    public void setPontoDeRessuprimento(int pontoDeRessuprimento) {
        this.pontoDeRessuprimento = pontoDeRessuprimento;
    }

    public int getEstoqueIdeal() {
        return estoqueIdeal;
    }

    public void setEstoqueIdeal(int estoqueIdeal) {
        this.estoqueIdeal = estoqueIdeal;
    }
    
    

    public void setValorMovimentado(float valorMovimentado) {
        this.valorMovimentado = valorMovimentado;
    }

    public float getPorcMovimentacao() {
        return porcMovimentacao;
    }

    public void setPorcMovimentacao(float porcMovimentacao) {
        this.porcMovimentacao = porcMovimentacao;
    }
    
    

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getDiasEstoqueDesejavel() {
        return diasEstoqueDesejavel;
    }

    public void setDiasEstoqueDesejavel(int diasEstoqueDesejavel) {
        this.diasEstoqueDesejavel = diasEstoqueDesejavel;
    }

    public int getDiasEstoqueDisponivel() {
        return diasEstoqueDisponivel;
    }

    public void setDiasEstoqueDisponivel(int diasEstoqueDisponivel) {
        this.diasEstoqueDisponivel = diasEstoqueDisponivel;
    }

    public String getCurvaABC() {
        return curvaABC;
    }

    public void setCurvaABC(String curvaABC) {
        this.curvaABC = curvaABC;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    

    
}
