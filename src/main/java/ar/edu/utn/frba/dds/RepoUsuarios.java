package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.Comunidad;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Miembro;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.RepositorioComunidades;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepoUsuarios {
  public static RepoUsuarios instance = new RepoUsuarios();

  public RepoUsuarios getInstance(){
    return instance;
  }
  Set<Usuario> usuarios = new HashSet<>();

  public Set<Usuario> getUsuarios() {
    return this.usuarios;
  }
  public void guardarUsuario(Usuario usuario) {
    usuarios.add(usuario);
  }
  public List<Usuario> interesadoEnEntidad(Entidad entidad){
    return usuarios.stream().filter(u->u.estidadesInteres.contains(entidad)).toList();
  }
}
