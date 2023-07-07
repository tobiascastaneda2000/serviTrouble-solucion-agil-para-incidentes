package ar.edu.utn.frba.dds.rankings;

import ar.edu.utn.frba.dds.Entidad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LectorCSVEscritura {
  public void escribirRankings(List<Entidad> listadoEntidades) {

    if(listadoEntidades == null){
      throw new LaListaEstaVaciaException();
    }

    String string = "src/main/java/ar/edu/utn/frba/dds/rankings/rankings-entidades.txt";
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(string))) {
      for (Entidad entidad : listadoEntidades) {
        String linea = entidad.getId() + " ; " + entidad.getRazonSocial() + " ; " + entidad.getEmail() ;
        bw.write(linea);
        bw.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String obtenerPrimeraLinea(String rutaArchivo, int i) {
    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
      int aux = 0;
      String linea = null;
      while(aux<i){
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
