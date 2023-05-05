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
    private int intentos;

    public Validador(String username, String password) {
        this.username = username;
        this.password = password;
        this.intentos = 3;
        validaciones = new HashSet<>();
        errores = new Stack<>();
    }
    //Setter de validaciones
    public void subirValidacion(Validacion validacion){
        validaciones.add(validacion);
    }
    //Setter
    public void setUsername(String username){
        this.username = username;
    }
    //Setter
    public void setPassword(String password){
        this.password = password;
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
           Usuario usuario = new Usuario(username,password);
           usuariosRegistrados.add(usuario);
        }else{
            while(!errores.empty()){
                String mensaje = errores.pop();
                System.out.println(mensaje);
            }
        }
    }

    public Usuario iniciarSesion(){

        if(this.intentos>0){
            Usuario usuario = this.devolverUsuarioCorrespondiente(username);
            if(usuario != null){
                if(usuario.getContrasenia().equals(password)){
                    return usuario;
                }
                else {
                    this.intentos--;
                    throw new RuntimeException("LA contraseña es incorrecta");
                }
            }
            else{
                throw new RuntimeException("No existe este usuario");
            }

        }
        else {
            throw new RuntimeException("No hay mas intentos");
        }

    }


    private Usuario devolverUsuarioCorrespondiente(String username) {
        return usuariosRegistrados.stream().filter( usuario -> usuario.getNombre().equals(username) ).
                collect(Collectors.toList()).get(0);
    }



}
