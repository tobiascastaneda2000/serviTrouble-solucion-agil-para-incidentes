package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.entidades_y_servicios.Entidad;
import ar.edu.utn.frba.dds.lectorCSV_y_entidadesPrestadoras.ArchivoNoExistenteException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LectorCSVEscritura {

  File archivo;
  Logger logger = null;

  public File getArchivo() {
    return archivo;
  }

  public LectorCSVEscritura(String path) {
    File unArchivo = new File(path);

    if (unArchivo.exists()) {
      this.archivo = unArchivo;
    } else {
      throw new ArchivoNoExistenteException();
    }

  }

  public void escribirRankings(List<Entidad> listadoEntidades) {

    if (listadoEntidades == null) {
      throw new EntradaNoPuedeSerNull();
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.archivo))) {
      String cabecera = "id ; razonSocial ; email";
      bw.write(cabecera);
      bw.newLine();
      for (Entidad entidad : listadoEntidades) {
        String linea = entidad.getId() + ";" + entidad.getRazonSocial() + " ; " + entidad.getEmail();
        bw.write(linea);
        bw.newLine();
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error al escribir entidades en archivo", e);
    }
  }

}
