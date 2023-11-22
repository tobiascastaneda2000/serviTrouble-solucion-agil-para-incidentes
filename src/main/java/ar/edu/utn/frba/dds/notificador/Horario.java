package ar.edu.utn.frba.dds.notificador;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Embeddable
public class Horario {

  public int hora;
  public int minuto;


  public Horario(int hora, int minuto) {
    this.hora = hora;
    this.minuto = minuto;
  }

  protected Horario() {

  }

  public int getHora() {
    return this.hora;
  }

  public int getMinuto() {
    return minuto;
  }

  public void setHora(int hora) {
    this.hora = hora;
  }

  public void setMinuto(int minuto) {
    this.minuto = minuto;
  }

  @Override
  public boolean equals(Object otro) {

    if ((otro == null) || (otro.getClass() != getClass())) {
      return false;
    } else {
      Horario otroHorario = (Horario) otro;
      if (otroHorario.minuto == this.minuto && otroHorario.hora == this.hora) {
        return true;
      } else {
        return false;
      }
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(hora, minuto);
  }
}
