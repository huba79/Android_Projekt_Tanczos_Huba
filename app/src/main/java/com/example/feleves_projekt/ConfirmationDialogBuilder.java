package com.example.feleves_projekt;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

class ConfirmationDialogBuilder extends AlertDialog.Builder{

    public ConfirmationDialogBuilder(@NonNull Context context, int messageID) {
        super(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        this.setTitle(context.getResources().getString(messageID));
        this.setPositiveButton(R.string.confirmButtonText, (dialog, which) -> {
            //ennyi
        });
    }
    public AlertDialog getAlertDialog(){
        return  this.create();
    }
}