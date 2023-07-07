package ar.edu.utn.frba.dds.notificador;

public class Horario {
  private String hora;
  private String minuto;
  private String diaDelMes;
  private String mes;
  private String diaDeLaSemana;

  public Horario(String hora, String minuto, String diaDelMes, String mes, String diaDeLaSemana) {
    this.hora = hora;
    this.minuto = minuto;
    this.diaDelMes = diaDelMes;
    this.mes = mes;
    this.diaDeLaSemana = diaDeLaSemana;
  }

  /*
  *   Faltaria validar:
  *     - Los input del constructor -> Cada campo tenga los valores permitidos.
  *     - Salida del generador -> que la salida sea la correspondiente.
  *
  *   No se hizo mucho incapie en estos puntos debido a que cuando se tenga una interfaz gráfica
  *   se podrá visualizar mucho mejor esta "conversion" de horario a CronExpression.
  */
  public String generarExpresionCron(){
    return String.format("%s %s %s %s %s",minuto,hora,diaDelMes,mes,diaDeLaSemana);
  }

}
