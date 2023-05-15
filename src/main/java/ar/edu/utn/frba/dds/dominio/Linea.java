package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.dominio.Estacion;

import java.util.List;

public class Linea {
    String nombre;
    Estacion estacionInicial;
    Estacion estacionFinal;
    List<Estacion> estacionesIntermedias;

}
