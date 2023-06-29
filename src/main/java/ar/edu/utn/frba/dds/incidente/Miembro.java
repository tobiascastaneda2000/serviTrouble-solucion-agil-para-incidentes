package ar.edu.utn.frba.dds.incidente;

import ar.edu.utn.frba.dds.PermisoComunidad;
import ar.edu.utn.frba.dds.TipoServicio;
import ar.edu.utn.frba.dds.usuario.Usuario;

import java.util.List;

public class Miembro {

    public Usuario usuario;
    public PermisoComunidad permisoComunidad;
    public String notiConfinguracion;
    List<String> notificaciones;

    public Miembro(Usuario usuario, PermisoComunidad permisoComunidad, String notiConfinguracion) {
        this.usuario = usuario;
        this.permisoComunidad = permisoComunidad;
        this.notiConfinguracion = notiConfinguracion;
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
        new Notificador().notificar("Nuevo Incidente", this.devolverComunidad());

    }

        public void recibirNotificaiones(String mensaje) {
        notificaciones.add(mensaje);
    }
    //Por ahora falta diferenciar el envio via whatssap y mail
}
