package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import java.util.Comparator;

@Entity(name="CriterioRanking")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_de_criterio", discriminatorType = DiscriminatorType.STRING)
public abstract class CriterioRanking {

  @Id
  @Column(name="id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  long id;

  @Transient
  Comparator<Entidad> criterio;

  public  Comparator<Entidad> getCriterio() {
    return this.criterio;
  }

}
