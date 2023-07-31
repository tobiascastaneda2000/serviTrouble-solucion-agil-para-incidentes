package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.comunidad_e_incidentes.Comunidad;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Incidente;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.Miembro;
import ar.edu.utn.frba.dds.comunidad_e_incidentes.RepositorioComunidades;
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
    return usuarios.stream().filter(u->u.estidadesInteres.contains(entidad)).toList();
  }

  public void sacarIncidentesCerrados(Incidente incidente) {
    List<Usuario> usersConIncidente = usuarios.stream().filter(u->u.contieneIncidentePendiente(incidente)).toList();
    usersConIncidente.forEach(
        u->u.notificacionesPendientes.remove(u.obtenerNotificacion(incidente))
    );
  }
}
