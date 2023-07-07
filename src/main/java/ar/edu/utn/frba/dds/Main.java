package ar.edu.utn.frba.dds;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
  public static void main(String[] args) {

    /*
    *
    * En args[0] indicaremos el tipo de tarea programada, ya que pueden existir varias
    * y solo se puede tener un "Main" por proyecto, de esta manera podemos diferenciarlas.
    *
    */
    switch(args[0]){
      case "notificacion": lanzarNotificacion();
                           break;
      case "ranking": lanzarRanking();
                      break;
      default:;
    }
  }

  public static void lanzarNotificacion() {

  }

  public static void lanzarRanking(){
    /*
     * List<Entidad> entidades = RepoEntidades.instance.getEntidades();
     * Entidad gualmayen = new Entidad(12, "Gualmayen ", "mail");
     * Entidad jorgito = new Entidad(11, "Jorgito", "mail");
     * Entidad quatar_aerolines = new Entidad(10, "Chorros ", "mail");
     * RepoEntidades.instance.guardarEntidad(gualmayen);
     * Para ranking Utilizaremos https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#comparing-java.util.function.Function-java.util.Comparator-
     */
  }



}
