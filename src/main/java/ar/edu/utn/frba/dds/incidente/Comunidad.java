package ar.edu.utn.frba.dds.incidente;

import ar.edu.utn.frba.dds.PermisoComunidad;
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

    public Set<Miembro> getMiembros() {
        return miembros;
    }


    public void registrarMiembro(Usuario usuario, String notiConfinguracion) {
        miembros.add(new Miembro(usuario, PermisoComunidad.USUARIO_COMUNIDAD, notiConfinguracion));
    }
    //ACLARACION: siempre que se registra un miembro desde comunidado su permiso es USUARIO_COMUNIDAD

    boolean contieneMiembro(Miembro miembro) {
        return miembros.contains(miembro);
    }

    public void agregarIncidente(Incidente incidente) {
        incidentes.add(incidente);
    }

    public void cerrarIncidente(Incidente incidente){
        incidente.cerrar();
        new Notificador().notificar("Resuelto", this);
        //Debe tambien eliminar el incidente???
    }
}