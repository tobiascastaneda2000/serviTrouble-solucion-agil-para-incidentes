package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.entidades.Establecimiento;
import ar.edu.utn.frba.dds.entidades.Servicio;
import java.util.HashSet;
import java.util.Set;

public class RepoEstablecimientos extends Repositorio<Establecimiento>{

  private static RepoEstablecimientos instance = null;
  Set<Establecimiento> establecimientos = new HashSet<>();
  private RepoEstablecimientos() {
    super(Establecimiento.class);
  }

  public static RepoEstablecimientos getInstance() {
    if (instance == null) {
      instance = new RepoEstablecimientos();
    }
    return instance;
  }

}
