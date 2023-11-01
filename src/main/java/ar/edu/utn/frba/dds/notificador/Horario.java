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
  public int hora;
  public int minuto;

  public Horario(int hora, int minuto) {
    this.hora = hora;
    this.minuto = minuto;
  }

  protected Horario() {

  }

  public int getHora(){
    return hora;
  }

  public int getMinuto(){
    return minuto;
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
