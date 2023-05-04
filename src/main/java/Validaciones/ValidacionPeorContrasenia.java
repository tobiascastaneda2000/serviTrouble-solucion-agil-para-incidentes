package Validaciones;

import java.io.*;
import java.util.List;

public class ValidacionPeorContrasenia implements Validacion{
  private BufferedReader archivoPeoresContrasenias;
  private List<String> peoresContrasenias;

  public ValidacionPeorContrasenia(String rutaArchivoPeoresContrasenias) {
    try{
      this.archivoPeoresContrasenias = new BufferedReader(new FileReader( new File(rutaArchivoPeoresContrasenias)));
      this.peoresContrasenias = archivoPeoresContrasenias.lines().toList();
    } catch (IOException e) {
      throw new IllegalArgumentException("No se pudo cargar el listado de contraseñas prohibidas.");
    }
  }

  @Override
  public Boolean esValida(String password) {
    if(peoresContrasenias.contains(password)) {
      throw new IllegalArgumentException(
          "La contrasenia no puede estar dentro del Top10000 de peores contrasenias."
      );
    }
    return false;
  }
}
