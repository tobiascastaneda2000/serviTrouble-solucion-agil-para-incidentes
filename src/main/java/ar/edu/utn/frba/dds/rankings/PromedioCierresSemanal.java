package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.Servicio;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.EstadoIncidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

public class PromedioCierresSemanal implements CriterioRanking {

  @Override
  public Comparator<Entidad> criterioDeComparacion() {
    return Comparator.comparing(Entidad::promedioDuracionIncidentes);
  }

}
