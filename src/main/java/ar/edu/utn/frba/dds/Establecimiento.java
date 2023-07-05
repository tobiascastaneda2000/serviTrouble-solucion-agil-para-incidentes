package ar.edu.utn.frba.dds;

import java.util.ArrayList;
import java.util.List;

public class Establecimiento {
  public List<TipoServicio> getServicio() {
    return servicios;
  }

  List<TipoServicio> servicios = new ArrayList<>();
}
