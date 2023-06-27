package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.usuario.Usuario;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Comunidad {
    public Set<Miembro> miembros;

    public Comunidad() {
        this.miembros = new HashSet<>();
    }

    List<Incidente> incidentes;

    public void registrarMiembro(Usuario usuario, String notiConfinguracion) {
        miembros.add(new Miembro(usuario, PermisoComunidad.USUARIO_COMUNIDAD, notiConfinguracion));
    }

    //ACLARACION: siempre que se registra un miembro desde comunidado su permiso es USUARIO_COMUNIDAD
}
