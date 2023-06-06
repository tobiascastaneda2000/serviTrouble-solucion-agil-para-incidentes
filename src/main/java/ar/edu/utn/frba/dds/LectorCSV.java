package ar.edu.utn.frba.dds;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV {

    private String csvPath;
    private File file;
    private BufferedReader fileReader;

    String extensioncsv;


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
        List<Entidad> entidades = new ArrayList<>();

        if(file.exists()) {
            try {
                fileReader = new BufferedReader(new FileReader(file));
                String cabecera = fileReader.readLine();
                String leido = fileReader.readLine();
                while (leido != null) {
                    Entidad entidadNueva = generarEntidad(leido);
                    if (entidadNueva != null) entidades.add(entidadNueva);
                    leido = fileReader.readLine();
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
        if(campos.length < 3) return null;
        int id = Integer.parseInt(campos[0]);
        String razonSocial = campos[1];
        String email = campos[2];
        return ( razonSocial.equals("") || email.equals("") ) ? null : new Entidad(id, razonSocial, email);
    }

}
