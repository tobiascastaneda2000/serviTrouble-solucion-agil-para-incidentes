package ar.edu.utn.frba.dds.serviciolocalizacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("departamento")
public class Departamento extends Localizacion{

}
