package ar.edu.utn.frba.dds.lectorCSV_y_entidadesPrestadoras;

import ar.edu.utn.frba.dds.Entidad;
import ar.edu.utn.frba.dds.usuario.Usuario;

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
