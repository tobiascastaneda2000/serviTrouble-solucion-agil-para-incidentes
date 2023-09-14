package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.Comunidad;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;

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


}
