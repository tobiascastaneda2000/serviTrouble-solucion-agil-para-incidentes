package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.Entidad;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LectorDeRankings {
  public void escribirRankings(List<Entidad> listadoEntidades, String rutaArchivo) {

    String string = "src/main/java/ar/edu/utn/frba/dds/rankings";
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
      for (Entidad entidad : listadoEntidades) {
        String linea = entidad.getId() + " ; " + entidad.getEmail() + " ; " + entidad.getRazonSocial() + entidad.getId();
        bw.write(linea);
        bw.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
