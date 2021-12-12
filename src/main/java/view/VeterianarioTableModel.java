/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.topicos.tt001.Veterinario.Veterinario;
import java.util.List;

/**
 *
 * @author kugel
 */
public class VeterianarioTableModel extends GenericTableModel {
    public VeterianarioTableModel(List vDados) {
        super(vDados, new String[]{"Nome", "Endereço", "Telefone"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
              return String.class;
            case 1:
              return String.class;
            case 2:
              return String.class;
            default: 
              throw new IndexOutOfBoundsException("Não existe coluna para este index");
            }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veterinario veterinario = (Veterinario) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return veterinario.getNome();
            case 1:
                return veterinario.getEndereco();
            case 2:
                return veterinario.getTelefone();
            default:
                throw new IndexOutOfBoundsException("Não existe coluna para este index");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Veterinario veterinario = (Veterinario) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                veterinario.setNome((String)aValue);
                break;
            case 1:
                veterinario.setEndereco((String)aValue);
                break;
            case 2:
                veterinario.setTelefone((String)aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("Não existe coluna para este index");
        }  
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
