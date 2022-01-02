package com.example.feleves_projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feleves_projekt.Models.Reservations;
import com.example.feleves_projekt.Services.NetworkServices;
import com.example.feleves_projekt.Services.ReservationsServices;

import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationEditActivity extends AppCompatActivity {

    EditText pondNameEditField;
    EditText stageNumberEditField;
    EditText dateFromEditField;
    EditText dateToEditField;
    TextView statusView;
    Button editConfirmButton;
    Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_edit);
        Reservations currentReservation  = (Reservations) getIntent().getSerializableExtra("currentReservation");
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(currentReservation == null) {
            Toast.makeText(ReservationEditActivity.this ,"Hiba a foglalas megjelenitese kozben!",Toast.LENGTH_SHORT).show();
            //at kell irni alert dialogra vagy egy emesztheto hibaoldalra, amint kerul ra ido
        } else {
            pondNameEditField = findViewById(R.id.pondNameEditId);
            stageNumberEditField = findViewById(R.id.stageNumberEditID);
            dateFromEditField = findViewById(R.id.dateFromEditId);
            dateToEditField = findViewById(R.id.dateToEditId);
            statusView = findViewById(R.id.statusTextViewId);

            editConfirmButton = findViewById(R.id.reservationEditConfirmButtonId);
            backButton = findViewById(R.id.backButtonId);

            pondNameEditField.setText( String.valueOf(currentReservation.getPondId() ) );
            stageNumberEditField.setText( String.valueOf(currentReservation.getStageId() ) );
            statusView.setText( String.valueOf(currentReservation.getStatus() ) );

            statusView.setText(Constants.STATUSES.get(currentReservation.getStatus()));
            statusView.setTextColor(Constants.STATUS_COLORS.get(currentReservation.getStatus()));

            dateFromEditField.setText(currentReservation.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            dateToEditField.setText(currentReservation.getDateTo().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            backButton.setOnClickListener(view->{
                Log.d("backPressed ", "Back button Pressed ");
                this.finish();
            });

            editConfirmButton.setOnClickListener(view->{
                ReservationsServices service = NetworkServices.getApiServiceInstance().create(ReservationsServices.class);

                try {
                    Reservations UpdatedReservation = new Reservations(
                            Long.parseLong(String.valueOf(pondNameEditField.getText())),
                            Long.parseLong(String.valueOf(stageNumberEditField.getText())),
                            currentReservation.getUserId(),
                            1,//modositas utan uj foglalassa valik
                            getDateFromView(dateFromEditField),
                            getDateFromView(dateToEditField)
                    );
                    Call<Reservations> reservationPost = service.updateReservation(currentReservation.getId(),UpdatedReservation);
                    reservationPost.enqueue(new Callback<Reservations>() {
                        @Override
                        public void onResponse(@NonNull Call<Reservations> call, @NonNull Response<Reservations> response) {
                            if (response.isSuccessful()) {
                                Log.d("response", "received succesful response" + response.code());
                                ConfirmationDialogBuilder builder = new ConfirmationDialogBuilder(ReservationEditActivity.this,R.string.updateSuccesfulMessage );
                                builder.getAlertDialog().show();
                            } else {
                                Log.d("response", "Sikertelen Frissites:\t" + response.code());
                                ConfirmationDialogBuilder builder = new ConfirmationDialogBuilder(ReservationEditActivity.this,R.string.updateUnsuccesfulMessage );
                                builder.getAlertDialog().show();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<Reservations> call, @NonNull Throwable t) {
                            Log.d("\nresponse", "call failure!\n");
                            t.printStackTrace();
                            ConfirmationDialogBuilder builder = new ConfirmationDialogBuilder(ReservationEditActivity.this,R.string.updateUnsuccesfulMessage );
                            builder.getAlertDialog().show();
                        }
                    });
                } catch(Exception e){
                    Log.d("dataparseerror",e.getLocalizedMessage()+"\n");
                    e.printStackTrace();
                    ConfirmationDialogBuilder builder = new ConfirmationDialogBuilder(ReservationEditActivity.this,R.string.incorrectDataFormatMessage );
                    builder.getAlertDialog().show();
                }
            });
        }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    OffsetDateTime getDateFromView(EditText sourceView) throws DateTimeException {
        String baseDateTime = String.valueOf(sourceView.getText());
        String offsetDT = baseDateTime+":00.000000+02:00";
        offsetDT=offsetDT.substring(0,10)+'T'+offsetDT.substring(11);
        Log.d("\ndataparseerror",offsetDT);
        //TODO hasznalni a hely idozonat
        //pl: ZoneOffset o = OffsetDateTime.now().getOffset();
        try {
            return OffsetDateTime.parse(offsetDT,DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (Exception e) {
            Log.d("dataparseerror",e.getLocalizedMessage());
            e.printStackTrace();
        }
        throw new DateTimeException("Error while parsing contents of the view!\n");
    }
}