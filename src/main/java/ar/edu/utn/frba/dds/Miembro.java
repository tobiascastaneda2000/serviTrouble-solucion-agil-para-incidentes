package ar.edu.utn.frba.dds;


import java.util.List;
import java.util.Map;

public class Miembro {

    public Usuario getUsuario() {
        return usuario;
    }

    public Usuario usuario;
    public PermisoComunidad permisoComunidad;

    public Notificador tipoNotificador;
    //public String medioComunicacion; De momento esta al dope
    //List<Notificacion> notificaciones;
    //Debe guardarse notificaciones o solo se envia a traves de una api??

    //List<Map<Integer, Integer>> horarios;

  public Miembro(Usuario usuario, PermisoComunidad permisoComunidad,
                   //List<Map<Integer, Integer>> horarios,
                   Notificador tipoNotificador) {
        this.usuario = usuario;
        this.permisoComunidad = permisoComunidad;
        //this.horarios = horarios;
        this.tipoNotificador = tipoNotificador;
    }

    public Comunidad devolverComunidad() {
        Comunidad comunidad =
            RepositorioComunidades.getInstance().getComunidades()
                .stream().filter(c->c.contieneMiembro(this)).findFirst().orElse(null);
        return comunidad;
    }

    public void informarIncidente(TipoServicio servicio, String observaciones) {

        List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
        comunidades.forEach(c->c.agregarIncidente(new Incidente(servicio, observaciones)));
        comunidades.forEach(c->c.notificar("Nuevo Incidente",servicio));
    }

    public void cerrarIncidente(Incidente incidente){
        List<Comunidad> comunidades = usuario.comunidadesPertenecientes();
        comunidades.forEach(c->c.cerrarIncidente(incidente));
    }

}
