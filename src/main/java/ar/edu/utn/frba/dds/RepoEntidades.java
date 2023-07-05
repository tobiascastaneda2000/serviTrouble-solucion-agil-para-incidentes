package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.RepositorioComunidades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class RepoEntidades {

  public static RepoEntidades instance = new RepoEntidades();
  List<Entidad> entidades = new ArrayList<>();

  public RepoEntidades getInstance() {
    return instance;
  }

  public List<Entidad> getEntidades() {
    return this.entidades;
  }

  public void guardarEntidad(Entidad entidad) {
    entidades.add(entidad);
  }

  public List<Entidad> rankingMayorPromedioCierreIncidentesSemanal() {

    List<Entidad> lista = this.entidades;

    Comparator<Entidad> comparadorPromedioCierreIncidentes = Comparator.comparing(Entidad::promedioDuracionIncidentes);
    lista.sort(comparadorPromedioCierreIncidentes);

    return lista;
  }
  public List<Entidad> rankingMayorCantidadIncidentesSemanal(){
      List<Entidad> lista = this.entidades;

      Comparator<Entidad> comparadorCantidadIncidentes = Comparator.comparing(Entidad::cantidadDeIncidentes);
      lista.sort(comparadorCantidadIncidentes);

      return lista;

  }


}
