package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;
import ar.edu.utn.frba.dds.incidentes.Incidente;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepoUsuarios {
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

  public void sacarIncidentesCerrados(Incidente incidente) {
    List<Usuario> usersConIncidente = usuarios.stream().filter(u->u.contieneIncidentePendiente(incidente)).toList();
    usersConIncidente.forEach(
        u->u.getNotificaciones().remove(u.obtenerNotificacion(incidente))
    );
  }

  public void clear() {
    this.usuarios = new HashSet<>();
  }
}
