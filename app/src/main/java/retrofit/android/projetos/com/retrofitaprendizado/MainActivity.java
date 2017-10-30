package retrofit.android.projetos.com.retrofitaprendizado;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import retrofit.android.projetos.com.retrofitaprendizado.models.Course;
import retrofit.android.projetos.com.retrofitaprendizado.models.Instructor;
import retrofit.android.projetos.com.retrofitaprendizado.models.UdacityCatalog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
private static final String TAG = "Joao";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//criacao do retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UdacityService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UdacityService service = retrofit.create(UdacityService.class);

        Call<UdacityCatalog> requestCatalog  = service.listCatalog();

        requestCatalog.enqueue(new Callback<UdacityCatalog>() {

            @Override
            public void onResponse(Call<UdacityCatalog> call, Response<UdacityCatalog> response) {
                if (!response.isSuccessful()){
                    Log.i(TAG, "Erro"+response.code());
                }else {
                    //requisicao sucesso
                    UdacityCatalog catalogo = response.body();
                    for (Course c : catalogo.courses
                            ) {
                        Log.i(TAG, String.format("%s : %s", c.title, c.subtitle));
                        for (Instructor i: c.instructors
                             ) {Log.i(TAG, i.name.toString());

                        }
                        Log.i(TAG,"-----------------------");
                    }

                }
            }

            @Override
            public void onFailure(Call<UdacityCatalog> call, Throwable t) {
                Log.e(TAG, "Erro"+t.getMessage());

            }
        });


    }
}
