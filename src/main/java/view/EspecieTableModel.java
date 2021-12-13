/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Especie;
import model.EspecieDAO;
import java.util.List;

/**
 *
 * @author kugel
 */
public class EspecieTableModel extends GenericTableModel {
    
    public EspecieTableModel(List vDados) {
        super(vDados, new String[]{"Nome da Especie", "Qtd. Animais"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
              return String.class;
            case 1:
              return Integer.class;
            default:
              throw new IndexOutOfBoundsException("Não existe coluna para este index");
            }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Especie especie = (Especie) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return especie.getNomeEspecie();
            case 1:
            final List retriveAllAnimalWithEspecie = 
                    EspecieDAO
                            .getInstance()
                            .retriveAllAnimalWithEspecie(especie.getId());
                
                return retriveAllAnimalWithEspecie.isEmpty() 
                        ? 0 
                        : retriveAllAnimalWithEspecie.size();
            default:
                throw new IndexOutOfBoundsException("Não existe coluna para este index");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
