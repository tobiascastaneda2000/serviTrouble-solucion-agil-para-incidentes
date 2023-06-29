package ar.edu.utn.frba.dds.incidente;

import java.util.Set;

public class RepositorioComunidades {
   public static RepositorioComunidades instance = new RepositorioComunidades();
  Set<Comunidad> comunidades;

  public Set<Comunidad>getComunidades(){
    return this.comunidades;
  }
  public void guardarComunidad(Comunidad comunidad){
    comunidades.add(comunidad);
  }

  public static RepositorioComunidades getInstance(){
    return instance;
  }
}