package ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref;


import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name = "localizacion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "localizacion_tipo",discriminatorType = DiscriminatorType.STRING)
public abstract class Localizacion {

  @Id
  long id;
}
