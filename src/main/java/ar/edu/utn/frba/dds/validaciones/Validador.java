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
  public void subirValidacion(Validacion validacion) {
    validaciones.add(validacion);
  }

  //Devuelve lista de excepcciones, y de no a haber, lista vacia
  public Stack<RuntimeException> validarContrasenia(String password) {
    realizarTodasLasValidaciones(password);
    return errores2;
  }

  //Hacer todas las validaciones
  public void realizarTodasLasValidaciones(String password) {
    try {
      //Realiza verificacion con cada validacion de coleccion
      validaciones.forEach(validacion -> validacion.esValida(password));
    } catch (RuntimeException exception) {
      //Coloca errores en una pila
      errores2.push(exception);
    }
  }

  //METODO VALIDAR ANTERIOR, lo deje comentado por las dudas
/*
    public Boolean validar(){
        return validaciones.stream().allMatch(
            validacion -> {
                try{
                    return validacion.esValida(password);
                }catch(IllegalArgumentException exception){
                    errores.push(exception.getMessage());
                    return false;
                }
            }
        );
    }
    
    public void registrar(){
        if( this.validar() ){
           this.crearUsuario();
        }else{
            while(!errores.empty()){
                String mensaje = errores.pop();
                System.out.println(mensaje);
            }
        }
    }*/


}
