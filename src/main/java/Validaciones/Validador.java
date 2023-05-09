package Validaciones;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Usuario.*;
public class Validador {

    Set<Usuario> usuariosRegistrados;

    private String username;
    private String password;
    private Set<Validacion> validaciones;
    private Stack<String> errores;

    public Validador(String username, String password) {
        this.username = username;
        this.password = password;
        validaciones = new HashSet<>();
        errores = new Stack<>();
    }

    //Setter de validaciones
    public void subirValidacion(Validacion validacion){
        validaciones.add(validacion);
    }

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
    }

    private void crearUsuario() {
        Usuario usuario = new Usuario(username,password);
        usuariosRegistrados.add(usuario);
    }


    private Usuario devolverUsuarioCorrespondiente(String username) {
        return usuariosRegistrados.stream().filter( usuario -> usuario.getNombre().equals(username) ).
                collect(Collectors.toList()).get(0);
    }



}
