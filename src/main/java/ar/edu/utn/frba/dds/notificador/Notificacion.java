package ar.edu.utn.frba.dds.notificador;

import ar.edu.utn.frba.dds.comunidad.Usuario;
import ar.edu.utn.frba.dds.incidentes.Incidente;

import ar.edu.utn.frba.dds.repositorios.RepoUsuarios;
import javax.persistence.*;

@Entity
public class Notificacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @ManyToOne
  @JoinColumn(name = "incidente_id")
  Incidente incidente;
  public Boolean fueNotificada = false;

  public Incidente getIncidente() {
    return incidente;
  }

  protected Notificacion() {
  }

  public Notificacion(Usuario usuario, Incidente incidente) {
    this.incidente = incidente;
  }

  public void ejecutarse(Usuario usuario) {
    usuario.notificarIncidente(incidente);
    this.fueNotificada = true;
    usuario.deleteNotificacion(this);
    RepoUsuarios.getInstance().update(usuario);
  }
}

