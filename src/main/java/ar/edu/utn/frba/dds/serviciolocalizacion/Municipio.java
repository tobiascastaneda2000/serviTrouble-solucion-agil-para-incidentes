package ar.edu.utn.frba.dds.serviciolocalizacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("municipio")
public class Municipio extends Localizacion{

}
