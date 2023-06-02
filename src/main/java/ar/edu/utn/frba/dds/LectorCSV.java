package ar.edu.utn.frba.dds;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV {

    private String csvPath;
    private File file;
    private BufferedReader fileReader;


    public LectorCSV(String csvPath) {
        this.csvPath = csvPath;
        file = new File(this.csvPath);
    }

    public String getCsvPath() {
        return csvPath;
    }

    public List<Entidad> obtenerEntidadesDeCSV() {
        List<Entidad> entidades = new ArrayList<>();
        try {
            fileReader = new BufferedReader(new FileReader(file));
            String leido = fileReader.readLine();
            while (leido != null) {
                String[] campos = leido.split(";");
                entidades.add(generarEntidad(campos));
                leido = fileReader.readLine();
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return entidades;
    }

    public Entidad generarEntidad(String[] campos) {
        int id = Integer.parseInt(campos[0]);
        String razonSocial = campos[1];
        String email = campos[2];

        return new Entidad(id, razonSocial, email);
    }

}
