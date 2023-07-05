package ar.edu.utn.frba.dds;

import java.util.ArrayList;
import java.util.List;

public class Establecimiento {
  public List<Servicio> getServicio() {
    return servicios;
  }

  List<Servicio> servicios = new ArrayList<>();
}
