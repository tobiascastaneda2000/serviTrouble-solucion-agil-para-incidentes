package ar.edu.utn.frba.dds.validaciones;

import java.io.File;
import java.util.*;

import ar.edu.utn.frba.dds.usuario.*;

public class Validador {

  public Set<Validacion> validaciones;
  private File archivo;
  public List<String> denyList;
  //private Stack<String> errores; //Atributo de anterior version validar() descomentar de querer utilizarse
  public Stack<RuntimeException> errores2;

  public Validador() {
    validaciones = new HashSet<>();
    //errores = new Stack<>(); //Atributo de anterior version validar() descomentar de querer utilizarse
    this.errores2 = new Stack<>();
  }

  //Setter de validaciones
  public void agregarValidacion(Validacion validacion) {
    validaciones.add(validacion);
  }

  //Devuelve lista de excepcciones, y de no a haber, lista vacia
  public Stack<RuntimeException> validarContrasenia(String password) {
    realizarTodasLasValidaciones(password);
    return errores2;
  }

  //Hacer todas las validaciones
  public void realizarTodasLasValidaciones(String password) {

      //Realiza verificacion con cada validacion de coleccion
      validaciones.forEach(validacion -> {
        try{
          validacion.esValida(password);}
          catch (RuntimeException exception) {
            //Coloca errores en una pila
            errores2.push(exception);
          }
        }
      );
    }
}
