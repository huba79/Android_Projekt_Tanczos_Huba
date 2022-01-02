package com.example.feleves_projekt;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.feleves_projekt.Models.Login;
import com.example.feleves_projekt.Models.LoginResponse;
import com.example.feleves_projekt.Services.LoginServices;
import com.example.feleves_projekt.Services.NetworkServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    //a hasznalt api linkjeinek beallitasa, gondolom vegpontonkent egy
    //az API ismerete kotelezo, anelkul a kezeles maceras
    EditText eiv;
    EditText piv;
    ImageView tiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int orientation = this.getResources().getConfiguration().orientation;

        if (orientation==Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main_portrait);
        } else setContentView(R.layout.activity_main_landscape);

        eiv = findViewById(R.id.emailAddressInputId);
        piv = findViewById(R.id.passwordInputId);
        tiv = findViewById(R.id.themeImageViewId);
        tiv.setImageResource(  orientation==Configuration.ORIENTATION_PORTRAIT? R.drawable.theme_portrait:R.drawable.theme_land_900x900 )  ;
        Button loginButton = findViewById(R.id.loginButtonId);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginServices service = NetworkServices.getApiServiceInstance().create(LoginServices.class);

                Call<LoginResponse> loginCall = service.login(new Login(eiv.getText().toString(), piv.getText().toString()));


                loginCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("\nresponse", "received succesful response" + response.code());
                            switch(response.code()){
                                case 200 : {
                                    MainActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(MainActivity.this, ReservationsActivity.class);
                                            intent.putExtra ("userid", (long)103);
                                            intent.putExtra("displayname", "Pontyhorgasz");
                                            startActivity(intent);
                                        }
                                    });
                                    break;
                                }
                                case 204: {
                                    Toast.makeText(MainActivity.this,"Hibas felhasznalonev vagy jelszo!",Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                default: {
                                    Log.d("response", "received unsuccesful response:\t" + response.code());
                                    Toast.makeText(MainActivity.this,"Halozati hiba!!",Toast.LENGTH_SHORT).show();

                                }
                            }

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                        Log.d("\nresponse", "call failure!\n");
                        Toast.makeText(getBaseContext(), "Inaccessible Login service!", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
                setContentView(R.layout.activity_main_landscape);
            } else setContentView(R.layout.activity_main_portrait);
    }
}