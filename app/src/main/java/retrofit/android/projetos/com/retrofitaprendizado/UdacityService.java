package retrofit.android.projetos.com.retrofitaprendizado;

import retrofit.android.projetos.com.retrofitaprendizado.models.UdacityCatalog;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by João Gabriel on 29/10/2017.
 */

public interface UdacityService {
    public static final String BASE_URL = "https://www.udacity.com/public-api/v0/";

    @GET("courses")
    Call<UdacityCatalog> listCatalog();
}
