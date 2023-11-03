package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;
import java.util.Comparator;

@Entity
@DiscriminatorValue("cantidad_incidentes_reportados")
public class CantidadReportesSemanal extends CriterioRanking {

  @Transient
  public Comparator<Entidad> criterio = Comparator.comparing(Entidad::cantidadDeIncidentesReportados);

  @Override
  public String getNombre_criterio() {
    return nombre_criterio;
  }

  public String nombre_criterio = "cantidad de incidentes reportados";

  public CantidadReportesSemanal() {
  }

  @Override
  public Comparator<Entidad> getCriterio() {
    return this.criterio;
  }


}
