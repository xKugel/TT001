package controller;

import com.topicos.tt001.Animal.Animal;
import com.topicos.tt001.Cliente.Cliente;
import javax.swing.JTable;
import javax.swing.JTextField;
import view.GenericTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kugel
 */
public class Controller {
    private static Cliente clienteSelecionado = null;
    private static Animal animalSelecionado = null;
    private static JTextField clienteSelecionadoTextField = null;
    private static JTextField animalSelecionadoTextField = null;

    public static void setTableModel(JTable table, GenericTableModel tableModel) {
        table.setModel(tableModel);
    }
    
    public static void setTextFields(JTextField cliente, JTextField animal) {
        clienteSelecionadoTextField = cliente;
        animalSelecionadoTextField = animal;
    }
    
    public static Cliente getClienteSelecionado () {
        return clienteSelecionado;
    }
    
    public static void setSelected(Object selected) {
        if (selected instanceof Cliente) {
            clienteSelecionado = (Cliente) selected;
            clienteSelecionadoTextField.setText(clienteSelecionado.getNome());
            animalSelecionado = null;
            animalSelecionadoTextField.setText("");
            return;
        } else if (selected instanceof Animal) {
            animalSelecionado = (Animal)selected;
            animalSelecionadoTextField.setText(animalSelecionado.getNome());
        }
    }
}
