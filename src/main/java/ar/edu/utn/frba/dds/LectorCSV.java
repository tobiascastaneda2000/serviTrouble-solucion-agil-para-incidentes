package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.lectorCSV_y_entidadesPrestadoras.ArchivoNoExistenteException;
import ar.edu.utn.frba.dds.lectorCSV_y_entidadesPrestadoras.CampoDeEntidadVacioException;
import ar.edu.utn.frba.dds.lectorCSV_y_entidadesPrestadoras.EntidadIncompletaException;
import ar.edu.utn.frba.dds.lectorCSV_y_entidadesPrestadoras.EntidadYaCargadaException;
import ar.edu.utn.frba.dds.lectorCSV_y_entidadesPrestadoras.PathIncorrectoException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV {

    private String csvPath;
    private File file;
    private BufferedReader fileReader;

    String extensioncsv;
    List<Entidad> entidades = new ArrayList<>();


    public LectorCSV(String csvPath) {

        if(csvPath.length() >= 3) {
            this.extensioncsv = csvPath.substring(csvPath.length() - 4);
            if (extensioncsv.equals(".csv")) {
                this.csvPath = csvPath;
                file = new File(this.csvPath);
            } else {
                throw new PathIncorrectoException();
            }
        }
        else{
            throw new RuntimeException();
        }

    }

    public String getCsvPath() {
        return csvPath;
    }

    public List<Entidad> obtenerEntidadesDeCSV() {


        if(file.exists()) {
            try {
                fileReader = new BufferedReader(new FileReader(file));
                String cabecera = fileReader.readLine();
                String leido = fileReader.readLine();
                while (leido != null) {
                    Entidad entidadNueva = generarEntidad(leido);
                    if(entidades.stream().anyMatch(e->
                        e.getId() == entidadNueva.getId() &&
                        e.getEmail().equals(entidadNueva.getEmail()) &&
                        e.getRazonSocial().equals(entidadNueva.getRazonSocial())
                    )){
                        throw new EntidadYaCargadaException();
                    }
                    else{
                        entidades.add(entidadNueva);
                        leido = fileReader.readLine();
                    }
                }
                fileReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return entidades;
        }
        else{
            throw new ArchivoNoExistenteException();
        }
    }

    public Entidad generarEntidad(String dataLeida) {
        String[] campos = dataLeida.split(";");
        if(campos.length < 3){
            throw new EntidadIncompletaException();
        }
        int id = Integer.parseInt(campos[0]);
        String razonSocial = campos[1];
        String email = campos[2];
        if( razonSocial.equals("") || email.equals("") ){
            throw new CampoDeEntidadVacioException();
        }
        return new Entidad(id, razonSocial, email);
    }



}
