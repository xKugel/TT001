/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Animal;
import model.AnimalDAO;
import model.ClienteDAO;
import model.Consulta;
import model.ConsultaDAO;
import model.Exame;
import model.ExameDAO;
import model.Veterinario;
import model.VeterinarioDAO;
import view.GenericTableModel;

/**
 *
 * @author kugel
 */
public class ConsultaTableModel extends GenericTableModel {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ConsultaTableModel(List vDados) {
        super(vDados, new String[]{"Data", "Hora", "Cliente", "Animal", "Veterinário", "Obs", "Exame", "Fim"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
                return String.class;
            case 7:
                return Boolean.class;
            default:
                throw new IndexOutOfBoundsException("columIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Consulta consulta = (Consulta) vDados.get(rowIndex);
        Animal animal;

        switch (columnIndex) {
            case 0:
                return dateFormat.format(consulta.getData().getTime());
            case 1:
                return consulta.getHora();
            case 2:
                animal = AnimalDAO.getInstance().retrieveById(consulta.getIdAnimal());
                return ClienteDAO.getInstance().retrieveById(animal.getIdCliente()).getNome();
            case 3:
                animal = AnimalDAO.getInstance().retrieveById(consulta.getIdAnimal());
                return animal.getNome();
            case 4:
                return VeterinarioDAO.getInstance().retrieveById(consulta.getIdVet()).getNome();
            case 5:
                return consulta.getComentarios();
            case 6:
                Exame exame = ExameDAO.getInstance().retriveByConsultaId(consulta.getId());
                return exame != null ? exame.getDescricao(): "";
            case 7:
                return consulta.isTerminado();
            default:
                throw new IndexOutOfBoundsException("columIndex out of bounds");
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Consulta consulta = (Consulta) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(dateFormat.parse((String) aValue));
                } catch (Exception e) {
                    Logger.getLogger(ConsultaTableModel.class.getName()).log(Level.SEVERE, null, e);
                }
                consulta.setData(cal);
                break;
            case 1:
                consulta.setHora((Integer) aValue);
                break;
            case 2,3,4:
                break;
            case 5:
                consulta.setComentarios((String) aValue);
                break;
            case 6:
                Exame exame = ExameDAO.getInstance().retriveByConsultaId(consulta.getId());
                exame.setDescricao((String) aValue);
                ExameDAO.getInstance().update(exame);
                break;
            case 7:
                consulta.setTerminado((Boolean) aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("Não existe coluna para este index");
        }
        ConsultaDAO.getInstance().update(consulta);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex < 2 || columnIndex > 4;
    }

}
