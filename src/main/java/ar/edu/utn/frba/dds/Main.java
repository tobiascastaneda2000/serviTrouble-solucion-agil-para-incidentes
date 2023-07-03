package ar.edu.utn.frba.dds;

import java.util.Collections;
import java.util.List;

public class Main {
  public static void main(String[] args) {

    List<Entidad> entidades = RepoEntidades.instance.getEntidades();

    // Para ranking Utilizaremos https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#comparing-java.util.function.Function-java.util.Comparator-
    //Aun o sabemos si sera todod en main o se creara ademas una un iterfaz crearRankings


  }


}
