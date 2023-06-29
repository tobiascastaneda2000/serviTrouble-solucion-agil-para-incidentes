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

    List<Incidente> incidentesAResolver;
    List<Incidente> incidentesResueltos;

    public Set<Miembro> getMiembros() {
        return miembros;
    }

    public void registrarMiembro(Usuario usuario, String medioComunicacion) {
        miembros.add(new Miembro(usuario, PermisoComunidad.USUARIO_COMUNIDAD, medioComunicacion));
    }
    //ACLARACION: siempre que se registra un miembro desde comunidado su permiso es USUARIO_COMUNIDAD

    boolean contieneMiembro(Miembro miembro) {
        return miembros.contains(miembro);
    }

    public void agregarIncidente(Incidente incidente) {
        incidentesAResolver.add(incidente);
    }

    public void cerrarIncidente(Incidente incidente){
        incidente.cerrar();
        incidentesAResolver.remove(incidente);
        incidentesResueltos.add(incidente);
    }
}