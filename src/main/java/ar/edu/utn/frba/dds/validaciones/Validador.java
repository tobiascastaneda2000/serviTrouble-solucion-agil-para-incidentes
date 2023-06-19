package ar.edu.utn.frba.dds.validaciones;

import java.util.*;

public class Validador {

  public Set<Validacion> validaciones;
  public Set<RuntimeException> listadoErrores;

  public Validador() {
    this.validaciones = new HashSet<>();
    this.listadoErrores = new HashSet<>();
  }

  //Setter de validaciones
  public void agregarValidacion(Validacion validacion) {
    validaciones.add(validacion);
  }

  //Devuelve lista de excepcciones, y de no a haber, lista vacia
  public Set<RuntimeException> validarContrasenia(String password) {
    realizarTodasLasValidaciones(password);
    return listadoErrores;
  }

  //Hacer todas las validaciones
  public void realizarTodasLasValidaciones(String password) {

    //Realiza verificacion con cada validacion de coleccion
    validaciones.forEach(validacion -> {
          try {
            validacion.esValida(password);
          } catch (RuntimeException exception) {
            //Coloca errores en una pila
            listadoErrores.add(exception);
          }
        }
    );
  }
}
