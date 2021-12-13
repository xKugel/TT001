/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author kugel
 */
public class Especie {
    private int id;
    private String nomeEspecie;

    public Especie(int id, String nomeEspecie) {
        this.id = id;
        this.nomeEspecie = nomeEspecie;
    }

    public int getId() {
        return id;
    }
    
    public String getNomeEspecie() {
        return nomeEspecie;
    }
}
