package ar.edu.utn.frba.dds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class RepoEntidades {

  public static RepoEntidades instance = new RepoEntidades();
  List<Entidad> entidades = new ArrayList<>();

  public RepoEntidades getInstance() {
    return instance;
  }

  public List<Entidad> getEntidades() {
    return this.entidades;
  }

  public void guardarEntidad(Entidad entidad) {
    entidades.add(entidad);
  }

  public List<Entidad> rankingMayorPromedioCierreIncidentesSemanal(){

    List<Entidad> lista = this.entidades;/*
    lista.sort(new Comparator<Entidad>() {
      @Override
      public int compare(Entidad p1, Entidad p2) {
        return p1.getAge() - p2.getAge();
      }
    });*/
    return this.entidades;
  }

  public List<Entidad> rankingMayorCantidadIncidentesSemanal(){
    return this.entidades;
  }


}
