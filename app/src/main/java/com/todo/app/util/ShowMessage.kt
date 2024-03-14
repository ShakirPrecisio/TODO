package com.todo.app.util;


import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.todo.app.ApplicationClass
import com.todo.app.R


class ShowMessage {


    fun showMessaeOnSnakeBar(message: String, view : View){
        val snackBar: Snackbar = Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_LONG
        )
        val snackBarView: View = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(ApplicationClass.mcontext, R.color.appPrimaryColor))
        snackBar.show()
    }

}