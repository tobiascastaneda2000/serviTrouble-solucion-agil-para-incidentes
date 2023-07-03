package ar.edu.utn.frba.dds;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RepositorioComunidades {
   public static RepositorioComunidades instance = new RepositorioComunidades();
  Set<Comunidad> comunidades = new HashSet<>();

  public Set<Comunidad>getComunidades(){
    return this.comunidades;
  }
  public void guardarComunidad(Comunidad comunidad){
    comunidades.add(comunidad);
  }

  public static RepositorioComunidades getInstance(){
    return instance;
  }

  public Comunidad devolverComunidad(Miembro miembro) {
    return comunidades.stream().filter(c -> c.contieneMiembro(miembro)).toList().get(0);
  }
}
