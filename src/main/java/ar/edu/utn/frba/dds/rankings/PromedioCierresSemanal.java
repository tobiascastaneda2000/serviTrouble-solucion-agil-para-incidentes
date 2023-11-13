package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.entidades.Entidad;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Comparator;

@Entity
@DiscriminatorValue("promedio_cierres_incidentes")
public class PromedioCierresSemanal extends CriterioRanking {

  @Transient
  public Comparator<Entidad> criterio = Comparator.comparing(Entidad::promedioDuracionIncidentes);

  public PromedioCierresSemanal() {
  }


  public Long getId() {
    return id;
  }

  @Override
  public String getNombre_criterio() {
    return nombre_criterio;
  }

  @Override
  public String getPath() {
    return path;
  }

  @Transient
  public String path = "src/main/resources/rankings-entidades-pc.csv";

  public String nombre_criterio = "promedio de cierres de incidentes";


  @Override
  public Comparator<Entidad> getCriterio() {
    return this.criterio;
  }


}
