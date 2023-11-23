package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.entidades.Entidad;
import ar.edu.utn.frba.dds.comunidad.Usuario;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepoUsuarios extends Repositorio<Usuario> {

  private static RepoUsuarios instance = null;

  private RepoUsuarios() {
    super(Usuario.class);
  }

  public static RepoUsuarios getInstance() {
    if (instance == null) {
      instance = new RepoUsuarios();
    }
    return instance;
  }


  public Set<Usuario> getUsuariosComoSet() {
    return new HashSet<>(this.getAll());
  }

  public List<Usuario> interesadoEnEntidad(Entidad entidad) {
    return getAll().stream().filter(u -> u.getEntidadesInteres().contains(entidad)).toList();
  }

  public Usuario buscarPorUsuarioYContrasenia(String nombre, String contrasenia) {
    return entityManager()
        .createQuery("from Usuario where usuario = :nombre and contrasenia = :contrasenia", Usuario.class)
        .setParameter("nombre", nombre)
        .setParameter("contrasenia", contrasenia)
        .getResultList()
        .get(0);
  }

  public List<Usuario> buscarPorUsuario(String nombre) {
    return entityManager()
        .createQuery("from Usuario where usuario = :nombre", Usuario.class)
        .setParameter("nombre", nombre)
        .getResultList();
  }


  //getOne
  public Usuario buscarUsuarioPorID(Long userid) {
    return entityManager()
        .createQuery("from Usuario where id = :id", Usuario.class)
        .setParameter("id", userid)
        .getResultList()
        .get(0);
  }

  //getAll
  public List<Usuario> listarUsuarios() {
    return entityManager()
        .createQuery("from Usuario", Usuario.class)
        .getResultList();
  }
}
