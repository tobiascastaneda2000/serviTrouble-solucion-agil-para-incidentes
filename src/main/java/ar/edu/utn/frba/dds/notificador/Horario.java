package ar.edu.utn.frba.dds.notificador;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Horario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  private int hora;
  private int minuto;

  public Horario(int hora, int minuto) {
    this.hora = hora;
    this.minuto = minuto;
  }

  protected Horario() {

  }

  public boolean esIgual(int hora2,int minuto2){

    if(this.hora == hora2 && this.minuto == minuto2){
      return true;
    }
    else{
      return false;
    }
  }


}
