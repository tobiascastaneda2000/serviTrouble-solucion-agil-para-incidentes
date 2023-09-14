package ar.edu.utn.frba.dds.repositorios.daos;

import java.util.List;

public interface DAO<T> { // Data Access Object

  List<T> all();

  T get(int id);

  void add(Object objeto);

  void delete(Object objeto);

  void update(Object objeto);

  void clean();
}
