package ar.edu.utn.frba.dds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Comunidad {

    public Set<Miembro> miembros;

    public Comunidad() {
        this.miembros = new HashSet<>();
    }

    public List<Incidente> getIncidentesAResolver() {
        return incidentesAResolver;
    }

    public List<Incidente> getIncidentesResueltos() {
        return incidentesResueltos;
    }

    List<Incidente> incidentesAResolver  = new ArrayList<>();
    List<Incidente> incidentesResueltos = new ArrayList<>();

    public Set<Miembro> getMiembros() {
        return miembros;
    }

    public void registrarMiembro(Usuario usuario,
                                 //List<Map<Integer, Integer>> horarios,
                                 Notificador noti) {
        miembros.add(new Miembro(usuario, PermisoComunidad.USUARIO_COMUNIDAD,
            //horarios,
            noti));
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

    public void notificar(String texto, TipoServicio servicio) {
       miembros.stream().filter(m->m.usuario.getServiciosDeInteres().contains(servicio))
           .forEach(m->m.tipoNotificador.notificar(texto,m.usuario));
    }

    public boolean contieneUsuario(Usuario usuario) {
        return miembros.stream().map(Miembro::getUsuario).toList().contains(usuario);
    }


}