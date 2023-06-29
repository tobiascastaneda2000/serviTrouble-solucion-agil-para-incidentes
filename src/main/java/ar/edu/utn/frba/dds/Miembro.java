package ar.edu.utn.frba.dds;


public class Miembro {

    public Usuario usuario;
    public PermisoComunidad permisoComunidad;
    //public String medioComunicacion; De momento esta al dope
    //List<Notificacion> notificaciones;
    //Debe guardarse notificaciones o solo se envia a traves de una api??

    //List<Map<Integer, Integer>> horarios;

 /*   public Miembro(Usuario usuario, PermisoComunidad permisoComunidad,
                   List<Map<Integer, Integer>> horarios, Notificador tipoNotificador) {
        this.usuario = usuario;
        this.permisoComunidad = permisoComunidad;
        this.horarios = horarios;
        this.tipoNotificador = tipoNotificador;
    }*/

    public Comunidad devolverComunidad() {
        Comunidad comunidad =
            RepositorioComunidades.getInstance().getComunidades()
                .stream().filter(c->c.contieneMiembro(this)).findFirst().orElse(null);
        return comunidad;
    }

}
