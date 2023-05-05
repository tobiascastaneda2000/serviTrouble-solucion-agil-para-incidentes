package Validaciones;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import Usuario.*;
public class Validador {

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
    
    public Usuario iniciar_sesion(){
        if( this.intentos>0 && this.validar() ){           
           return new Usuario(username,password);
        }else{
            while(!errores.empty()){
                String mensaje = errores.pop();
                System.out.println(mensaje);
            }
            this.intentos--;
            return null;
        }
    }

}
