package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.incidentes.Incidente;

import javax.persistence.*;

@Entity
public class Notificacion {
  @Id
  @GeneratedValue
  Long id;
  @ManyToOne
  @JoinColumn(name = "incidente_id")
  Incidente incidente;
  public Boolean fueNotificada = false;

  public Incidente getIncidente(){
    return incidente;
  }

  protected Notificacion(){}
  public Notificacion(Usuario usuario, Incidente incidente) {
    this.incidente = incidente;
  }
  public void ejecutarse(Usuario usuario) {
    usuario.notificarIncidente(incidente);
    this.fueNotificada = true;
  }
}

