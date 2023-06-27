package ar.edu.utn.frba.dds.validaciones_password;

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
    validarLongitudMaxima(passwordConEspaciosRecortados);
  }

  public String recortarEspaciosSeguidos(String password){
    return password.replaceAll("\\s+", " ");
  }

  private void validarLongitudMaxima(String password) {
    if (password.length() >= longitudMaximaCaracteres) {
      throw new ContraseniaConMuchosCaracteresException();
    }
  }

  private void validarLongitudMinima(String password) {
    if (password.length() < longitudMinimaCaracteres) {
      throw new ContraseniaConPocosCaracteresException();
    }
  }

}
