/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.Animal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kugel
 */
public class Cliente {
    private int id;
    private String nome;
    private String endereco;
    private String telefone;
    private String cep;
    private String email;
    
    private List<Animal> animais;

    public Cliente(int id, String nome, String endereco, String telefone, String cep, String email) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.cep = cep;
        this.email = email;
        this.animais = new ArrayList<>();
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Animal> getAnimais() {
        return new ArrayList<>(animais);
    }

    private void setAnimais(List<Animal> animais) {
        this.animais = animais;
    }
    
    public void addAnimal(Animal animal) {
        List<Animal> animais = this.getAnimais();
        animais.add(animal);
        this.setAnimais(animais);
    }
}
