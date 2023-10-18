package ar.edu.utn.frba.dds.notificador;


import ar.edu.utn.frba.dds.incidentes.Incidente;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@Entity(name = "medioNotificador")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_medio_notificador",discriminatorType = DiscriminatorType.STRING)
public abstract class MedioNotificador {
  @Id
  long id;
  public abstract void notificarUnIncidente(Incidente incidente, String contacto);
//Incluye la apertura de incidentes y la sugerencias
}
