package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.usuario.Usuario;

public class Miembro {

    public Usuario usuario;
    public PermisoComunidad permisoComunidad;

    public String notiConfinguracion;

    public Miembro(Usuario usuario, PermisoComunidad permisoComunidad, String notiConfinguracion) {
        this.usuario = usuario;
        this.permisoComunidad = permisoComunidad;
        this.notiConfinguracion = notiConfinguracion;
    }

    //TODO agregar comportamiento
}
