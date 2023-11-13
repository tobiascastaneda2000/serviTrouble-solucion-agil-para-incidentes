package ar.edu.utn.frba.dds.serviciolocalizacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("provincia")
public class Provincia extends Localizacion{

}
