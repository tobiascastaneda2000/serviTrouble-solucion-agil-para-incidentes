package ar.edu.utn.frba.dds.lectorCSV_y_entidadesPrestadoras;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.comunidad_y_usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EntidadPropietaria {
  Usuario usuarioEncargado;
  List<Entidad> entidades;

  public EntidadPropietaria(Usuario usuarioEncargado) {
    this.entidades = new ArrayList<>();
    this.usuarioEncargado = usuarioEncargado;
  }

  public void recopilarProblematicas() {

  }
}
