package ar.edu.utn.frba.dds.validaciones;

public class ValidacionLongitudContrasenia implements Validacion {

  private final int longitudMinimaCaracteres = 8;
  private final int longitudMaximaCaracteres = 64;

  public ValidacionLongitudContrasenia() {
  }

  public int getLongitudMinimaCaracteres() {
    return longitudMinimaCaracteres;
  }

  public int getLongitudMaximaCaracteres() {
    return longitudMaximaCaracteres;
  }

  @Override
  public void esValida(String password) {

    String passwordConEspaciosRecortados = recortarEspaciosSeguidos(password);
    validarLongitudMinima(passwordConEspaciosRecortados);
    validarLongitudMaxima(password);
  }

  public String recortarEspaciosSeguidos(String password) {
    String cadenaResultante = "";
    char caracterAnterior = ' ';
    for (int i = 0; i < password.length(); i++) {
      char caracterActual = password.charAt(i);
      if (caracterActual != ' ') {
        cadenaResultante += caracterActual;
      } else {
        if (caracterAnterior != ' ') {
          cadenaResultante += caracterActual;

        }
      }

      caracterAnterior = caracterActual;

    }

    return cadenaResultante;
  }

  private void validarLongitudMaxima(String password) {
    if (password.length() >= longitudMaximaCaracteres) {
      throw new ContraseniaConMuchosCaracteresException(
          "La longitud de la contraseña debe ser menor a los " + getLongitudMaximaCaracteres() + " caracteres."
      );
    }
  }

  private void validarLongitudMinima(String password) {
    if (password.length() < longitudMinimaCaracteres) {
      throw new ContraseniaConPocosCaracteresException(
          "La contrasenia debe tener al menos " + getLongitudMinimaCaracteres() + " caracteres."
      );
    }
  }

}
