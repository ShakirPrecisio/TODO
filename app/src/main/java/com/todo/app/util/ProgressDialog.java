package com.todo.app.util;

import android.app.Dialog;
import android.content.Context;

import com.todo.app.R;


public class ProgressDialog {
    public Context context;
    private Dialog dialog;


    public ProgressDialog(Context context) {
        this.context = context;

        dialog = new Dialog(context); // Context, this, etc.



        dialog.setContentView(R.layout.progress_dialoge);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }


    public void openDialog() {

        dialog.show();
    }


    public void closeDialog() {

        dialog.dismiss();
    }






}
