package controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Animal;
import model.Cliente;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.AnimalDAO;
import model.ClienteDAO;
import model.Consulta;
import model.ConsultaDAO;
import model.EspecieDAO;
import model.Exame;
import model.ExameDAO;
import model.Veterinario;
import model.VeterinarioDAO;
import view.AnimalTableModel;
import view.ClienteTableModel;
import view.ConsultaTableModel;
import view.EspecieTableModel;
import view.GenericTableModel;
import view.VeterianarioTableModel;

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
    private static Veterinario vetSelecionado = null;
    private static JTextField clienteSelecionadoTextField = null;
    private static JTextField animalSelecionadoTextField = null;
    private static JTextField vetSelecionadoTextField = null;
    private static String baseQueryConsulta = ""
            + "SELECT"
            + " consulta.id AS id, "
            + " consulta.data AS data, "
            + " consulta.hora AS hora, "
            + " consulta.id_animal AS id_animal, "
            + " consulta.id_vet AS id_vet, "
            + " consulta.comentarios AS comentarios, "
            + " consulta.terminado AS terminado "
            + "FROM consultas AS consulta"
            + " LEFT JOIN animais AS animal"
            + "   ON animal.id = consulta.id_animal"
            + " LEFT JOIN especies AS especie"
            + "   ON especie.id = animal.id_especie";

    public static void setTableModel(JTable table, GenericTableModel tableModel) {
        table.setModel(tableModel);
    }

    public static void setTextFields(JTextField cliente, JTextField animal,
            JTextField vet) {
        clienteSelecionadoTextField = cliente;
        animalSelecionadoTextField = animal;
        vetSelecionadoTextField = vet;
    }

    public static Cliente getClienteSelecionado() {
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
            animalSelecionado = (Animal) selected;
            animalSelecionadoTextField.setText(animalSelecionado.getNome());
        } else if (selected instanceof Veterinario) {
            vetSelecionado = (Veterinario) selected;
            vetSelecionadoTextField.setText(vetSelecionado.getNome());
        }

    }

    /**
     * Processa o click na aba animal, e volta um booleano com informação se há
     * um cliente selecionado
     *
     * @param table jTable da view
     * @return true se houver cliente, false se não.
     */
    public static boolean clickAbaAnimal(JTable table) {
        boolean temClienteSelecionado = clienteSelecionado != null;
        List<Animal> animais = temClienteSelecionado
                ? AnimalDAO
                        .getInstance()
                        .retrieveByClienteId(
                                Controller.getClienteSelecionado().getId())
                : new ArrayList();
        if (animais == null) {
            animais = new ArrayList<>();
        }
        Controller.setTableModel(table, new AnimalTableModel(animais));
        return temClienteSelecionado;
    }

    public static void filtraNomeParecido(JTable table, String input) {
        if (table.getModel() instanceof ClienteTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(
                    ClienteDAO.getInstance().retrieveBySimilarName(input)
            );
        } else if (table.getModel() instanceof AnimalTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(
                    AnimalDAO.getInstance().retrieveBySimilarName(input)
            );
        } else if (table.getModel() instanceof VeterianarioTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(
                    VeterinarioDAO.getInstance().retrieveBySimilarName(input)
            );
        } else if (table.getModel() instanceof EspecieTableModel) {
            ((GenericTableModel) table.getModel()).addListOfItems(
                    EspecieDAO.getInstance().retrieveBySimilarName(input)
            );
        }
    }

    public static void clickAbaCliente(JTable table) {
        final List todosClientes = ClienteDAO.getInstance().retrieveAll();
        Controller.setTableModel(table, new ClienteTableModel(todosClientes));
    }

    public static void clickAbaVet(JTable table) {
        final List todosVets = VeterinarioDAO.getInstance().retrieveAll();
        Controller.setTableModel(table, new VeterianarioTableModel(todosVets));
    }

    public static void clickAbaEspecie(JTable table) {
        final List todasEspecies = EspecieDAO.getInstance().retrieveAll();
        Controller.setTableModel(table, new EspecieTableModel(todasEspecies));
    }

    public static String novoRegistro(JTable table) {
        if (table.getModel() instanceof ClienteTableModel) {
            ((GenericTableModel) table.getModel())
                    .addItem(ClienteDAO.getInstance().create("", "", "", "", ""));
        } else if (table.getModel() instanceof AnimalTableModel) {
            if (clienteSelecionado == null) {
                return "Selecione um cliente";
            }
            ((GenericTableModel) table.getModel())
                    .addItem(AnimalDAO.getInstance()
                            .create(
                                    "", 'M', 0,
                                    EspecieDAO.getInstance().retrieveLast().getId(),
                                    clienteSelecionado.getId()));
        } else if (table.getModel() instanceof VeterianarioTableModel) {
            ((GenericTableModel) table.getModel())
                    .addItem(VeterinarioDAO.getInstance().create("", "", ""));
        } else if (table.getModel() instanceof EspecieTableModel) {
            ((GenericTableModel) table.getModel())
                    .addItem(EspecieDAO.getInstance().create(""));
        }
        return null;
    }

    public static String clickApagar(JTable table) {
        if (table.getModel() instanceof ClienteTableModel) {
            if (clienteSelecionado != null) {
                List<Animal> animais = AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());
                boolean isAnyConsulta = animais.stream()
                        .map(animal -> ConsultaDAO.getInstance().retrieveByAnimalId(animal.getId()))
                        .anyMatch(consult -> consult != null);
                if (isAnyConsulta) {
                    return "Cliente tem consultas, não pode se excluido";
                }
                for (Animal animal : animais) {
                    AnimalDAO.getInstance().delete(animal);
                }
                ClienteDAO.getInstance().delete(clienteSelecionado);
                ((ClienteTableModel) table.getModel()).removeItem(table.getSelectedRow());
                clienteSelecionado = null;
                clienteSelecionadoTextField.setText("");
                animalSelecionado = null;
                animalSelecionadoTextField.setText("");
            } else {
                return "Selecione um cliente";
            }
        } else if (table.getModel() instanceof AnimalTableModel) {
            if (animalSelecionado != null) {
                Consulta found = ConsultaDAO.getInstance().retrieveByAnimalId(animalSelecionado.getId());
                if (found != null) {
                    return "Animal tem consultas, não pode ser excluido";
                }
                AnimalDAO.getInstance().delete(animalSelecionado);
                ((AnimalTableModel) table.getModel()).removeItem(table.getSelectedRow());
                animalSelecionado = null;
                animalSelecionadoTextField.setText("");
            } else {
                return "Selecione um animal";
            }
        } else if (table.getModel() instanceof VeterianarioTableModel) {
            if (vetSelecionado != null) {
                Consulta found = ConsultaDAO.getInstance().retrieveByVetId(vetSelecionado.getId());
                if (found != null) {
                    return "Veterinario tem consultas, não pode ser excluido";
                }
                VeterinarioDAO.getInstance().delete(vetSelecionado);
                ((VeterianarioTableModel) table.getModel()).removeItem(table.getSelectedRow());
                vetSelecionado = null;
                vetSelecionadoTextField.setText("");
            } else {
                return "Selecione um Veterinario";
            }
        } else if (table.getModel() instanceof EspecieTableModel) {
            return "Não é possivel apagar especies";
        } else if (table.getModel() instanceof ConsultaTableModel) {
            Consulta consulta = (Consulta) ((ConsultaTableModel) table.getModel()).getItem(table.getSelectedRow());
            Exame exame = ExameDAO.getInstance().retriveByConsultaId(consulta.getId());
            ExameDAO.getInstance().delete(exame);
            ConsultaDAO.getInstance().delete(consulta);
            ((ConsultaTableModel) table.getModel()).removeItem(table.getSelectedRow());
        }
        return null;
    }

    public static String criaConsulta(JTable table) {
        if (clienteSelecionado == null) {
            return "Selecione um cliente";
        }
        if (animalSelecionado == null) {
            return "Selecione um animal";
        }
        if (vetSelecionado == null) {
            return "Selecione um veterinário";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        Calendar now = Calendar.getInstance();
        Consulta novaConsulta = ConsultaDAO.getInstance().create(now, Integer.parseInt(dateFormat.format(now.getTime())) + 2, "", animalSelecionado.getId(), vetSelecionado.getId(), false);
        ExameDAO.getInstance().create("", novaConsulta.getId());
        ((ConsultaTableModel) table.getModel()).addItem(novaConsulta);
        return null;
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static void filtrarConsultas(JTable table, String sintomas, String esp,
            boolean todas, boolean hoje, boolean cliente, boolean animal,
            boolean vet) {
        if (table.getModel() instanceof ConsultaTableModel) {
            String where = " WHERE comentarios LIKE '%" + sintomas + "%' AND especie.nome_especie LIKE '%" + esp + "%' ";

            if (hoje) {
                Date now = Calendar.getInstance().getTime();
                LocalDateTime localDateTime = dateToLocalDateTime(now);
                String startOfDay = localDateTime.with(LocalTime.MIN).toString();
                where += " AND (strftime('%s', data) BETWEEN strftime('%s', '"+startOfDay+"') AND strftime('%s', date('"+startOfDay+"', '+1 day'))) ";
            }

            if (cliente && clienteSelecionado != null) {
                where += " AND animal.id_cliente = '" + clienteSelecionado.getId() + "' ";
            }

            if (animal && animalSelecionado != null) {
                where += " AND id_animal = '" + animalSelecionado.getId() + "' ";
            }

            if (vet && vetSelecionado != null) {
                where += " AND id_vet = '" + vetSelecionado.getId() + "' ";
            }
            System.out.println(where);
            String orderBy = " ORDER BY data, hora ";
            ((ConsultaTableModel) table.getModel())
                    .addListOfItems(ConsultaDAO
                            .getInstance().
                            retrieve(baseQueryConsulta + where + orderBy));
        }
    }
}
