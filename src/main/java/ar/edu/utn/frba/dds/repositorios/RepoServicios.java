package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.entidades.Servicio;
import java.util.HashSet;
import java.util.Set;

public class RepoServicios extends Repositorio<Servicio>{

  private static RepoServicios instance = null;
  Set<Servicio> servicios = new HashSet<>();
  private RepoServicios() {
    super(Servicio.class);
  }

  public static RepoServicios getInstance() {
    if (instance == null) {
      instance = new RepoServicios();
    }
    return instance;
  }

}
