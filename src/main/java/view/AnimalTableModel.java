/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Animal;
import model.EspecieDAO;
import model.VeterinarioDAO;
import java.util.List;
import model.AnimalDAO;
import model.Especie;

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
                String nomeEspecie = (String)aValue;
                List<Especie> retrieveBySimilarName = 
                    EspecieDAO.getInstance().retrieveBySimilarName(nomeEspecie);
                Especie especie = retrieveBySimilarName == null 
                        || retrieveBySimilarName.isEmpty() 
                        ? null :
                        retrieveBySimilarName.get(0);
                animal.setIdEspecie((
                        especie != null 
                                ? especie 
                                : EspecieDAO.getInstance().create(nomeEspecie))
                        .getId());
                break;
            default:
                throw new IndexOutOfBoundsException("Não existe coluna para este index");
        }  
        AnimalDAO.getInstance().update(animal);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    
    
    
}
