package ar.edu.utn.frba.dds.repositorios;

import ar.edu.utn.frba.dds.comunidad_y_usuarios.Comunidad;
import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.incidentes.Incidente;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RepositorioComunidades implements WithSimplePersistenceUnit {
  public static RepositorioComunidades instance = new RepositorioComunidades();
  Set<Comunidad> comunidades = new HashSet<>();

  public List<Comunidad> getComunidades() {

    return entityManager()
        .createQuery("from Comunidad", Comunidad.class)
        .getResultList();
  }

  public void guardarComunidad(Comunidad comunidad) {
    comunidades.add(comunidad);
  }

  public static RepositorioComunidades getInstance() {
    return instance;
  }

  public void notificarIncidente(Incidente incidente) {
    this.getComunidades().forEach(comunidad -> comunidad.notificarMiembros(incidente));
  }


  public List<Comunidad> listarComunidades(){

    return entityManager().createQuery("from Comunidad", Comunidad.class)
        .getResultList();
  }

  public Comunidad buscarPorId(Long id) {

    return entityManager()
        .createQuery("from Comunidad where id = :id", Comunidad.class)
        .setParameter("id", id)
        .getResultList()
        .get(0);
  }

  public void clear() {
    this.comunidades = new HashSet<>();
  }
}
