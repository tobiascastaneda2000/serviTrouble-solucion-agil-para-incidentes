package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.Usuario;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Horario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  private int hora;
  private int minuto;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  public Usuario usuario;

  public Horario(int hora, int minuto) {
    this.hora = hora;
    this.minuto = minuto;
  }

  public boolean esIgual(int hora2,int minuto2){

    if(this.hora == hora2 && this.minuto == minuto2){
      return true;
    }
    else{
      return false;
    }
  }

  /*
  *   Faltaria validar:
  *     - Los input del constructor -> Cada campo tenga los valores permitidos.
  *     - Salida del generador -> que la salida sea la correspondiente.
  *
  *   No se hizo mucho incapie en estos puntos debido a que cuando se tenga una interfaz gráfica
  *   se podrá visualizar mucho mejor esta "conversion" de horario a CronExpression.
  */
  /*public String generarExpresionCron(){
    return String.format("%s %s %s %s %s",minuto,hora,diaDelMes,mes,diaDeLaSemana);
  }*/

}
