package Validaciones;

public class ValidacionLongitudContrasenia implements Validacion{

  private int longitudMinimaCaracteres;

  public ValidacionLongitudContrasenia(int longitudMinimaCaracteres) {
    this.longitudMinimaCaracteres = longitudMinimaCaracteres;
  }

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
    return false;
  }

}
