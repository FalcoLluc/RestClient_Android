package com.example.retrofit_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private Button buttonSearch;
    private TextView resultsView;
    Retrofit retrofit;
    GitHub github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        editTextUsername = findViewById(R.id.editTextUsername);
        buttonSearch = findViewById(R.id.buttonSearch);
        resultsView = findViewById(R.id.resultsTextView);

        //RetroFit
        retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://api.github.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        github = retrofit.create(GitHub.class);
    }

    public void makeAPICall(){
        Controller controller = new Controller();
        controller.start();
    }

    public void onClickAPICall(View v){
        String username = editTextUsername.getText().toString().trim();  // Get username from EditText
        resultsView.setText("");

        // Check if the username is empty
        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter a GitHub username", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<List<Repo>> call = github.repos(username);

        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    List<Repo> repos = response.body();
                    // Handle the response
                    for (Repo repo : repos ) {
                        Log.d("API_RESPONSE", "Repo name: " + repo.name);
                        resultsView.append("Name: "+repo.name+" Desc: "+ repo.description+" Count: "+repo.stargarzers_count+"\n");
                    }
                }
                else{
                    Log.d("API_RESPONSE", "Response not successful, code: " + response.code());
                    Toast.makeText(MainActivity.this, "User not found or no repositories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                // Handle the error
                Log.e("API_ERROR", "API call failed", t);
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}