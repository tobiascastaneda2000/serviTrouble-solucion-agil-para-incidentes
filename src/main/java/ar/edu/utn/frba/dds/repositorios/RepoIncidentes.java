package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.incidentes.Incidente;
import java.util.HashSet;
import java.util.Set;

public class RepoIncidentes extends Repositorio<Incidente>{

  private static RepoIncidentes instance = null;
  Set<Incidente> incidentes = new HashSet<>();
  private RepoIncidentes() {
    super(Incidente.class);
  }

  public static RepoIncidentes getInstance() {
    if (instance == null) {
      instance = new RepoIncidentes();
    }
    return instance;
  }

}
