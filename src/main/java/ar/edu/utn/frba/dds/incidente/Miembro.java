package ar.edu.utn.frba.dds.incidente;

import ar.edu.utn.frba.dds.PermisoComunidad;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Miembro {

    public Usuario usuario;
    public PermisoComunidad permisoComunidad;
    public String medioComunicacion; //De momento esta al dope
    List<Notificacion> notificaciones;
    //Debe guardarse notificaciones o solo se envia a traves de una api??

    public Notificador tipoNotificador;

    List<Map<Integer, Integer>> horarios;

    public Miembro(Usuario usuario, PermisoComunidad permisoComunidad,
                   List<Map<Integer, Integer>> horarios, Notificador tipoNotificador) {
        this.usuario = usuario;
        this.permisoComunidad = permisoComunidad;
        this.horarios = horarios;
        this.tipoNotificador = tipoNotificador;
    }

    public Comunidad devolverComunidad() {
        Comunidad comunidad =
            RepositorioComunidades.getInstance().getComunidades()
                .stream().filter(c->c.contieneMiembro(this)).findFirst().orElse(null);
        return comunidad;
    }

    public void informarIncidente(TipoServicio servicio, String observaciones) {

        Comunidad comunidad = this.devolverComunidad();
        comunidad.agregarIncidente(new Incidente(servicio, observaciones));
        tipoNotificador.notificar("Nuevo Incidente", this.devolverComunidad(), servicio);

    }

        public void recibirNotificaiones(Notificacion notificacion) {
        notificaciones.add(notificacion);
    }
    //Por ahora falta diferenciar el envio via whatssap y mail
}
