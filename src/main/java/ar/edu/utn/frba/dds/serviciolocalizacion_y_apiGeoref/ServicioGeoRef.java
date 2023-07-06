package ar.edu.utn.frba.dds.serviciolocalizacion_y_apiGeoref;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class ServicioGeoRef implements ServicioLocalizacion {
    private final String url;
    private ApiGeoRef apiGeoRef;

    public ServicioGeoRef(String nuevaUrl) {
        this.url = nuevaUrl;
        this.apiGeoRef = new Retrofit.Builder()
                .baseUrl(nuevaUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiGeoRef.class);
    }

    public List<Provincia> getProvincias() throws IOException {

        Call<List<Provincia>> requestProvincias = apiGeoRef.getProvincias();
        Response<List<Provincia>> responsePaises = requestProvincias.execute();

        return responsePaises.body();
    }

    public List<Municipio> getMunicipios() throws IOException {

        Call<List<Municipio>> requestMunicipios = apiGeoRef.getMunicipios();
        Response<List<Municipio>> responseMunicipios = requestMunicipios.execute();

        return responseMunicipios.body();
    }

    public List<Departamento> getDepartamentos() throws IOException {

        Call<List<Departamento>> requestDepartamentos = apiGeoRef.getDepartamentos();
        Response<List<Departamento>> responseDepartamentos = requestDepartamentos.execute();

        return responseDepartamentos.body();
    }



}