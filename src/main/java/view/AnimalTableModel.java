/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.topicos.tt001.Animal.Animal;
import com.topicos.tt001.Cliente.Cliente;
import com.topicos.tt001.Especie.EspecieDAO;
import com.topicos.tt001.Veterinario.VeterinarioDAO;
import java.util.List;

/**
 *
 * @author kugel
 */
public class AnimalTableModel extends GenericTableModel {
    
    public AnimalTableModel(List vDados) {
        super(vDados, new String[]{"Nome", "Sexo", "Idade", "Espécie"});
VeterinarioDAO.getInstance().retrieveAll().stream().forEach(e -> {
            System.out.println(e);
        });

    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
              return String.class;
            case 1:
              return Character.class;
            case 2:
              return Integer.class;
            case 3:
              return String.class;
            default: 
              throw new IndexOutOfBoundsException("Não existe coluna para este index");
            }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Animal animal = (Animal) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return animal.getNome();
            case 1:
                return animal.getSexo();
            case 2:
                return animal.getIdade();
            case 3:
                return EspecieDAO.getInstance().retrieveById(animal.getIdEspecie()).getNomeEspecie();
            default:
                throw new IndexOutOfBoundsException("Não existe coluna para este index");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Animal animal = (Animal) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                animal.setNome((String)aValue);
                break;
            case 1:
                animal.setSexo((Character)aValue);
                break;
            case 2:
                animal.setIdade((Integer)aValue);
                break;
            case 3:
                animal.setIdEspecie((Integer)aValue);
            default:
                throw new IndexOutOfBoundsException("Não existe coluna para este index");
        }  
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
