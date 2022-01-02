package com.example.feleves_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feleves_projekt.Models.Reservations;
import com.example.feleves_projekt.Services.NetworkServices;
import com.example.feleves_projekt.Services.ReservationsServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationsActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private Long userId;
    private String displayName;
    private ArrayList<Reservations> reservationsToDisplay= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // get the user data out of the intent
        Intent intent= getIntent();
        userId= intent.getLongExtra("userid",0);
        displayName = intent.getStringExtra("displayName");

        recyclerView = findViewById(R.id.rvReservationsId);
        //TODO get default appBar and display welcome message

        getData();
        Log.d("errors", "getData finalized!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        Log.d("errors", "getData finalized!");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getData() {
        ReservationsServices reservationsServices = NetworkServices.getApiServiceInstance().create(ReservationsServices.class);

        Call<List<Reservations>> reservationsCall = reservationsServices.getReservationsByUser(userId);

        reservationsCall.enqueue(new Callback<List<Reservations>>() {
            @Override
            public void onResponse(@NonNull Call<List<Reservations>> call, @NonNull Response<List<Reservations>> response) {
                if (response.isSuccessful()) {
                    Log.d("\nresponse", "received succesful response" + response.code());

                    reservationsToDisplay = (ArrayList<Reservations>) response.body();


                    ReservationsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //TODO a konfirmalo alert dialogot ki lehetne szervezni kulso osztalyba, parameter szoveggel

                            ReservationsAdapter adapter = new ReservationsAdapter(getBaseContext(),reservationsToDisplay );
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReservationsActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                } else {
                    Log.d("response", "received unsuccesful response:\t" + response.code());
                    ConfirmationDialogBuilder builder = new ConfirmationDialogBuilder(ReservationsActivity.this,R.string.reservationsUnretrievableMessage);
                    builder.getAlertDialog().show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Reservations>> call, @NonNull Throwable t) {
                Log.d("\nresponse", "call failure!\n");
                t.printStackTrace();
                ConfirmationDialogBuilder builder = new ConfirmationDialogBuilder(ReservationsActivity.this,R.string.reservationsUnretrievableMessage);
                builder.getAlertDialog().show();
            }
        });

    }


}