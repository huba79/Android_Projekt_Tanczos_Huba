package com.example.feleves_projekt;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.feleves_projekt.Models.Reservations;
import com.example.feleves_projekt.Services.NetworkServices;
import com.example.feleves_projekt.Services.ReservationsServices;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder> {

    private final Context context;
    private final ArrayList<Reservations> reservationsList;

    public ReservationsAdapter(Context context, ArrayList<Reservations> reservationsList) {
        this.context = context;
        this.reservationsList = reservationsList;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservations_list_item, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservations reservation = reservationsList.get(position);

        holder.pondNameView.setText(reservation.getPondId().toString());
        holder.stageIdView.setText(reservation.getStageId().toString());
        holder.statusView.setText(Constants.STATUSES.get(reservation.getStatus()));
        holder.statusView.setTextColor(Constants.STATUS_COLORS.get(reservation.getStatus()));
        holder.dateFromView.setText(reservation.getDateFrom().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        holder.dateToView.setText(reservation.getDateTo().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        holder.arrowIcon.setImageResource(R.drawable.downarrow);

        holder.reservationEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //working.......
                Intent toEdit = new Intent(view.getContext(),ReservationEditActivity.class);
                toEdit.putExtra("currentReservation",reservationsList.get(holder.getAdapterPosition()));
                view.getContext().startActivity(toEdit);
            }
        });

        holder.reservationCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO cancel - update reservation to cancelled status
                ReservationsServices service = NetworkServices.getApiServiceInstance().create(ReservationsServices.class);
                Reservations currentReservation = reservationsList.get(holder.getAdapterPosition());
                currentReservation.setStatus(4);

                Call<Reservations> reservationPost = service.updateReservation(currentReservation.getId(),currentReservation);
                reservationPost.enqueue(new Callback<Reservations>() {
                    @Override
                    public void onResponse(@NonNull Call<Reservations> call, @NonNull Response<Reservations> response) {
                        if (response.isSuccessful()) {
                            Log.d("response", "received successful response" + response.code());

                            //TODO utananezni hogy innen hogy lehet ujratolteni az activity-t
                            
                            Toast.makeText(context.getApplicationContext(), R.string.bigCancelNotificationSuccess,Toast.LENGTH_SHORT).show();
//                            ConfirmationDialogBuilder builder = new ConfirmationDialogBuilder(context,R.string.cancelSuccesfulMessage );
//                            builder.getAlertDialog().show();
                        } else {
                            Log.d("response", "received unsuccessful response:\t" + response.code());
                            Toast.makeText(context.getApplicationContext(), R.string.bigCancelNotificationSuccess,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Reservations> call, @NonNull Throwable t) {
                        Log.d("\nresponse", "call failure!\n");
                        Toast.makeText(context, R.string.bigCancelNotificationFailure, Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                        ConfirmationDialogBuilder builder = new ConfirmationDialogBuilder(context,R.string.cancelUsuccesfulMessage );
                        builder.getAlertDialog().show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservationsList.size();
    }

    class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView pondNameView;
        TextView pondLabelView;
        TextView stageIdView;
        TextView stageLabelView;
        TextView statusView;
        TextView statusLabelView;
        TextView dateFromView;
        TextView dateToView;
        ImageView arrowIcon;

        Button reservationEditButton;
        Button reservationCancelButton;

        public ReservationViewHolder(View itemView) {
            super(itemView);

            pondLabelView = itemView.findViewById(R.id.pondLabelid);
            pondNameView = itemView.findViewById(R.id.pondNameEditId);
            stageLabelView = itemView.findViewById(R.id.stageLabelId);
            stageIdView = itemView.findViewById(R.id.stageNumberEditID);
            statusLabelView = itemView.findViewById(R.id.statusLabelId);
            statusView = itemView.findViewById(R.id.statusTextViewId);
            dateFromView = itemView.findViewById(R.id.dateFromEditId);
            dateToView = itemView.findViewById(R.id.dateToEditId);

            arrowIcon=itemView.findViewById(R.id.rightArrowIconId);

            reservationEditButton = itemView.findViewById(R.id.reservationEditConfirmButtonId);
            reservationCancelButton = itemView.findViewById(R.id.backButtonId);

        }
    }
}
