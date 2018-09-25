package com.example.hsamuel.midifood

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class Command : AppCompatActivity() {
    lateinit var PopDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command)
        PopDialog= Dialog(this)
    }


    fun ShowPopup(v: View) {
        val txtclose: TextView = PopDialog.findViewById(R.id.txtclose)
        val Okclose: Button = PopDialog.findViewById(R.id.Okclose)
        PopDialog.setContentView(R.layout.confirmation)
        txtclose.text = "M"
        txtclose.setOnClickListener { PopDialog.dismiss() }
        Okclose.setOnClickListener{PopDialog.dismiss()}
        PopDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        PopDialog.show()
    }

}
