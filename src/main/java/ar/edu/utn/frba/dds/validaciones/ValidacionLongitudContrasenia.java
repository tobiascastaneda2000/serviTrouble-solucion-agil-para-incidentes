package ar.edu.utn.frba.dds.validaciones;

public class ValidacionLongitudContrasenia implements Validacion {

  private int longitudMinimaCaracteres = 8;
  private int longitudMaximaCaracteres = 64;

  public ValidacionLongitudContrasenia() {
  }

  public int getLongitudMinimaCaracteres() {
    return longitudMinimaCaracteres;
  }

  @Override
  public void esValida(String password) {
    /*
    Para arreglar los espacios de password tenemos dos opcciones:

   1) Crear funcion que se pasa el password y devuelve un String con la password arreglada
   2) Crear funcion que devuelva, en caso que tenga mas de un espacio seguido, un entero
   con la cantidad de espacios qu ehay que restarle a su logitud
     */
    validarLongitudMinima(password);
    validarLongitudMinima(devolverCadenaSinEspacios(password));
    validarLongitudMaxima(password);
  }

  String devolverCadenaSinEspacios(String password) {
    String cadenaResultante = "";
    char caracterAnterior = ' ';
    for (int i = 0; i < password.length(); i++) {
      char caracterActual = password.charAt(i);
      if (caracterActual != ' ') {
        cadenaResultante += caracterActual;
        }
      else {
        if(caracterAnterior != ' ' ){
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
          "La longitud de la contraseña debe ser menor a los " + longitudMaximaCaracteres + " caracteres."
      );
    }
  }

  private void validarLongitudMinima(String password) {
    if (password.length() < longitudMinimaCaracteres) {
      throw new ContraseniaConPocosCaracteresException(
          "La contrasenia debe tener al menos " + longitudMinimaCaracteres + " caracteres."
      );
    }
  }

}
