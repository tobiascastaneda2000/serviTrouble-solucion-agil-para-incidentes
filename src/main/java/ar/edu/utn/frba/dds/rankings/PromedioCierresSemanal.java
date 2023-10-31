package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;

import java.util.Comparator;

public class PromedioCierresSemanal implements CriterioRanking {

  @Override
  public Comparator<Entidad> criterioDeComparacion() {
    return Comparator.comparing(Entidad::promedioDuracionIncidentes);
  }

  public String nombreDelRanking(){
    return "Promedio de cierre de incidentes";
  }

}
