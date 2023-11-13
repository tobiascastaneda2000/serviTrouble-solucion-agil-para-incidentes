package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.entidades.Entidad;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.Comparator;

@Entity(name = "CriterioRanking")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_de_criterio", discriminatorType = DiscriminatorType.STRING)
public abstract class CriterioRanking {

  public Long getId() {
    return id;
  }

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  public String getPath() {
    return path;
  }

  public String path;

  @OneToMany
  @JoinColumn(name = "ranking_id")
  public List<Entidad> entidadesOrdenadas = new ArrayList<>();
  public String nombre_criterio;

  public String getNombre_criterio() {
    return nombre_criterio;
  }

  @Transient
  public Comparator<Entidad> criterio;

  public Comparator<Entidad> getCriterio() {
    return this.criterio;
  }


  public void setEntidadesOrdenadas(List<Entidad> entidadesOrdenadas) {
    this.entidadesOrdenadas = entidadesOrdenadas;
  }
  public List<Entidad> getEntidadesOrdenadas() {
    return entidadesOrdenadas;
  }
}
