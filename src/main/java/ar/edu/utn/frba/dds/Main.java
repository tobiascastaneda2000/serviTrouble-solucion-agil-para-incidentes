package ar.edu.utn.frba.dds;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
  public static void main(String[] args) {

    //List<Entidad> entidades = RepoEntidades.instance.getEntidades();
    Entidad gualmayen = new Entidad(12, "Gualmayen ", "mail");
    Entidad jorgito = new Entidad(11, "Jorgito", "mail");
    Entidad quatar_aerolines = new Entidad(10, "Chorros ", "mail");

    RepoEntidades.instance.guardarEntidad(gualmayen);

    //Taresa planificadas internamente: ej TimerTask
    //Tareas planificadas internamente -> menos robustas y menos escalables, muchos recursos
    //Mejor usar las externas
/*
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        //Hacer alguna tarea
      }
    };

      Timer timer = new Timer();
      long internal = 5000L;
      timer.schedule(task ,0L,internal);
*/

    // Para ranking Utilizaremos https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#comparing-java.util.function.Function-java.util.Comparator-
    //Aun o sabemos si sera todod en main o se creara ademas una un iterfaz crearRankings


  }
}
