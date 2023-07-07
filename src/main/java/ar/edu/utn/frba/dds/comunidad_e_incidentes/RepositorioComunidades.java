package ar.edu.utn.frba.dds.comunidad_e_incidentes;

import ar.edu.utn.frba.dds.Servicio;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RepositorioComunidades {
  public static RepositorioComunidades instance = new RepositorioComunidades();
  Set<Comunidad> comunidades = new HashSet<>();

  public Set<Comunidad> getComunidades() {
    return this.comunidades;
  }

  public void guardarComunidad(Comunidad comunidad) {
    comunidades.add(comunidad);
  }

  public static RepositorioComunidades getInstance() {
    return instance;
  }

  public void notificarIncidente(Incidente incidente) {
    this.getComunidades().forEach(comunidad -> comunidad.notificarMiembros(incidente));
  }

  public void detectarCercania(Servicio servicio){
    this.comunidades.stream().flatMap(c->c.getMiembros().stream()).filter(m-> {
      try {
        return m.estaCerca(servicio);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }).forEach(m->m.usuario.getMedioNotificador().notificarSugerenciaRevisionIncidente(servicio));
  }
}
