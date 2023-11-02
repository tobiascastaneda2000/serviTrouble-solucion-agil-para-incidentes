package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Comparator;
@Entity
@DiscriminatorValue("promedio_cierres_incidentes")
public class PromedioCierresSemanal extends CriterioRanking {

  @Transient
 Comparator<Entidad> criterio = Comparator.comparing(Entidad::promedioDuracionIncidentes);

  public PromedioCierresSemanal() {
  }


  @Override
  public Comparator<Entidad> getCriterio() {
    return this.criterio;
  }



}
