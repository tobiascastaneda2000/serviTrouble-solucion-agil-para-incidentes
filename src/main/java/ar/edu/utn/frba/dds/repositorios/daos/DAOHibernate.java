package ar.edu.utn.frba.dds.repositorios.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DAOHibernate<T> implements DAO<T>{

  @PersistenceContext
  private EntityManager entityManager;
  private Class<T> type;

  public DAOHibernate(Class<T> type){
    this.type = type;
  }

  @Override
  public List<T> all() {
    return entityManager.createQuery("FROM " + type.getSimpleName()).getResultList();
  }

  @Override
  public T get(int id) {
    return entityManager.find(type, id);
  }

  @Override
  public void add(Object object) {
    entityManager.getTransaction().begin();
    entityManager.persist(object);
    entityManager.getTransaction().commit();
  }

  @Override
  public void update(Object object) {
    entityManager.getTransaction().begin();
    entityManager.merge(object);
    entityManager.getTransaction().commit();
  }

  @Override
  public void delete(Object object) {
    entityManager.getTransaction().begin();
    entityManager.remove(object);
    entityManager.getTransaction().commit();
  }

  @Override
  public void clean() {
    entityManager.getTransaction().begin();
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
    query.from(type);
    entityManager.createQuery(query).getResultList().forEach(entityManager::remove);
    entityManager.getTransaction().commit();
  }
}
