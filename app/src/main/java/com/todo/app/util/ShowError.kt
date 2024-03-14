package com.todo.app.util;


import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.todo.app.ApplicationClass

class ShowError {


    fun showErrorOnSnakeBar(error: String, view : View){
        val snackBar: Snackbar = Snackbar.make(
            view,
            error,
            Snackbar.LENGTH_LONG
        )
        val snackBarView: View = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(ApplicationClass.mcontext, android.R.color.holo_red_dark))
        snackBar.show()
    }

}