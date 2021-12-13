/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Cliente;
import java.util.List;
import model.ClienteDAO;

/**
 *
 * @author kugel
 */
public class ClienteTableModel extends GenericTableModel {

    public ClienteTableModel(List vDados) {
        super(vDados, new String[]{"Nome", "Endereço", "CEP", "E-mail", "Telefone"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex > 4) {
            throw new IndexOutOfBoundsException("Não existe coluna para este index");
        }
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = (Cliente) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return cliente.getNome();
            case 1:
                return cliente.getEndereco();
            case 2:
                return cliente.getCep();
            case 3:
                return cliente.getEmail();
            case 4:
                return cliente.getTelefone();
            default:
                throw new IndexOutOfBoundsException("Não existe coluna para este index");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Cliente cliente = (Cliente) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                cliente.setNome((String)aValue);
                break;
            case 1:
                cliente.setEndereco((String)aValue);
                break;
            case 2:
                cliente.setCep((String)aValue);
                break;
            case 3:
                cliente.setEmail((String)aValue);
                break;
            case 4:
                cliente.setTelefone((String)aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("Não existe coluna para este index");
        }  
        ClienteDAO.getInstance().update(cliente);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
