package model;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author kugel
 * 
 */
public class Consulta {
    private Calendar data;
    private int hora;
    private String comentarios;
    private int idAnimal;
    private int idVet;
//    private int idTratamento
    private boolean terminado;    
    private int id;

    public Consulta(int id, Calendar data, int hora, String comentarios, int idAnimal, int idVet, boolean terminou) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.comentarios = comentarios;
        this.idAnimal = idAnimal;
        this.idVet = idVet;
        this.terminado = terminou;
    }

    public int getId() {
        return id;
    }
    

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getIdVet() {
        return idVet;
    }

    public void setIdVet(int idVet) {
        this.idVet = idVet;
    }

//    public int getIdTratamento() {
//        return idTratamento;
//    }
//
//    public void setIdTratamento(int idTratamento) {
//        this.idTratamento = idTratamento;
//    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }    
}
