/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topicos.tt001.Exame;

/**
 *
 * @author kugel
 */
public class Exame {
    private int id;
    private String descricao;
    private int idConsulta;

    public Exame(int id, String descricao, int idConsulta) {
        this.id = id;
        this.descricao = descricao;
        this.idConsulta = idConsulta;
    }
    
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdConsulta() {
        return idConsulta;
    }    
}
