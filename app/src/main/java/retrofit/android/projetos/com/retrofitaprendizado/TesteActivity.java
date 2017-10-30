package retrofit.android.projetos.com.retrofitaprendizado;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.android.projetos.com.retrofitaprendizado.models.Course;
import retrofit.android.projetos.com.retrofitaprendizado.models.Instructor;
import retrofit.android.projetos.com.retrofitaprendizado.models.UdacityCatalog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TesteActivity extends AppCompatActivity{

    private static final String TAG = "Joao";
    private ListView listaCursos;
    private ArrayList<String>titulos = new ArrayList<>();
    private ArrayList<String>subtitulos = new ArrayList<>();
    private boolean carregou = false;
    //private String[] titulos = {"a","b", "c"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        //criacao do retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UdacityService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UdacityService service = retrofit.create(UdacityService.class);

        final Call<UdacityCatalog> requestCatalog  = service.listCatalog();

        requestCatalog.enqueue(new Callback<UdacityCatalog>() {


            @Override
            public void onResponse(Call<UdacityCatalog> call, Response<UdacityCatalog> response) {

                if (!response.isSuccessful()){
                    Log.i(TAG, "Erro"+response.code());
                }else {

                    //requisicao sucesso
                    UdacityCatalog catalog = response.body();

                    for (Course c : catalog.courses) {
                        Log.i(TAG, String.format("%s : %s", c.title, c.subtitle));
                        titulos.add(c.title.toString());
                        subtitulos.add(c.subtitle.toString());

                        for (Instructor inst: c.instructors) {Log.i(TAG, inst.name.toString());

                        }
                        Log.i(TAG,"-----------------------");
                    }





                }
                if(requestCatalog.isExecuted()){
                    listaCursos = (ListView) findViewById(R.id.listViewId);
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                            getApplicationContext(),
                            android.R.layout.simple_expandable_list_item_1,
                            android.R.id.text1,
                            titulos);

                    listaCursos.setAdapter(adaptador);
                    listaCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int posicao = position;
                            Toast.makeText(TesteActivity.this, subtitulos.get(position).toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<UdacityCatalog> call, Throwable t) {
                Log.e(TAG, "Erro" +t.getMessage());
                Toast.makeText(TesteActivity.this, "Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();

            }
        });




    }


}
