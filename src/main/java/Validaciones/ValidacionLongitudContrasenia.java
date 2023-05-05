package Validaciones;

public class ValidacionLongitudContrasenia implements Validacion{

  private int longitudMinimaCaracteres = 8;
  private int longitudMaximaCaracteres = 64;

  public ValidacionLongitudContrasenia(){}

  public int getLongitudMinimaCaracteres() {
    return longitudMinimaCaracteres;
  }

  @Override
  public Boolean esValida(String password) {
    if(password.length() < longitudMinimaCaracteres) {
      throw new IllegalArgumentException(
          "La contrasenia debe tener al menos "+longitudMinimaCaracteres+" caracteres."
      );
    }

    if(password.length() >= longitudMaximaCaracteres){
      throw new IllegalArgumentException(
              "La longitud de la contraseña debe ser menor a los "+ longitudMaximaCaracteres+" caracteres."
      );
    }
    return false;
  }

}
