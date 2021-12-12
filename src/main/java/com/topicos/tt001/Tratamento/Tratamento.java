/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topicos.tt001.Tratamento;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author kugel
 */
public class Tratamento {
    private int id;
    private String nome;
    private String dataInicio;
    private String dataFim;
    private int idAnimal;
    private boolean terminou;

    public Tratamento(int id, String nome, String dataInicio, String dataFim, int animal, boolean terminou) {
        this.id = id;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idAnimal = animal;
        this.terminou = terminou;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public boolean isTerminou() {
        return terminou;
    }

    public void setTerminou(boolean terminou) {
        this.terminou = terminou;
    }
}
