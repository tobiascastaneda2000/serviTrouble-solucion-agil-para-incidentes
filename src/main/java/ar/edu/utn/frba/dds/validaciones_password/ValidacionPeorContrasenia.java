package ar.edu.utn.frba.dds.validaciones_password;

import java.io.*;
import java.util.List;

public class ValidacionPeorContrasenia implements Validacion {

  private final List<String> peoresContrasenias;

  public ValidacionPeorContrasenia() {
    File archivo = new File("src/main/java/ar/edu/utn/frba/dds/validaciones_password/password-list-top-10000.txt");
    try {
      BufferedReader archivoPeoresContrasenias = new BufferedReader(new FileReader(archivo));
      this.peoresContrasenias = archivoPeoresContrasenias.lines().toList();
    } catch (IOException e) {
      throw new IllegalArgumentException("No se pudo cargar el listado de contraseñas prohibidas.");
    }
  }

  @Override
  public void esValida(String password) {
    if (peoresContrasenias.contains(password)) {
      throw new DebilPasswordException();
    }
  }
}
