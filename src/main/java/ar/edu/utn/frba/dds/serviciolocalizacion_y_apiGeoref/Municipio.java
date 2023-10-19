package ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("municipio")
public class Municipio extends Localizacion{

}
