package ar.edu.utn.frba.dds.notificador;

public class Horario {
  private int hora;
  private int minuto;

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
