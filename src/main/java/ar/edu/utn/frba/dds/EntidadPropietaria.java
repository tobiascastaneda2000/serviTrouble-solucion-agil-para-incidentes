package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.usuario.Usuario;
import ar.edu.utn.frba.dds.Entidad;

import java.util.ArrayList;
import java.util.List;

public class EntidadPropietaria {
    Usuario usuarioEncargado;
    List<Entidad> entidades;

    public EntidadPropietaria(Usuario usuarioEncargado){
        this.entidades = new ArrayList<>();
        this.usuarioEncargado = usuarioEncargado;
    }
    public void recopilarProblematicas() {

    }
}
