package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.comunidad.Comunidad;
import ar.edu.utn.frba.dds.incidentes.Incidente;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepositorioComunidades extends Repositorio<Comunidad> {
  private static RepositorioComunidades instance = null;
  Set<Comunidad> comunidades = new HashSet<>();

  private RepositorioComunidades() {
    super(Comunidad.class);
  }

  public static RepositorioComunidades getInstance() {
    if (instance == null) {
      instance = new RepositorioComunidades();
    }
    return instance;
  }

  public void notificarIncidente(Incidente incidente) {
    this.getAll().forEach(comunidad -> comunidad.notificarMiembros(incidente));
  }

  public Comunidad contieneIncidente(Incidente incidente) {

    List<Comunidad> comunidades = this.getAll();
    return comunidades.stream().filter(c -> c.contieneIncidente(incidente)).toList().get(0);
  }

  public List<Comunidad> comunidadesFiltradas(List<Comunidad> comunidades,String filtro){

    Set<Comunidad> common = new HashSet<>(comunidades);
    List<Comunidad> comunidadesFiltradas = entityManager().createQuery("from Comunidad where nombre like :filtro")
        .setParameter("filtro",filtro)
        .getResultList();
    common.retainAll(comunidadesFiltradas);
    return common.stream().toList();
  }

  public void clear() {
    this.comunidades = new HashSet<>();
  }
}
