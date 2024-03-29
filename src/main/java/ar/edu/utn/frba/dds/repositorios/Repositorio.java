package ar.edu.utn.frba.dds.repositorios;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.HashSet;
import java.util.List;


public class Repositorio<T> implements WithSimplePersistenceUnit {

  private Class<T> type;

  public Repositorio(Class<T> type) {
    this.type = type;
  }

  public List<T> getAll() {
    return entityManager().createQuery("FROM " + type.getSimpleName()).getResultList();
  }

  public T getOne(Long id) {
    return entityManager().find(type, id);
  }

  public void add(Object object) {
    entityManager().persist(object);
  }

  public void update(Object object) {
    entityManager().merge(object);
  }

  public void delete(Object object) {
    entityManager().remove(object);
  }

  public void clean() {
    entityManager().getTransaction().begin();
    CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
    CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
    query.from(type);
    entityManager().createQuery(query).getResultList().forEach(entityManager()::remove);
    entityManager().getTransaction().commit();
  }
}
