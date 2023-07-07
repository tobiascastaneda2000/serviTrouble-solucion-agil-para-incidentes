package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.Entidad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LectorCSVEscritura {
  String path;

  public LectorCSVEscritura(String path) {
    this.path = path;
  }

  //SETTERS
  public void setPath(String path) {
    this.path = path;
  }

  public void escribirRankings(List<Entidad> listadoEntidades) {

    if (listadoEntidades == null) {
      throw new EntradaListadoEntidadesVacia();
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.path))) {
      for (Entidad entidad : listadoEntidades) {
        String linea = entidad.getId() + " ; " + entidad.getRazonSocial() + " ; " + entidad.getEmail();
        bw.write(linea);
        bw.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String obtenerNumeroDeLinea(int i) {
    try (BufferedReader br = new BufferedReader(new FileReader(this.path))) {
      int aux = 0;
      String linea = null;
      while (aux < i) {
        linea = br.readLine();
        aux++;
      }
      return linea;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
