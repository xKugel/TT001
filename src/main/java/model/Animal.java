/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.Especie;

/**
 *
 * @author kugel
 */
public class Animal {
    private int id;
    private String nome;
    private char sexo;
    private int idade;
    private int id_especie;
    private int id_cliente;

    public Animal(int id, String nome, char sexo, int idade, int id_especie, int id_cliente) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
        this.id_especie = id_especie;
        this.id_cliente = id_cliente;
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

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getIdEspecie() {
        return id_especie;
    }

    public void setIdEspecie(int id_especie) {
        this.id_especie = id_especie;
    }

    public int getIdCliente() {
        return id_cliente;
    }

    public void setIdCliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }
    
    
}
