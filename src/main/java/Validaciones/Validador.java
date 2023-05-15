package Validaciones;

import java.util.*;
import java.util.stream.Collectors;

import Usuario.*;

public class Validador {

  Set<Usuario> usuariosRegistrados;

  public Set<Validacion> validaciones;
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
  public Stack<RuntimeException> validar(String password) {
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


  private Usuario devolverUsuarioCorrespondiente(String username) {
    return usuariosRegistrados.stream().filter(usuario -> usuario.getUsername().equals(username)).
        collect(Collectors.toList()).get(0);
  }


}
