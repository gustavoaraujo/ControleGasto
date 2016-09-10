package com.david.gustavo.controlegasto.classes;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Cliente on 04/09/2016.
 */
public class Movimentacao implements Serializable{
    private int id;
    private double valor;
    private String descricao;
    private Date data;
    private boolean ehContinuo, ehDespesa;
    private Categoria categoria;
    private Usuario usuario;

    public Movimentacao() {
    }

    public Movimentacao(double valor, String descricao, Date data, boolean ehContinuo, boolean ehDespesa, Categoria categoria, Usuario usuario) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.ehContinuo = ehContinuo;
        this.categoria = categoria;
        this.usuario = usuario;
        this.ehDespesa = ehDespesa;
    }

    public Movimentacao(int id, double valor, String descricao, Date data, boolean ehContinuo, boolean ehDespesa, Categoria categoria, Usuario usuario) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.ehContinuo = ehContinuo;
        this.ehDespesa = ehDespesa;
        this.categoria = categoria;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isEhContinuo() {
        return ehContinuo;
    }

    public void setEhContinuo(boolean ehContinuo) {
        this.ehContinuo = ehContinuo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isEhDespesa() {
        return ehDespesa;
    }

    public void setEhDespesa(boolean ehDespesa) {
        this.ehDespesa = ehDespesa;
    }
}
