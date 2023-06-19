package ar.edu.utn.frba.dds.validaciones;

import java.util.*;

public class Validador {

  public Set<Validacion> validaciones;
  public Stack<RuntimeException> listadoErrores;

  public Validador() {
    this.validaciones = new HashSet<>();
    this.listadoErrores = new Stack<>();
  }

  public void agregarValidacion(Validacion validacion) {
    validaciones.add(validacion);
  }

  //Devuelve lista de excepciones
  public Stack<RuntimeException> validarContrasenia(String password) {
    realizarTodasLasValidaciones(password);
    return listadoErrores;
  }

  public void realizarTodasLasValidaciones(String password) {

    //Realiza verificacion con cada validacion de coleccion y lo añade al set
    validaciones.forEach(validacion -> {
          try {
            validacion.esValida(password);
          } catch (RuntimeException exception) {
            listadoErrores.add(exception);
          }
        }
    );
  }
}
