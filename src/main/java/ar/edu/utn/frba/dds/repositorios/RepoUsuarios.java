package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepoUsuarios implements WithSimplePersistenceUnit {
  public static RepoUsuarios instance = new RepoUsuarios();

  public static RepoUsuarios getInstance(){
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
    return usuarios.stream().filter(u->u.getEntidadesInteres().contains(entidad)).toList();
  }

  public void clear() {
    this.usuarios = new HashSet<>();
  }

  public Usuario buscarPorUsuarioYContrasenia(String nombre, String contrasenia) {


    return entityManager()
        .createQuery("from Usuario where usuario = :nombre and contrasenia = :contrasenia", Usuario.class)
        .setParameter("nombre", nombre)
        .setParameter("contrasenia",contrasenia)
        .getResultList()
        .get(0);
  }

  public List<Usuario> buscarPorUsuario(String nombre) {

    return entityManager()
        .createQuery("from Usuario where usuario = :nombre", Usuario.class)
        .setParameter("nombre", nombre)
        .getResultList();
  }


  public Usuario buscarUsuarioPorID(Long userid){
    return entityManager()
        .createQuery("from Usuario where id = :id", Usuario.class)
        .setParameter("id",userid)
        .getResultList()
        .get(0);
  }

  public List<Usuario> listarUsuarios(){
    return entityManager()
        .createQuery("from Usuario", Usuario.class)
        .getResultList();
  }
}
